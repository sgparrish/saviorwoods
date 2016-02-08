package com.sgparrish.woods.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.PhysicsSpriteEntity;
import com.sgparrish.woods.physics.Collidable;
import com.sgparrish.woods.physics.Contact;

public class PlayerEntity extends PhysicsSpriteEntity {

    private boolean facingRight;

    public PlayerEntity() {
        super();

        facingRight = true;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
    }

    @Override
    public void collision(Collidable other, Vector2 normal, Contact contact) {

    }
}
