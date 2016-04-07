package com.based;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class Area {

    private TiledMap map;
    private Array<Enemy> enemies;

    public Area(TiledMap map) {
        this.map = map;
        enemies = new Array<Enemy>();
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        for(Enemy e: enemies) {
            e.drawDebug(shapeRenderer);
        }
    }

    public void enemiesAct(float delta) {
        for(Enemy e: enemies) {
            e.act();
        }
    }

}
