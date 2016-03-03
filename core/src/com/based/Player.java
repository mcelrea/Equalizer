package com.based;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public static final float WIDTH = 25;
    public static final float HEIGHT = 25;
    private int x = 100;
    private int y = 100;
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

}
