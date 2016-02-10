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

    public Coordinates getCoordsFromTile(TileEntity tileEntity) {
        return worldMap.inverse().get(tileEntity);
    }

    public TileEntity getTileFromCoords(Coordinates coordinates) {
        return worldMap.get(coordinates);
    }

    public Coordinates getCoordsFromVector(Vector2 vector) {
        return new Coordinates((int) vector.x, (int) vector.y);
    }

    public TileEntity getTileFromVector(Vector2 vector) {
        return worldMap.get(getCoordsFromVector(vector));
    }

    @Override
    public void update(float delta) {

        // Update all physics entities
        for (PhysicsEntity entity : physicsEntities) {
            // Keep a copy of position
            Vector2 previousPosition = new Vector2(entity.position);

            // Move all physics entities
            entity.position.add(new Vector2(entity.velocity).scl(delta));

            // Get below
            Coordinates below = getCoordsFromVector(new Vector2(entity.position).add(0, -0.01f));
            if (entity.velocity.y < 0 && worldMap.get(below) != null) {
                entity.onGround = true;
                entity.position.y = below.y + 1;
                entity.velocity.y = Math.max(entity.velocity.y, 0);
            } else if (worldMap.get(below) == null) {
                entity.onGround = false;
            }

            // Get top
            Coordinates top = getCoordsFromVector(new Vector2(entity.position).add(0, 1.01f));
            if (entity.velocity.y > 0 && worldMap.get(top) != null) {
                entity.position.y = top.y;
                entity.velocity.y = Math.min(entity.velocity.y, 0);
            }

            // Get left
            Coordinates left = getCoordsFromVector(new Vector2(entity.position).add(-0.5f, 0.51f));
            if (entity.velocity.x < 0 && worldMap.get(left) != null) {
                entity.position.x = left.x + 1.5f;
                entity.velocity.x = Math.max(entity.velocity.x, 0);
            }

            // Get right
            Coordinates right = getCoordsFromVector(new Vector2(entity.position).add(0.5f, 0.51f));
            if (entity.velocity.x > 0 && worldMap.get(right) != null) {
                entity.position.x = right.x - 0.5f;
                entity.velocity.x = Math.min(entity.velocity.x, 0);
            }

            // Get below right
            Coordinates belowRight = getCoordsFromVector(new Vector2(entity.position).add(0.49f, 0.01f));
            if (worldMap.get(belowRight) != null) {
                // project to 45 deg axis
                Vector2 axis = (new Vector2(-1, 1)).nor();
                float entityProj = Math.abs(axis.dot(entity.position));
                float tileProj = Math.abs(axis.dot(new Vector2(belowRight.x, belowRight.y + 1)));
                float velProj = Math.abs(axis.dot(entity.velocity));

                if (entityProj > tileProj) {
                    entity.position.add((new Vector2(axis)).scl(entityProj - tileProj));
                    entity.velocity.add((new Vector2(axis)).scl(velProj));

                }
            }

            if (!entity.onGround)
                entity.velocity.y -= 0.01f;
        }
    }


    @Override
    public void render() {
        DebugRenderer.getInstance().renderWorld(this);
    }
}
