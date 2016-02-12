package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.TileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Body {

    public final Vector2 position;
    public final Vector2 velocity;
    public List<CollisionShape> collisionShapes;
    public CollisionListener listener;


    public Body() {
        velocity = new Vector2();
        position = new Vector2();
        collisionShapes = new ArrayList<CollisionShape>();
    }

    public void tileCollision(TileEntity tileEntity, Vector2 normal) {
        if(listener != null) {
            listener.tileCollision(tileEntity, normal);
        }
    }

    public void bodyCollision(Body body, Vector2 normal) {
        if(listener != null) {
            listener.bodyCollision(body, normal);
        }
    }

    public List<Vector2> getPoints() {
        List<Vector2> points = new ArrayList<Vector2>();
        for(CollisionShape collisionShape : collisionShapes) {
            Collections.addAll(points, collisionShape.points);
        }
        return points;
    }

    public List<CollisionPoint> getCollisionPoints() {
        List<CollisionPoint> collisionPoints = new ArrayList<CollisionPoint>();
        for(CollisionShape collisionShape : collisionShapes) {
            Collections.addAll(collisionPoints, collisionShape.collisionPoints);
        }
        return collisionPoints;
    }
}
