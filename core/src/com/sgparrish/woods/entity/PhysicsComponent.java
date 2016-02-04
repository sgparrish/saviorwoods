package com.sgparrish.woods.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

public abstract class PhysicsComponent extends Component {

    public Body body;

    public PhysicsComponent(Entity parent) {
        super(parent);
    }

    public abstract void beginContact(Contact contact, Entity other, boolean imA);

    public abstract void endContact(Contact contact, Entity other, boolean imA);
}
