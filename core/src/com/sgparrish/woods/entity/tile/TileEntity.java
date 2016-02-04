package com.sgparrish.woods.entity.tile;

import com.sgparrish.woods.entity.Entity;

public class TileEntity extends Entity {
    public TileEntity() {
        super();

        add(new TilePhysicsEntity(this));
    }

}
