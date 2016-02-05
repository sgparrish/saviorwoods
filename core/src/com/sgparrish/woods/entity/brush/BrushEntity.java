package com.sgparrish.woods.entity.brush;

import com.badlogic.gdx.physics.box2d.Contact;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsFactory;
import com.sgparrish.woods.entity.PhysicsSpriteEntity;

public class BrushEntity extends PhysicsSpriteEntity {

    public BrushEntity() {
        super();

        body = PhysicsFactory.getBrushBody();
        body.setUserData(this);
    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }
}
