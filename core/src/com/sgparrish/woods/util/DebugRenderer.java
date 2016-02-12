package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.physics.*;
import com.sgparrish.woods.entity.World;

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

    public void renderBody(Body body) {
        for (Shape shape : body.shapes) {
            if (shape instanceof Polygon) {
                renderPolygon((Polygon) shape, body.position);
            }
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                body.position.x,
                body.position.y,
                body.position.x + body.velocity.x,
                body.position.y + body.velocity.y,
                PLAYER_COLOR,
                PLAYER_COLOR
        );
        shapeRenderer.end();
    }

    public void renderPolygon(Polygon shape, Vector2 position) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < shape.points.length; i++) {
            int j = (i + 1) % shape.points.length;
            shapeRenderer.line(
                    shape.points[i].x + position.x,
                    shape.points[i].y + position.y,
                    shape.points[j].x + position.x,
                    shape.points[j].y + position.y,
                    PLAYER_COLOR,
                    PLAYER_COLOR
            );
        }
        shapeRenderer.end();
    }

    public void renderWorld(World world) {
        for (GridPoint2 gridPoint : world.worldMap.keySet()) {
            renderTile(gridPoint.x, gridPoint.y, TILE_COLOR);
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