package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sgparrish.woods.entity.*;

public class DebugRenderer {

    private static DebugRenderer instance = new DebugRenderer();

    public static final DebugRenderer getInstance() {
        return  instance;
    }

    private static final Color TILE_COLOR = Color.YELLOW;
    private static final Color PLAYER_COLOR = Color.RED;
    private static final Color PLAYER_TILE_COLOR = Color.BLUE;

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

    public void renderPhysicsEntity(PhysicsEntity physicsEntity) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                physicsEntity.position.x,
                physicsEntity.position.y,
                1,
                1,
                PLAYER_COLOR,
                PLAYER_COLOR,
                PLAYER_COLOR,
                PLAYER_COLOR);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                (int) physicsEntity.position.x,
                (int) physicsEntity.position.y,
                1,
                1,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR);
        shapeRenderer.end();
    }

    public void renderWorld(World world) {
        for(Coordinate c : world.worldMap.keySet()) {
            renderTile(c.x, c.y);
        }
    }

    public void renderTile(int x, int y) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                x,
                y,
                1,
                1,
                TILE_COLOR,
                TILE_COLOR,
                TILE_COLOR,
                TILE_COLOR);
        shapeRenderer.end();
    }
}