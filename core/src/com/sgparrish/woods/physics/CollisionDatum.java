package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;

public class CollisionDatum {
    public Collidable collidable;
    private Rectangle aabb;
    public boolean aabbDirty;
    public float tRemaining;

    public CollisionDatum(Collidable collidable) {
        this.collidable = collidable;
        aabb = collidable.getBroadPhaseAABB();
        aabbDirty = false;
        tRemaining = 1.0f;
    }

    public Rectangle getAabb() {
        if (aabbDirty) {
            aabb = collidable.getBroadPhaseAABB();
        }
        return aabb;
    }

}
