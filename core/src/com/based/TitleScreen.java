package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Tech on 3/3/2016.
 */
public class TitleScreen implements Screen {

    private MyGdxGame game;

    public TitleScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clearScreen();
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameplayScreen(game));
        }
    }

    private void clearScreen() {
        //                    R,G,B,A
        Gdx.gl20.glClearColor(0,0,1,1);
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
