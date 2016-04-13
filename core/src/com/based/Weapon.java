package com.based;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Weapon {
    protected static final int HEIGHT = 25;
    protected static final int WIDTH = 10;
    protected Rectangle hitBox;
    protected float x;
    protected float y;

    public Weapon(float x, float y, int dir) {
        this.x = x;
        this.y = y;
        if(dir == Player.UP || dir == Player.DOWN)
            hitBox = new Rectangle(x,y,WIDTH,HEIGHT);
        else
            hitBox = new Rectangle(x,y,HEIGHT,WIDTH);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateHitBox();
    }

    protected void updateHitBox() {
        hitBox.setPosition(x,y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitBox.x, hitBox.y,
                hitBox.width, hitBox.height);
    }

    public void update(Player p, float delta) {

    }
}
