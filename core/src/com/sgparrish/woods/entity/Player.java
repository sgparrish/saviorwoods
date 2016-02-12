package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.physics.Body;
import com.sgparrish.woods.physics.CollisionListener;
import com.sgparrish.woods.physics.CollisionShape;
import com.sgparrish.woods.util.DebugRenderer;
import com.sgparrish.woods.util.GameInput;

public class Player implements Entity, CollisionListener {

    public boolean canJump;
    public Body body;

    public Player() {
        canJump = false;
        body = new Body();
        body.collisionShapes.add(CollisionShape.createOctagon(new Vector2(0, 0)));
        body.listener = this;
    }

    @Override
    public void render() {
        DebugRenderer.getInstance().renderBody(body);
    }

    @Override
    public void update(float delta) {

        float vel = 0.05f;
        float value = 0.0f;
        if ((value = GameInput.getCommandValue(GameInput.Commands.LEFT)) != 0) {
            body.velocity.x -= vel;
        } else if ((value = GameInput.getCommandValue(GameInput.Commands.RIGHT)) != 0) {
            body.velocity.x += vel;
        } else {
            // player.velocity.x = 0;
        }
        if (GameInput.getCommandValue(GameInput.Commands.JUMP) > 0 && canJump) {
            body.velocity.y += 5.0f;
        } else if ((value = GameInput.getCommandValue(GameInput.Commands.DOWN)) != 0) {
            body.velocity.y -= vel;
        } else {
            // player.velocity.y = 0;
        }
        canJump = false;
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
}
