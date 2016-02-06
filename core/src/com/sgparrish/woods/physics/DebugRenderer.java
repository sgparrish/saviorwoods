package com.sgparrish.woods.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class DebugRenderer {

    private static final Color COLLIDABLE_COLOR = Color.YELLOW;
    private static final Color VELOCITY_COLOR = Color.RED;
    private static final Color AABB_COLOR = Color.BLUE;
    private final ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public DebugRenderer() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280 / 64, 720 / 64);
    }

    public void render(ArrayList<CollisionDatum> collisionData, PriorityQueue<CollisionPair> collisionPairs) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        for (CollisionDatum datum : collisionData) {
            renderDatum(datum);
        }

        if (!collisionPairs.isEmpty()) {
            System.out.println("asdfasdf");
        }
    }

    private void renderDatum(CollisionDatum datum) {
        renderAABB(datum.aabb);
        renderCollidable(datum.collidable);
    }

    private void renderCollidable(Collidable collidable) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                collidable.position.x,
                collidable.position.y,
                collidable.dimension.x,
                collidable.dimension.y,
                COLLIDABLE_COLOR,
                COLLIDABLE_COLOR,
                COLLIDABLE_COLOR,
                COLLIDABLE_COLOR);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                collidable.position.x,
                collidable.position.y,
                collidable.position.x + collidable.velocity.x,
                collidable.position.y + collidable.velocity.y,
                VELOCITY_COLOR,
                VELOCITY_COLOR
        );
        shapeRenderer.end();
    }

    private void renderAABB(Rectangle aabb) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                aabb.x,
                aabb.y,
                aabb.width,
                aabb.height,
                AABB_COLOR,
                AABB_COLOR,
                AABB_COLOR,
                AABB_COLOR);
        shapeRenderer.end();
    }


}
