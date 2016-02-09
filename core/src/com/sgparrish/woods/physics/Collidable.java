package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collidable {

    public static int collisions = 0;

    public Collidable() {
        this(null);
    }

    public Collidable(CollisionListener listener) {
        active = true;
        solid = true;
        position = new Vector2();
        velocity = new Vector2();
        dimension = new Vector2();
        properties = new MaterialProperties();
        this.listener = listener;
    }

    public boolean active;
    public boolean solid;
    public final Vector2 position; // NOTE: Position is bottom left corner -- does this matter? math is confusing sometimes.
    public final Vector2 velocity;
    public final Vector2 dimension;
    public MaterialProperties properties;
    public CollisionListener listener;

    public void applyVelocity(float t, float delta) {
        if (velocity.x != 0.0f || velocity.y != 0.0f) {
            position.add(velocity.x * t * delta, velocity.y * t * delta);
        }
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

    public void collision(Collidable other, Vector2 normal, Contact contact) {

        if (listener != null) {
            listener.collision(other, normal, contact);
            collisions += 1;
            //System.out.println("collision " + collisions);
        }

        if (solid && other.solid) {
            // Remove velocity component in direction that collision occurred
            Vector2 orthogonalToNormal = new Vector2(normal).rotate90(1);

            float normalComponent = Math.abs(velocity.dot(normal));
            float orthoComponent = velocity.dot(orthogonalToNormal);

            normalComponent *= properties.getElasticity(other.properties);
            orthoComponent *= properties.getFriction(other.properties);

            velocity.set(
                    normal.x * normalComponent + orthogonalToNormal.x * orthoComponent,
                    normal.y * normalComponent + orthogonalToNormal.y * orthoComponent
            );
        }
    }
}
