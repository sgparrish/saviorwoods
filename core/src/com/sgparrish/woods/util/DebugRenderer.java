package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.Coordinates;
import com.sgparrish.woods.entity.PhysicsEntity;
import com.sgparrish.woods.entity.World;

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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                (int) physicsEntity.getBottom().x,
                (int) physicsEntity.getBottom().y,
                1,
                1,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                (int) physicsEntity.getTop().x,
                (int) physicsEntity.getTop().y,
                1,
                1,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                (int) physicsEntity.getLeft().x,
                (int) physicsEntity.getLeft().y,
                1,
                1,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                (int) physicsEntity.getRight().x,
                (int) physicsEntity.getRight().y,
                1,
                1,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR,
                PLAYER_TILE_COLOR);
        shapeRenderer.end();
    }

    public void renderWorld(World world) {
        for(Coordinates c : world.worldMap.keySet()) {
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