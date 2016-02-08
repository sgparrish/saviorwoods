package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionDatum {
    public Collidable collidable;
    public Rectangle aabb;
    public boolean regenPairs;

    public CollisionDatum(Collidable collidable, float delta) {
        this.collidable = collidable;
        regenPairs = false;
        aabb = collidable.getBroadPhaseAABB(1.0f, delta);
    }

    public void refreshAABB(float timeRemaining, float delta) {
        Rectangle newAABB = collidable.getBroadPhaseAABB(timeRemaining, delta);
        if (!aabb.contains(newAABB) && !aabb.equals(newAABB)) {
            aabb = newAABB;
            regenPairs = true;
        }
    }

    public Vector2 getVelocity(float timeRemaining, float delta) {
        return new Vector2(collidable.velocity.x * timeRemaining * delta,
                collidable.velocity.y * timeRemaining * delta);
    }
}
