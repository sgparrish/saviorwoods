package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;

public interface CollisionListener {

    void collision(Collidable other, Vector2 force, Vector2 normal, Contact contact);
}
