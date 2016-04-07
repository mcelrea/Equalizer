package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

public abstract class AbstractWorld implements Screen {

    static final float WORLD_WIDTH = 800;
    static final float WORLD_HEIGHT = 600;
    static final float CELL_SIZE = 25;

    OrthographicCamera camera;//2D camera, it displays a portion of the world
    Viewport viewport; //lock the dimensions of the screen
    SpriteBatch spriteBatch; //draw textures to the screen
    ShapeRenderer shapeRenderer; //draw shapes to the screen
    OrthogonalTiledMapRenderer mapRenderer; //draw Tiled maps

    Area world[][];
    Area currentArea;

    Player player;
    int playerRow = 3;
    int playerCol = 7;

    MyGdxGame game;

    public abstract void createOverWorld();

    public abstract void handleWorldChange();

    @Override
    public abstract void show();

    @Override
    public void render(float delta) {
        clearScreen();
        update(delta);//AI, User input
        draw();//final game graphics
        drawDebug();//hidden things (hit boxes, hidden text)
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin();
        player.drawDebug(shapeRenderer);
        currentArea.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

    private void draw() {
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
        mapRenderer.render();
    }

    public void update(float delta) {
        player.update();
        Array<CollisionCell> collisionCells = whichCellsDoesPlayerCover();
        filterOutNonCollisionCells(collisionCells);
        System.out.println(collisionCells);
        handlePlayerCollision();
        handleAreaTransition();

        currentArea.enemiesAct(delta);
    }

    private void handleAreaTransition() {
        //goes off top of the screen
        if(player.getY() > WORLD_HEIGHT) {
            player.setPosition(player.getX(),0);
            playerRow--;
            currentArea = world[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the bottom of the screen
        else if(player.getY() < 0) {
            player.setPosition(player.getX(),WORLD_HEIGHT - player.HEIGHT);
            playerRow++;
            currentArea = world[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the right of the screen
        else if(player.getX() > WORLD_WIDTH) {
            player.setPosition(0, player.getY());
            playerCol++;
            currentArea = world[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the left of the screen
        else if(player.getX() < 0) {
            player.setPosition(WORLD_WIDTH - player.WIDTH, player.getY());
            playerCol--;
            currentArea = world[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
    }

    private void clearScreen() {
        //                    R,G,B,A
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public Array<CollisionCell> whichCellsDoesPlayerCover() {
        float x = player.getX();
        float y = player.getY();
        Array<CollisionCell> cellsCovered = new Array<CollisionCell>();
        float cellRow = x / CELL_SIZE;
        float cellCol = y / CELL_SIZE;

        int bottomLeftCellRow = MathUtils.floor(cellRow);
        int bottomLeftCellCol = MathUtils.floor(cellCol);

        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer)currentArea.getMap().getLayers().get(0);

        cellsCovered.add(new CollisionCell(tiledMapTileLayer.getCell(bottomLeftCellRow,
                bottomLeftCellCol), bottomLeftCellRow, bottomLeftCellCol));

        if(cellRow % 1 != 0 && cellCol % 1 != 0) {
            int topRightCellRow = bottomLeftCellRow + 1;
            int topRightCellCol = bottomLeftCellCol + 1;
            cellsCovered.add(new CollisionCell(tiledMapTileLayer.getCell(topRightCellRow,
                    topRightCellCol), topRightCellRow, topRightCellCol));
        }
        if(cellRow % 1 != 0) {
            int bottomRightCellRow = bottomLeftCellRow + 1;
            int bottomRightCellCol = bottomLeftCellCol;
            cellsCovered.add(new CollisionCell(tiledMapTileLayer.getCell(bottomRightCellRow,
                    bottomRightCellCol), bottomRightCellRow, bottomRightCellCol));

        }
        if(cellCol % 1 != 0) {
            int topLeftCellRow = bottomLeftCellRow;
            int topLeftCellCol = bottomLeftCellCol + 1;
            cellsCovered.add(new CollisionCell(tiledMapTileLayer.getCell(topLeftCellRow,
                    topLeftCellCol), topLeftCellRow, topLeftCellCol));
        }

        return cellsCovered;
    }

    public Array<CollisionCell> filterOutNullCells(Array<CollisionCell> cells) {
        for(Iterator<CollisionCell> iter = cells.iterator(); iter.hasNext();) {
            CollisionCell collisionCell = iter.next();

            if(collisionCell == null) {
                iter.remove();
            }
        }

        return cells;
    }

    public Array<CollisionCell> filterOutNonCollisionCells(Array<CollisionCell> cells) {
        for(Iterator<CollisionCell> iter = cells.iterator(); iter.hasNext();) {
            CollisionCell collisionCell = iter.next();

            if(collisionCell == null) {
                iter.remove();
            }
            else if(collisionCell.isEmpty()) {
                iter.remove();
            }
            else if(collisionCell.getId() >= 1 && collisionCell.getId() <= 15) {
                iter.remove();
            }
            else if(collisionCell.getId() >= 33 && collisionCell.getId() <= 48) {
                iter.remove();
            }
        }

        return cells;
    }

    public void handlePlayerCollision() {
        Array<CollisionCell> playerCells = whichCellsDoesPlayerCover();
        playerCells = filterOutNonCollisionCells(playerCells);
        for(CollisionCell cell: playerCells) {
            if(cell.getId() == 16) {
                handleWorldChange();
                return;
            }
            float cellLevelX = cell.getCellRow() * CELL_SIZE;
            float cellLevelY = cell.getCellCol() * CELL_SIZE;
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(player.getHitBox(),
                    new Rectangle(cellLevelX, cellLevelY, CELL_SIZE, CELL_SIZE),
                    intersection);
            if(intersection.getHeight() < intersection.getWidth()) {

                if(intersection.getY() == player.getY()) {
                    player.setPosition(player.getX(), intersection.getY() + intersection.getHeight());
                }
                if(intersection.getY() > player.getY()) {
                    player.setPosition(player.getX(), intersection.getY() - player.HEIGHT);
                }
            }
            else if (intersection.getWidth() < intersection.getHeight()) {
                if(intersection.getX() == player.getX()) {
                    player.setPosition(intersection.getX() + intersection.getWidth(),
                            player.getY());
                }
                if(intersection.getX() > player.getX()) {
                    player.setPosition(intersection.getX() - player.WIDTH,
                            player.getY());
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
