package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

public class CollisionPoint {
    public final Vector2 offset;
    public final Vector2 normal;

    public CollisionPoint(Vector2 offset, Vector2 normal) {
        this.offset = offset;
        this.normal = normal;
    }

    public Vector2 getPosition(Vector2 position) {
        return new Vector2(offset).add(position);
    }

    public Vector2 projectOntoNormal(Vector2 vector) {
        return new Vector2(normal).scl(Math.abs(normal.dot(vector)));
    }

    public Vector2 getScaledNormal(float scale) {
        return new Vector2(normal).scl(scale);
    }

    public boolean hasIntoNormalComponent(Vector2 vector) {
        return normal.dot(vector) > 0.0f;
    }

    public void removeIntoNormalComponent(Vector2 vector) {
        float dot = vector.dot(normal);
        if (dot > 0.0f) {
            // Since normal is a unit vector,
            Vector2 normalComponent = new Vector2(normal).scl(-Math.abs(dot));
            vector.add(normalComponent);
        }
    }

    public float getMinProjection(Vector2 position, List<Vector2> vertices) {
        float minProjection = Float.POSITIVE_INFINITY;
        for (Vector2 vertex : vertices) {
            Vector2 offsetVertex = new Vector2(vertex).add(position);
            minProjection = Math.min(minProjection, normal.dot(offsetVertex));
        }
        return minProjection;
    }

    public float getMinProjection(Vector2 position, Vector2[] vertices) {
        return getMinProjection(position, Arrays.asList(vertices));
    }

    public float getMaxProjection(Vector2 position, List<Vector2> vertices) {
        float maxProjection = Float.NEGATIVE_INFINITY;
        for (Vector2 vertex : vertices) {
            Vector2 offsetVertex = new Vector2(vertex).add(position);
            maxProjection = Math.max(maxProjection, normal.dot(offsetVertex));
        }
        return maxProjection;
    }

    public float getMaxProjection(Vector2 position, Vector2[] vertices) {
        return getMaxProjection(position, Arrays.asList(vertices));
    }
}
