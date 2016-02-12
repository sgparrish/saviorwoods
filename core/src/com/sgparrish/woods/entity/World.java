package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.physics.Body;
import com.sgparrish.woods.physics.CollisionPoint;
import com.sgparrish.woods.util.DebugRenderer;

import java.util.ArrayList;
import java.util.List;

public class World implements Entity {

    public Vector2 gravity;

    public List<Body> bodies;
    public BiMap<GridPoint2, TileEntity> worldMap;

    public World() {
        gravity = new Vector2(0, -0.05f);
        bodies = new ArrayList<Body>();
        worldMap = HashBiMap.create();
    }

    public GridPoint2 getPointFromTile(TileEntity tileEntity) {
        return worldMap.inverse().get(tileEntity);
    }

    public TileEntity getTileFromPoint(GridPoint2 gridPoint) {
        return worldMap.get(gridPoint);
    }

    public GridPoint2 getPointFromVector(Vector2 vector) {
        return new GridPoint2((int) vector.x, (int) vector.y);
    }

    public TileEntity getTileFromVector(Vector2 vector) {
        return worldMap.get(getPointFromVector(vector));
    }

    @Override
    public void update(float delta) {

        // Update all bodies
        for (Body body : bodies) {

            // Move all bodies
            body.position.add(new Vector2(body.velocity).scl(delta));

            // Collide entities with world
            resolveCollision(body);

            body.velocity.add(gravity);
        }
    }

    private void resolveCollision(Body body) {
        // First check collision points
        for (CollisionPoint point : body.getCollisionPoints()) {
            // Going correct direction?
            if (point.hasIntoNormalComponent(body.velocity)) {
                // Check if point is inside a tile
                Vector2 vertex = point.getPosition(body.position);
                GridPoint2 gridPoint = getPointFromVector(vertex);
                if (worldMap.get(gridPoint) != null) {
                    // Possible collision, time to project to normal, and test axis
                    float entityProj = point.getMaxProjection(body.position, body.getPoints());
                    float tileProj = point.getMinProjection(
                            new Vector2(gridPoint.x, gridPoint.y),
                            new Vector2[]{
                                    new Vector2(0, 0),
                                    new Vector2(0, 1),
                                    new Vector2(1, 0),
                                    new Vector2(1, 1)});

                    if (entityProj > tileProj) {
                        body.position.add(point.getScaledNormal(tileProj - entityProj));
                        point.removeIntoNormalComponent(body.velocity);
                        body.tileCollision(worldMap.get(gridPoint), point.normal);
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
