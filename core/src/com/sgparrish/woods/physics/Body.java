package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.List;

public class Body {

    public final Vector2 position;
    public final Vector2 velocity;
    public final List<Vector2> forces;
    public final List<Shape> shapes;
    public CollisionListener listener;

    public Body() {
        velocity = new Vector2();
        position = new Vector2();
        forces = new ArrayList<Vector2>();
        shapes = new ArrayList<Shape>();
        shapes.add(Polygon.getSquare(1, 1));
    }

    public Range projectOntoAxis(Vector2 axis) {
        Range range = new Range();
        for (Shape shape : shapes) {
            range.add(shape.projectOntoAxis(position, axis));
        }
        return range;
    }

    public List<Vector2> getNormals(Body other) {
        List<Vector2> normals = new ArrayList<Vector2>();
        for (Shape otherShape : other.shapes) {
            for (Vector2 normal : getNormals(otherShape)) {
                if (!normals.contains(normal)) {
                    normals.add(normal);
                }
            }
        }
        return normals;
    }

    public List<Vector2> getNormals(Shape otherShape) {
        List<Vector2> normals = new ArrayList<Vector2>();
        for (Shape shape : shapes) {
            for (Vector2 normal : shape.getNormals(position, otherShape)) {
                if (!normals.contains(normal)) {
                    normals.add(normal);
                }
            }
        }
        return normals;
    }

    public Rectangle getAABB() {
        Range xRange = projectOntoAxis(new Vector2(1, 0));
        Range yRange = projectOntoAxis(new Vector2(0, 1));
        return new Rectangle(xRange.min, yRange.min, xRange.getLength(), yRange.getLength());
    }
}
