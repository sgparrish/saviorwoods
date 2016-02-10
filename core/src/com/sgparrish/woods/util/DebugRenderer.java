package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.*;

public class DebugRenderer {

    private static DebugRenderer instance = new DebugRenderer();

    public static final DebugRenderer getInstance() {
        return instance;
    }

    private static final Color TILE_COLOR = Color.YELLOW;
    private static final Color PLAYER_COLOR = Color.RED;
    private static final Color AABB_COLOR = Color.BLUE;
    private static final Color PLAYER_TILE_COLOR = Color.PURPLE;

    private final ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public DebugRenderer() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280 / 64, 720 / 64);
    }

    public void start() {
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void renderPhysicsEntity(PhysicsEntity physicsEntity, World world) {
        Vector2 left = physicsEntity.getLeft();
        Vector2 right = physicsEntity.getRight();
        Vector2 top = physicsEntity.getTop();
        Vector2 bottom = physicsEntity.getBottom();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                bottom.x,
                bottom.y,
                right.x,
                right.y,
                PLAYER_COLOR,
                PLAYER_COLOR
        );
        shapeRenderer.line(
                right.x,
                right.y,
                top.x,
                top.y,
                PLAYER_COLOR,
                PLAYER_COLOR
        );
        shapeRenderer.line(
                top.x,
                top.y,
                left.x,
                left.y,
                PLAYER_COLOR,
                PLAYER_COLOR
        );
        shapeRenderer.line(
                left.x,
                left.y,
                bottom.x,
                bottom.y,
                PLAYER_COLOR,
                PLAYER_COLOR
        );
        shapeRenderer.end();
        Rectangle aabb = physicsEntity.getAABB();
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                physicsEntity.position.x,
                physicsEntity.position.y,
                physicsEntity.position.x + physicsEntity.velocity.x,
                physicsEntity.position.y + physicsEntity.velocity.y,
                PLAYER_COLOR,
                PLAYER_COLOR
                );
        shapeRenderer.end();
    }

    public void renderWorld(World world) {
        for (Coordinates c : world.worldMap.keySet()) {
            renderTile(c.x, c.y, TILE_COLOR);
        }
    }

    public void renderTile(int x, int y, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                x,
                y,
                1,
                1,
                color,
                color,
                color,
                color);
        shapeRenderer.end();
    }
}