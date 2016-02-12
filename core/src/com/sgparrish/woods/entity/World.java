package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.physics.Body;
import com.sgparrish.woods.physics.Polygon;
import com.sgparrish.woods.physics.Range;
import com.sgparrish.woods.physics.Shape;
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
        for (Shape shape : getTileShapes(body.getAABB())) {

            float minPenetration = Float.POSITIVE_INFINITY;
            Vector2 minNormal = null;

            for (Vector2 normal : body.getNormals(shape)) {
                Range bodyRange = body.projectOntoAxis(normal);
                Range shapeRange = shape.projectOntoAxis(new Vector2(0,0), normal);
                Range overlap = bodyRange.overlap(shapeRange);
                if (overlap == null) {
                    break;
                } else {
                    minNormal = normal;
                    minPenetration = Math.min(minPenetration, overlap.getLength());
                }
            }

            // Collision, so move out of shape and remove velocity into normal
            if (minNormal != null) {
                body.position.add(new Vector2(minNormal).scl(minPenetration));
                float velocityDot = body.velocity.dot(minNormal);
                if(velocityDot > 0) {
                    body.velocity.add(new Vector2(minNormal).scl(Math.abs(velocityDot)));
                }
            }
        }
    }

    private List<Shape> getTileShapes(Rectangle aabb) {
        List<Shape> shapes = new ArrayList<Shape>();
        GridPoint2 start = getPointFromVector(new Vector2(aabb.x, aabb.y));
        GridPoint2 end = getPointFromVector(new Vector2(aabb.x + aabb.width, aabb.y + aabb.height));
        for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
                if(worldMap.get(new GridPoint2(x, y)) != null) {
                    Shape shape = Polygon.getSquare(1, 1);
                    shape.translate(new Vector2(x, y));
                    shapes.add(shape);
                }
            }
        }
        return shapes;
    }


    @Override
    public void render() {
        DebugRenderer.getInstance().renderWorld(this);
    }
}
