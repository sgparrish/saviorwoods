package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionDatum {
    public Collidable collidable;
    private Rectangle aabb;
    public boolean regenPairs;
    public float tRemaining;

    public CollisionDatum(Collidable collidable, float delta) {
        this.collidable = collidable;
        regenPairs = false;
        tRemaining = 1.0f;
        aabb = collidable.getBroadPhaseAABB(tRemaining, delta);
    }

    public Rectangle getAABB() {
        return aabb;
    }

    public void refreshAABB(float delta) {
        Rectangle newAABB = collidable.getBroadPhaseAABB(tRemaining, delta);
        if (!aabb.contains(newAABB)) {
            aabb = newAABB;
            regenPairs = true;
        }
    }

    public Vector2 getVelocity(float delta) {
        return new Vector2(collidable.velocity.x * tRemaining * delta,
                collidable.velocity.y * tRemaining * delta);
    }
}
