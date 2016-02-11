package com.sgparrish.woods.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends PhysicsEntity {

    public Player() {
        super();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void update(float delta) {

        float vel = 0.05f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            velocity.x -= vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            velocity.x += vel;
        } else {
            // player.velocity.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && canJump) {
            velocity.y += 5.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            velocity.y -= vel;
        } else {
            // player.velocity.y = 0;
        }

        super.update(delta);
    }
}
