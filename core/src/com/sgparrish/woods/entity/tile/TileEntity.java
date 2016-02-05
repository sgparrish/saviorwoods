package com.sgparrish.woods.entity.tile;

import com.sgparrish.woods.entity.PhysicsSpriteEntity;

public abstract class TileEntity extends PhysicsSpriteEntity {
    protected TileMapEntity tileMapEntity;
    protected boolean canFall;

    public TileEntity() {
        super();
        canFall = true;
    }

    public boolean canFall() {
        return canFall;
    }

    public void setTileMapEntity(TileMapEntity tileMapEntity) {
        this.tileMapEntity = tileMapEntity;
    }
}
