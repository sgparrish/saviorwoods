package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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

    public void renderPhysicsEntity(PhysicsEntity entity, World world) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < entity.collisionShape.points.length; i++) {
            int j = (i + 1) % entity.collisionShape.points.length;
            shapeRenderer.line(
                    entity.collisionShape.points[i].x + entity.position.x,
                    entity.collisionShape.points[i].y + entity.position.y,
                    entity.collisionShape.points[j].x + entity.position.x,
                    entity.collisionShape.points[j].y + entity.position.y,
                    PLAYER_COLOR,
                    PLAYER_COLOR
            );
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                entity.position.x,
                entity.position.y,
                entity.position.x + entity.velocity.x,
                entity.position.y + entity.velocity.y,
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