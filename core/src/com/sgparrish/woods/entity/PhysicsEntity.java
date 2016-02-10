package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.util.DebugRenderer;

public abstract class PhysicsEntity implements Entity {

    public final static float WIDTH = 1.0f;
    public final static float HEIGHT = 1.0f;
    private final static float HALF_WIDTH = WIDTH / 2.0f;
    private final static float HALF_HEIGHT = HEIGHT / 2.0f;

    public boolean onGround;
    public final Vector2 position;
    public final Vector2 velocity;
    public World world;


    public PhysicsEntity() {
        onGround = false;
        velocity = new Vector2();
        position = new Vector2();
    }

    public Vector2 getLeft() {
        return new Vector2(position).add(-HALF_WIDTH, HALF_HEIGHT);
    }

    public Vector2 getRight() {
        return new Vector2(position).add(HALF_WIDTH, HALF_HEIGHT);
    }

    public Vector2 getTop() {
        return new Vector2(position).add(0.0f, HEIGHT);
    }

    public Vector2 getBottom() {
        return new Vector2(position);
    }

    public Rectangle getAABB() {
        return new Rectangle(position.x - HALF_WIDTH, position.y, WIDTH, HEIGHT);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderPhysicsEntity(this, world);
    }
}
