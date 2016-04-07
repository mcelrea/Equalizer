package com.based;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class FirstEnemy extends Enemy{

    private Circle collisionCircle;
    private float xvel;
    private float yvel;
    private long nextMove;
    private long wait = 1000; //1000 ms = 1 s

    public FirstEnemy(float x, float y, int health, int damage) {
        super(x, y, health, damage);
        collisionCircle = new Circle();
        collisionCircle.setRadius(15);
        collisionCircle.setPosition(x, y);
        nextMove = System.currentTimeMillis() + wait;
    }

    @Override
    public void act() {
        if(nextMove < System.currentTimeMillis()) {
            int dir = (int) (Math.random() * 4);
            if(dir == 0) {
                xvel = 1;
                yvel = 0;
            }
            else if(dir == 1) {
                xvel = -1;
                yvel = 0;
            }
            else if(dir == 2) {
                xvel = 0;
                yvel = 1;
            }
            else if(dir == 3) {
                xvel = 0;
                yvel = -1;
            }
            nextMove = System.currentTimeMillis() + wait;
        }

        x += xvel;
        y += yvel;
        collisionCircle.setPosition(x,y);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }
}
