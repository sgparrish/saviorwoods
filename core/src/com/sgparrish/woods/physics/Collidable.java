package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Collidable {

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 dimension;

    public Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, dimension.x, dimension.y);
    }
    public Rectangle getBroadPhaseAABB() {
        Rectangle aabb = getRectangle();
        //TODO: Fix this
        return aabb;
    }
}
