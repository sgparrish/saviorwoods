package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionPair implements Comparable<CollisionPair> {

    public enum CollisionSide {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

        public CollisionSide getOpposite() {
            if (this == LEFT) {
                return RIGHT;
            } else if (this == RIGHT) {
                return LEFT;
            } else if (this == TOP) {
                return BOTTOM;
            } else if (this == BOTTOM) {
                return TOP;
            }
            return null;
        }
    }

    public CollisionDatum datumA;
    public CollisionDatum datumB;
    public Rectangle minkowskiShape;
    public Vector2 minkowskiVelocity;
    public CollisionSide side;
    public float collisionTime;

    public CollisionPair(CollisionDatum datumA, CollisionDatum datumB, float timeRemaining, float delta) {
        this.datumA = datumA;
        this.datumB = datumB;

        runCollisionCheck(timeRemaining, delta);
    }

    public void runCollisionCheck(float timeRemaining, float delta) {
        minkowskiShape = generateMinkowskiShape();
        minkowskiVelocity = generateMinkowskiVelocity(timeRemaining, delta);
        collisionTime = getCollisionTime(timeRemaining);
    }

    private Rectangle generateMinkowskiShape() {
        Rectangle shape = new Rectangle();
        Vector2 position = new Vector2(datumB.collidable.position).sub(datumA.collidable.position);
        position.sub(datumA.collidable.dimension);
        Vector2 dimension = new Vector2(datumA.collidable.dimension).add(datumB.collidable.dimension);
        shape.setPosition(position);
        shape.setSize(dimension.x, dimension.y);
        return shape;
    }

    private Vector2 generateMinkowskiVelocity(float timeRemaining, float delta) {
        return new Vector2(datumA.getVelocity(timeRemaining, delta)).sub(datumB.getVelocity(timeRemaining, delta));
    }

    private float getCollisionTime(float timeRemaining) {
        // This method does the actual minkowski collision check
        float minT = 2;
        float t, x, y;
        float left = minkowskiShape.x;
        float right = minkowskiShape.x + minkowskiShape.width;
        float top = minkowskiShape.y + minkowskiShape.height;
        float bottom = minkowskiShape.y;
        // Test against left edge
        t = left / minkowskiVelocity.x; // Get the collisionTime that velocity would reach the left edge
        if (t >= 0 && t <= timeRemaining) { // if that collisionTime within [0, timeRemaining] (aka during this collisionTime step)
            y = t * minkowskiVelocity.y; // calculate the y value for that collisionTime
            if (y >= bottom && y <= top && t < minT) { // if that y value is on the edge, and this is the smallest t yet
                minT = t;
                side = CollisionSide.LEFT;
            }
        }
        // Test against right edge
        t = right / minkowskiVelocity.x; // Get the collisionTime that velocity would reach the right edge
        if (t >= 0 && t <= timeRemaining) { // if that collisionTime within [0, timeRemaining] (aka during this collisionTime step)
            y = t * minkowskiVelocity.y; // calculate the y value for that collisionTime
            if (y >= bottom && y <= top && t < minT) { // if that y value is on the edge, and this is the smallest t yet
                minT = t;
                side = CollisionSide.RIGHT;
            }
        }
        // Test against top edge
        t = top / minkowskiVelocity.y; // Get the collisionTime that velocity would reach the top edge
        if (t >= 0 && t <= timeRemaining) { // if that collisionTime within [0, timeRemaining] (aka during this collisionTime step)
            x = t * minkowskiVelocity.x; // calculate the x value for that collisionTime
            if (x >= left && x <= right && t < minT) { // if that x value is on the edge, and this is the smallest t yet
                minT = t;
                side = CollisionSide.TOP;
            }
        }
        // Test against bottom edge
        t = bottom / minkowskiVelocity.y; // Get the collisionTime that velocity would reach the bottom edge
        if (t >= 0 && t <= timeRemaining) { // if that collisionTime within [0, timeRemaining] (aka during this collisionTime step)
            x = t * minkowskiVelocity.x; // calculate the x value for that collisionTime
            if (x >= left && x <= right && t < minT) { // if that x value is on the edge, and this is the smallest t yet
                minT = t;
                side = CollisionSide.BOTTOM;
            }
        }
        return minT;
    }

    @Override
    public int compareTo(CollisionPair o) {
        if (collisionTime < o.collisionTime) {
            return -1;
        } else if (collisionTime > o.collisionTime) {
            return 1;
        } else {
            return 0;
        }
    }
}
