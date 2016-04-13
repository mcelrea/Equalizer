package com.based;

import com.badlogic.gdx.math.Rectangle;

public class BasicSword extends  Weapon{

    public BasicSword(float x, float y, int dir) {
        super(x, y, dir);
    }

    public void update(Player p, float delta) {
        x = p.getX();
        y = p.getY();
        updateHitBox();
    }
}
