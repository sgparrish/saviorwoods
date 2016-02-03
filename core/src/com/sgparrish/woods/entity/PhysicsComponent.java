package com.sgparrish.woods.entity;

import com.badlogic.gdx.physics.box2d.Contact;

public abstract class PhysicsComponent extends Component {

    public PhysicsComponent(Entity parent) {
        super(parent);
    }

    public abstract void beginContact(Contact contact);

    public abstract void endContact(Contact contact);
}
