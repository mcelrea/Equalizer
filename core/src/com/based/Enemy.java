package com.based;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Enemy {

    protected int health;
    protected int damage;
    protected float x;
    protected float y;
    protected Animation walk;
    protected Animation attack;
    protected Animation die;
    protected Texture idle;

    public Enemy(float x, float y, int health, int damage) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }

    //move enemy code (A.I.)
    public abstract void act();

    //draw the graphics of the enemy onto the screen
    public abstract void draw(SpriteBatch spriteBatch);

    //draw the hitbox of the enemy onto the screen
    public abstract void drawDebug(ShapeRenderer shapeRenderer);
}
