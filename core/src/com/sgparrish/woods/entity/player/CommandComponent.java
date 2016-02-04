package com.sgparrish.woods.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sgparrish.woods.entity.Component;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.entity.player.PlayerEntity;

public class CommandComponent extends Component {
    public CommandComponent(Entity parent) {
        super(parent);
    }

    @Override
    public void update(float delta) {
        Body b = parent.<PhysicsComponent>get(PhysicsComponent.class).body;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            b.applyLinearImpulse(new Vector2(-1000000 * delta, 0), b.getPosition(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            b.applyLinearImpulse(new Vector2(1000000 * delta, 0), b.getPosition(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            b.applyLinearImpulse(new Vector2(0, 1000000 * delta), b.getPosition(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            b.applyLinearImpulse(new Vector2(0, -1000000 * delta), b.getPosition(), true);
        }
    }

    @Override
    public void render() {
    }
}
