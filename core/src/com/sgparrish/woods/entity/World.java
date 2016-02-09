package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.util.DebugRenderer;

public class World implements Entity {

    public Player player;
    public BiMap<Coordinate, TileEntity> worldMap;

    public World() {
        worldMap = HashBiMap.create();
    }

    public TileEntity getFromVector(Vector2 vector) {
        return worldMap.get(new Coordinate((int) vector.x, (int) vector.y));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderWorld(this);
    }
}
