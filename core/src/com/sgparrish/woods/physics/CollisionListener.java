package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.TileEntity;

public interface CollisionListener {

    void tileCollision(TileEntity tileEntity, Vector2 normal);

    void bodyCollision(Body body, Vector2 normal);

}
