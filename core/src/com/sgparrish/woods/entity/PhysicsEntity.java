package com.sgparrish.woods.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.util.DebugRenderer;

public class PhysicsEntity implements Entity {
    public final Vector2 position;
    public final Vector2 velocity;
    public World world;

    public PhysicsEntity() {
        velocity = new Vector2();
        position = new Vector2();
    }

    @Override
    public void update(float delta) {
        float vel = 0.005f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            velocity.x -= vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            velocity.x += vel;
        } else {
            // player.velocity.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            velocity.y += vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            velocity.y -= vel;
        } else {
            // player.velocity.y = 0;
        }

        velocity.y -= 0.0001;

        position.add(velocity);

        if (world.getFromVector(position) != null) {
            position.y = (int) (position.y + 1);
            velocity.y = 0;
        }
    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderPhysicsEntity(this);
    }
}
