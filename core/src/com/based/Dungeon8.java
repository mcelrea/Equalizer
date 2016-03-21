package com.based;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

/*
 * Programmer : Hibshman
 * Item Found: Boat
 */
public class Dungeon8 extends AbstractWorld {

    public Dungeon8(MyGdxGame game, Player player) {
        this.game = game;
        this.player = player;

        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);//NEED THIS OR MAP ONLY SHOWS LIKE 2 TILES

        createOverWorld();

        mapRenderer = new OrthogonalTiledMapRenderer(world[playerRow][playerCol].getMap(), spriteBatch);
        mapRenderer.setView(camera);
    }


    @Override
    public void createOverWorld() {
        world = new Area[10][10];
        world[0][0]= new Area((TiledMap)game.getAssetManager().get("tempDungeon.tmx"));
        currentArea = world[0][0];
        playerRow = 0;
        playerCol = 0;
    }

    @Override
    public void handleWorldChange() {

    }

    @Override
    public void show() {

    }
}
