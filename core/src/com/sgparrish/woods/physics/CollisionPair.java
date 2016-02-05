package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionPair implements Comparable<CollisionPair> {
    public CollisionDatum datumA;
    public CollisionDatum datumB;
    public Rectangle minkowskiShape;
    public Vector2 minkowskiVelocity;
    public float intersectionT;

    public CollisionPair(CollisionDatum datumA, CollisionDatum datumB) {
        this.datumA = datumA;
        this.datumB = datumB;

        runCollisionCheck();
    }

    public void runCollisionCheck() {
        minkowskiShape = generateMinkowskiShape();
        minkowskiVelocity = generateMinkowskiVelocity();
        intersectionT = getIntersectionTime();
    }

    private Rectangle generateMinkowskiShape() {
        Rectangle shape = new Rectangle();
        Vector2 position = datumB.collidable.position.sub(datumA.collidable.position);
        Vector2 dimension = datumA.collidable.dimension.add(datumB.collidable.dimension);
        shape.setPosition(position);
        shape.setSize(dimension.x, dimension.y);
        return shape;
    }

    private Vector2 generateMinkowskiVelocity() {
        return datumA.collidable.velocity.add(datumB.collidable.velocity);
    }

    private float getIntersectionTime() {
        float minT = -1;
        float t;
        // Test against left edge
        t = minkowskiShape.x / minkowskiVelocity.x;
        if (t >= 0 && t <= 1) {
            minT = Math.min(minT, t);
        }
        // Test against right edge
        t = (minkowskiShape.x + minkowskiShape.width) / minkowskiVelocity.x;
        if (t >= 0 && t <= 1) {
            minT = Math.min(minT, t);
        }
        // Test against top edge
        t = minkowskiShape.y / minkowskiVelocity.y;
        if (t >= 0 && t <= 1) {
            minT = Math.min(minT, t);
        }
        // Test against bottom edge
        t = (minkowskiShape.y + minkowskiShape.height) / minkowskiVelocity.y;
        if (t >= 0 && t <= 1) {
            minT = Math.min(minT, t);
        }
        return minT;
    }

    @Override
    public int compareTo(CollisionPair o) {
        if (intersectionT < o.intersectionT) {
            return -1;
        } else if (intersectionT > o.intersectionT) {
            return 1;
        } else {
            return 0;
        }
    }
}
