package com.sgparrish.woods.physics;

public interface CollisionListener {

    void collision(Collidable other, CollisionPair.CollisionSide side, Contact contact);
}
