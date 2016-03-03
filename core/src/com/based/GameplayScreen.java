package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Tech on 3/3/2016.
 */
public class GameplayScreen implements Screen {

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    OrthographicCamera camera;//2D camera, it displays a portion of the world
    Viewport viewport; //lock the dimensions of the screen
    SpriteBatch spriteBatch; //draw textures to the screen
    ShapeRenderer shapeRenderer; //draw shapes to the screen

    Player player;

    private MyGdxGame game;

    public GameplayScreen(MyGdxGame game) {
        this.game = game;
        player = new Player();
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

    }

    private void update(float delta) {

    }

    private void clearScreen() {
        //                    R,G,B,A
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
