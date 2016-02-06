package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Collidable {

    public Collidable() {
        active = true;
        position = new Vector2();
        velocity = new Vector2();
        dimension = new Vector2();
    }

    public boolean active;
    public final Vector2 position; // NOTE: Position is bottom left corner -- does this matter? math is confusing sometimes.
    public final Vector2 velocity;
    public final Vector2 dimension;

    public void applyVelocity(float t, float delta) {
        position.add(velocity.x * t * delta, velocity.y * t * delta);
    }

    public Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, dimension.x, dimension.y);
    }

    public Rectangle getFutureRectangle(float t, float delta) {
        Vector2 scaledVelocity = new Vector2(velocity.x * t * delta, velocity.y * t * delta);
        return new Rectangle(position.x + scaledVelocity.x, position.y + scaledVelocity.y, dimension.x, dimension.y);
    }

    public Rectangle getBroadPhaseAABB(float t, float delta) {
        Rectangle rectangle = getRectangle();
        Rectangle velRect = getFutureRectangle(t, delta);
        return rectangle.merge(velRect);
    }

    public abstract void collision(Collidable other, CollisionPair.CollisionSide side, Contact contact);
}
