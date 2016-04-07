package com.based;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Enemy {

    private int health;
    private int damage;
    private Animation walk;
    private Animation attack;
    private Animation die;
    private Texture idle;

    public Enemy(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }

    //move enemy code (A.I.)
    public abstract void act();

    //draw the graphics of the enemy onto the screen
    public abstract void draw(SpriteBatch spriteBatch);

    //draw the hitbox of the enemy onto the screen
    public abstract void drawDebug(ShapeRenderer shapeRenderer);
}
