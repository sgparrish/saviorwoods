package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.util.DebugRenderer;

public abstract class PhysicsEntity implements Entity {

    public final static float WIDTH = 1.0f;
    public final static float HEIGHT = 1.0f;
    private final static float HALF_WIDTH = WIDTH / 2.0f;
    private final static float HALF_HEIGHT = HEIGHT / 2.0f;

    public boolean canJump;
    public final Vector2 position;
    public final Vector2 velocity;
    public CollisionShape collisionShape;
    public World world;


    public PhysicsEntity() {
        canJump = false;
        velocity = new Vector2();
        position = new Vector2();
        collisionShape = CollisionShape.createOctagon(WIDTH, HEIGHT);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderPhysicsEntity(this, world);
    }
}
