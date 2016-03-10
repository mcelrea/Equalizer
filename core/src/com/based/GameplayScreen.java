package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

/**
 * Created by Tech on 3/3/2016.
 */
public class GameplayScreen implements Screen {

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;
    private static final float CELL_SIZE = 25;

    OrthographicCamera camera;//2D camera, it displays a portion of the world
    Viewport viewport; //lock the dimensions of the screen
    SpriteBatch spriteBatch; //draw textures to the screen
    ShapeRenderer shapeRenderer; //draw shapes to the screen
    OrthogonalTiledMapRenderer mapRenderer; //draw Tiled maps

    Area overWorld[][];
    Area currentArea;

    Player player;
    private int playerRow = 3;
    private int playerCol = 7;

    private MyGdxGame game;

    public GameplayScreen(MyGdxGame game) {
        this.game = game;
        player = new Player();
        overWorld = new Area[10][16];
        createOverWorld();
    }

    private void createOverWorld() {
        overWorld[3][7]= new Area((TiledMap)game.getAssetManager().get("untitled.tmx"));
        overWorld[2][7]= new Area((TiledMap)game.getAssetManager().get("untitled2.tmx"));
        overWorld[2][8]= new Area((TiledMap)game.getAssetManager().get("untitled3.tmx"));
        currentArea = overWorld[3][7];
    }

    /*
     * Runs when the this screen is first shown, only once
     */
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);//NEED THIS OR MAP ONLY SHOWS LIKE 2 TILES
        mapRenderer = new OrthogonalTiledMapRenderer(overWorld[3][7].getMap(), spriteBatch);
        mapRenderer.setView(camera);
    }

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
        shapeRenderer.end();
    }

    private void draw() {
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
        mapRenderer.render();
    }

    private void update(float delta) {
        player.update();
        Array<CollisionCell> collisionCells = whichCellsDoesPlayerCover();
        filterOutNonCollisionCells(collisionCells);
        handlePlayerCollision();
        handleAreaTransition();
    }

    private void handleAreaTransition() {
        //goes off top of the screen
        if(player.getY() > WORLD_HEIGHT) {
            player.setPosition(player.getX(),0);
            playerRow--;
            currentArea = overWorld[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the bottom of the screen
        else if(player.getY() < 0) {
            player.setPosition(player.getX(),WORLD_HEIGHT - player.HEIGHT);
            playerRow++;
            currentArea = overWorld[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the right of the screen
        else if(player.getX() > WORLD_WIDTH) {
            player.setPosition(0, player.getY());
            playerCol++;
            currentArea = overWorld[playerRow][playerCol];
            mapRenderer.setMap(currentArea.getMap());
        }
        //goes off the left of the screen
        else if(player.getX() < 0) {
            player.setPosition(WORLD_WIDTH - player.WIDTH, player.getY());
            playerCol--;
            currentArea = overWorld[playerRow][playerCol];
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

    private Array<CollisionCell> filterOutNonCollisionCells(Array<CollisionCell> cells) {
        for(Iterator<CollisionCell> iter = cells.iterator(); iter.hasNext();) {
            CollisionCell collisionCell = iter.next();

            if(collisionCell == null) {
                iter.remove();
            }
            else if(collisionCell.isEmpty()) {
                iter.remove();
            }
            else if(collisionCell.getId() == 1) {
                iter.remove();
            }
            else if(collisionCell.getId() == 2) {
                iter.remove();
            }
            else if(collisionCell.getId() == 3) {
                iter.remove();
            }
        }

        return cells;
    }

    public void handlePlayerCollision() {
        Array<CollisionCell> playerCells = whichCellsDoesPlayerCover();
        playerCells = filterOutNonCollisionCells(playerCells);
        for(CollisionCell cell: playerCells) {
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
        viewport.update(width,height);
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
