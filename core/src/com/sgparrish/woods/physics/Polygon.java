package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

public class Polygon implements Shape {

    // Points must be defined in CCW order (right -> top -> left -> bottom)
    public Vector2[] points;
    public Vector2[] normals;

    private Polygon(int numPoints) {
        points = new Vector2[numPoints];
    }

    public Polygon(Vector2[] points) {
        this.points = points;
        generateNormals();
    }

    private void generateNormals() {
        normals = new Vector2[points.length];
        for (int i = 0; i < points.length; i++) {
            Vector2 point1 = points[i];
            Vector2 point2 = points[(i + 1) % points.length];
            Vector2 normal = new Vector2(point2).sub(point1);
            normal.rotate90(-1);
            normal.nor();
            normals[i] = normal;
        }
    }

    @Override
    public void translate(Vector2 distance) {
        for (Vector2 point : points) {
            point.add(distance);
        }
    }

    @Override
    public void scale(Vector2 scale) {
        for (Vector2 point : points) {
            point.scl(scale);
        }
    }

    @Override
    public Range projectOntoAxis(Vector2 position, Vector2 axis) {
        Range range = new Range();
        for (Vector2 point : points) {
            range.add(new Vector2(point).add(position).dot(axis));
        }
        return range;
    }

    @Override
    public List<Vector2> getNormals(Vector2 position, Shape other) {
        return Arrays.asList(normals);
    }

    public static Polygon getSquare(float width, float height) {

        Polygon square = new Polygon(4);
        square.points[0] = new Vector2(+0.5f, +0.5f);
        square.points[1] = new Vector2(-0.5f, +0.5f);
        square.points[2] = new Vector2(-0.5f, -0.5f);
        square.points[3] = new Vector2(+0.5f, -0.5f);

        square.scale(new Vector2(width, height));
        square.generateNormals();

        return square;
    }
}
