package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.util.DebugRenderer;

import java.util.ArrayList;
import java.util.List;

public class World implements Entity {

    public Vector2 gravity;

    public List<PhysicsEntity> physicsEntities;
    public BiMap<Coordinates, TileEntity> worldMap;

    public World() {
        gravity = new Vector2(0, -0.05f);
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

            entity.canJump = false;

            // Move all physics entities
            entity.position.add(new Vector2(entity.velocity).scl(delta));

            // Collide entities with world
            resolveCollision(entity);

            entity.velocity.add(gravity);
        }
    }

    private void resolveCollision(PhysicsEntity entity) {
        // First check collision points
        for (CollisionPoint point : entity.collisionShape.collisionPoints) {
            // Going correct direction?
            if (point.hasIntoNormalComponent(entity.velocity)) {
                // Check if point is inside a tile
                Vector2 vertex = point.getPosition(entity.position);
                Coordinates pointCoords = getCoordsFromVector(vertex);
                if (worldMap.get(pointCoords) != null) {
                    // Possible collision, time to project to normal, and test axis
                    float entityProj = point.getMaxProjection(entity.position, entity.collisionShape.points);
                    float tileProj = point.getMinProjection(
                            new Vector2(pointCoords.x, pointCoords.y),
                            new Vector2[]{
                                    new Vector2(0, 0),
                                    new Vector2(0, 1),
                                    new Vector2(1, 0),
                                    new Vector2(1, 1)});

                    if (entityProj > tileProj) {
                        entity.position.add(point.getScaledNormal(tileProj - entityProj));
                        point.removeIntoNormalComponent(entity.velocity);
                        if (point.normal.dot(gravity) > 0.0f) {
                            entity.canJump = true;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void render() {
        DebugRenderer.getInstance().renderWorld(this);
    }
}
