package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface Shape {

    void translate(Vector2 distance);

    void scale(Vector2 scale);

    Range projectOntoAxis(Vector2 position, Vector2 axis);

    List<Vector2> getNormals(Vector2 position, Shape other);
}
