package com.based;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Tech on 3/3/2016.
 */
public class GameplayScreen extends AbstractWorld {

    public GameplayScreen(MyGdxGame game) {
        this.game = game;
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
    public void show() {
        player = new Player();
    }


    public void createOverWorld() {
        world = new Area[10][10];
        world[3][7]= new Area((TiledMap)game.getAssetManager().get("untitled.tmx"));
        world[2][7]= new Area((TiledMap)game.getAssetManager().get("untitled2.tmx"));
        world[2][8]= new Area((TiledMap)game.getAssetManager().get("untitled3.tmx"));
        currentArea = world[3][7];
    }


}
