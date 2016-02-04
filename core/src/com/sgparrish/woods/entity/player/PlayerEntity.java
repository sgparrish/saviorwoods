package com.sgparrish.woods.entity.player;

import com.sgparrish.woods.entity.Entity;

public class PlayerEntity extends Entity {

    public PlayerEntity() {
        super();
        add(new PlayerPhysicsComponent(this));
        add(new CommandComponent(this));
    }
}
