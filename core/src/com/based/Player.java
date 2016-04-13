package com.based;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public static final int UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4;
    public static final float WIDTH = 25;
    public static final float HEIGHT = 25;
    private float x = 300;
    private float y = 100;
    private float xMaxSpeed = 10;
    private float yMaxSpeed = 10;
    private float xSpeed = 0;
    private float ySpeed = 0;
    private int dir = DOWN;
    private Rectangle hitBox;
    private Weapon currentWeaponAnimation;

    public static Inventory inventory;

    public Player() {
        hitBox = new Rectangle(x,y,WIDTH,HEIGHT);
        inventory = new Inventory();
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitBox.x, hitBox.y,
                hitBox.width, hitBox.height);

        if(currentWeaponAnimation != null) {
            currentWeaponAnimation.drawDebug(shapeRenderer);
        }
    }

    private void updateHitBox() {
        hitBox.setPosition(x, y);
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
        updateHitBox();
    }

    public void update(float delta) {
        Input input = Gdx.input;

        //up,down movement
        if(input.isKeyPressed(Input.Keys.W)) {
            ySpeed = yMaxSpeed;
            dir = UP;
        }
        else if(input.isKeyPressed(Input.Keys.S)) {
            ySpeed = -yMaxSpeed;
            dir = DOWN;
        }
        else {
            ySpeed = 0;
        }
        //left,right movement
        if(input.isKeyPressed(Input.Keys.A)) {
            xSpeed = -xMaxSpeed;
            dir = LEFT;
        }
        else if(input.isKeyPressed(Input.Keys.D)) {
            xSpeed = xMaxSpeed;
            dir = RIGHT;
        }
        else {
            xSpeed = 0;
        }

        if(input.isKeyPressed(Input.Keys.SPACE)) {
            attack();
        }

        //move player
        x += xSpeed;
        y += ySpeed;

        updateHitBox();

        if(currentWeaponAnimation != null) {
            currentWeaponAnimation.update(this,delta);
        }
    }

    private void attack() {
        if(inventory.getWeaponEquipped() == Inventory.BASICSWORD) {
            swordAttack();
        }
    }

    private void swordAttack() {
        currentWeaponAnimation = new BasicSword(x,y,dir);
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
