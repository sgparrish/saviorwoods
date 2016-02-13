package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.physics.Body;
import com.sgparrish.woods.physics.CollisionListener;
import com.sgparrish.woods.physics.CollisionShape;
import com.sgparrish.woods.util.DebugRenderer;
import com.sgparrish.woods.util.GameInput;

public class Player implements Entity, CollisionListener {

    public static final float GROUND_ACCEL = 3.0f;
    public static final float GROUND_DECEL = 6.0f;
    public static final float AIR_ACCEL = 2.0f;

    public boolean canJump;
    public Body body;

    public Player() {
        canJump = false;
        body = new Body();
        body.collisionShapes.add(CollisionShape.createOctagon(new Vector2(0, 0)));
        body.listener = this;
    }

    @Override
    public void tileCollision(TileEntity tileEntity, Vector2 normal) {
        if (normal.dot(new Vector2(0, -1)) > 0.0f) {
            canJump = true;
        }
    }

    @Override
    public void bodyCollision(Body body, Vector2 normal) {

    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderBody(body);
    }

    @Override
    public void update(float delta) {

        float value;
        if ((value = GameInput.getCommandValue(GameInput.Commands.LEFT)) != 0) {
            if (!canJump) {
                body.velocity.x -= AIR_ACCEL * value * delta;
            } else if (body.velocity.x > 0) {
                body.velocity.x -= GROUND_DECEL * value * delta;
                body.velocity.x = Math.max(0, body.velocity.x);
            } else {
                body.velocity.x -= GROUND_ACCEL * value * delta;
            }
        } else if ((value = GameInput.getCommandValue(GameInput.Commands.RIGHT)) != 0) {
            if (!canJump) {
                body.velocity.x += AIR_ACCEL * value * delta;
            } else if (body.velocity.x < 0) {
                body.velocity.x += GROUND_DECEL * value * delta;
                body.velocity.x = Math.min(0, body.velocity.x);
            } else {
                body.velocity.x += GROUND_ACCEL * value * delta;
            }
        } else {
            // player.velocity.x = 0;
        }
        if (GameInput.getCommandValue(GameInput.Commands.JUMP) > 0 && canJump) {
            body.velocity.y += 5.0f;
        } else if ((value = GameInput.getCommandValue(GameInput.Commands.DOWN)) != 0) {
            //body.velocity.y -= vel * value;
        } else {
            // player.velocity.y = 0;
        }
        canJump = false;
    }
}
