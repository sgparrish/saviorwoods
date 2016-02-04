package com.sgparrish.woods.entity.brush;

import com.sgparrish.woods.entity.Entity;

public class BrushEntity extends Entity {

    public BrushEntity() {
        super();

        add(new BrushPhysicsComponent(this));
    }
}
