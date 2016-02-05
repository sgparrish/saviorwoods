package com.sgparrish.woods.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.sgparrish.woods.game.PhysicsManager;

import java.util.HashMap;
import java.util.Map;

public abstract class PhysicsSpriteEntity implements Entity {
    protected final PhysicsManager physics = PhysicsManager.getInstance();
    protected Body body;
    protected Map<String, Animation> animationMap;

    public PhysicsSpriteEntity() {
        super();
        animationMap = new HashMap<String, Animation>();
    }

    public abstract void beginContact(Contact contact, Entity other, boolean imA);

    public abstract void endContact(Contact contact, Entity other, boolean imA);

    public Body getBody() {
        return body;
    }
}
