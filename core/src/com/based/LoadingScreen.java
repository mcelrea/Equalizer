package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class LoadingScreen implements Screen {

    private MyGdxGame game;

    public LoadingScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.getAssetManager().load("untitled.tmx", TiledMap.class);
        game.getAssetManager().load("untitled2.tmx", TiledMap.class);
        game.getAssetManager().load("untitled3.tmx", TiledMap.class);
        game.getAssetManager().load("tempDungeon.tmx", TiledMap.class);
    }

    /*
     * this render method will continuously run as fast
     * as it can: we will put
     *   * draw code
     *   * AI
     *   * user input
     */
    @Override
    public void render(float delta) {
        clearScreen();
        if(game.getAssetManager().update()) {
            game.setScreen(new TitleScreen(game));
        }
    }

    private void clearScreen() {
        //                    R,G,B,A
        Gdx.gl20.glClearColor(0,1,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

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
