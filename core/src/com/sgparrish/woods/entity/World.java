package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.util.DebugRenderer;

import java.util.ArrayList;
import java.util.List;

public class World implements Entity {

    public List<PhysicsEntity> physicsEntities;
    public BiMap<Coordinates, TileEntity> worldMap;

    public World() {
        physicsEntities = new ArrayList<PhysicsEntity>();
        worldMap = HashBiMap.create();
    }

    public Coordinates getCoordsFromVector(Vector2 vector) {
        return new Coordinates((int) vector.x, (int) vector.y);
    }


    public TileEntity getTileFromVector(Vector2 vector) {
        return worldMap.get(getCoordsFromVector(vector));
    }

    @Override
    public void update(float delta) {

        for (PhysicsEntity entity : physicsEntities) {

        }

    }


    @Override
    public void render() {
        DebugRenderer.getInstance().renderWorld(this);
    }
}
