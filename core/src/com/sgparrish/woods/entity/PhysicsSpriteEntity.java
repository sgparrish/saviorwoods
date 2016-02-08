package com.sgparrish.woods.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.sgparrish.woods.physics.Collidable;
import com.sgparrish.woods.physics.CollisionListener;

import java.util.HashMap;
import java.util.Map;

public abstract class PhysicsSpriteEntity implements Entity, CollisionListener {
    protected Collidable body;
    protected Map<String, Animation> animationMap;

    public PhysicsSpriteEntity() {
        super();
        body = new Collidable(this);
        animationMap = new HashMap<String, Animation>();
    }
}
