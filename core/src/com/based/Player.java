package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public static final float WIDTH = 25;
    public static final float HEIGHT = 25;
    private float x = 100;
    private float y = 100;
    private float xMaxSpeed = 2;
    private float yMaxSpeed = 2;
    private float xSpeed = 0;
    private float ySpeed = 0;
    private Rectangle hitBox;

    public Player() {
        hitBox = new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitBox.x,hitBox.y,
                hitBox.width,hitBox.height);
    }

    private void updateHitBox() {
        hitBox.setPosition(x, y);
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
        updateHitBox();
    }

    public void update() {
        Input input = Gdx.input;

        //up,down movement
        if(input.isKeyPressed(Input.Keys.W)) {
            ySpeed = yMaxSpeed;
        }
        else if(input.isKeyPressed(Input.Keys.S)) {
            ySpeed = -yMaxSpeed;
        }
        else {
            ySpeed = 0;
        }
        //left,right movement
        if(input.isKeyPressed(Input.Keys.A)) {
            xSpeed = -xMaxSpeed;
        }
        else if(input.isKeyPressed(Input.Keys.D)) {
            xSpeed = xMaxSpeed;
        }
        else {
            xSpeed = 0;
        }

        //move player
        x += xSpeed;
        y += ySpeed;

        updateHitBox();

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateHitBox();
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
