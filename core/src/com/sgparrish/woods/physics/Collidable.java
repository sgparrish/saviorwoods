package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collidable {

    public Collidable() {
        active = true;
        solid = true;
        position = new Vector2();
        velocity = new Vector2();
        dimension = new Vector2();
        properties = new MaterialProperties();
    }

    public boolean active;
    public boolean solid;
    public final Vector2 position; // NOTE: Position is bottom left corner -- does this matter? math is confusing sometimes.
    public final Vector2 velocity;
    public final Vector2 dimension;
    public MaterialProperties properties;
    public CollisionListener listener;

    public void applyVelocity(float t, float delta) {
        position.add(velocity.x * t * delta, velocity.y * t * delta);
    }

    public Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, dimension.x, dimension.y);
    }

    public Rectangle getFutureRectangle(float time, float delta) {
        Vector2 scaledVelocity = new Vector2(velocity.x * time * delta, velocity.y * time * delta);
        return new Rectangle(position.x + scaledVelocity.x, position.y + scaledVelocity.y, dimension.x, dimension.y);
    }

    public Rectangle getBroadPhaseAABB(float timeRemaining, float delta) {
        Rectangle rectangle = getRectangle();
        Rectangle velRect = getFutureRectangle(timeRemaining, delta);
        return rectangle.merge(velRect);
    }

    public void collision(Collidable other, CollisionPair.CollisionSide side, Contact contact) {

        listener.collision(other, side, contact);

        if (solid) {
            // Remove velocity component in direction that collision occurred
            switch(side) {
                case LEFT:
                case RIGHT:
                    velocity.x = -velocity.x * properties.getElasticity(other.properties);
                    velocity.y = velocity.y * properties.getFriction(other.properties);
                    break;
                case TOP:
                case BOTTOM:
                    velocity.x = velocity.x * properties.getFriction(other.properties);
                    velocity.y = -velocity.y * properties.getElasticity(other.properties);
                    break;
            }
        }
    }
}
