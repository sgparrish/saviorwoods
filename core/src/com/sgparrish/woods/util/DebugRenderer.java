package com.sgparrish.woods.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sgparrish.woods.entity.Coordinate;
import com.sgparrish.woods.entity.Player;
import com.sgparrish.woods.entity.TileEntity;
import com.sgparrish.woods.entity.World;

public class DebugRenderer {

    private static DebugRenderer instance = new DebugRenderer();

    public static final DebugRenderer getInstance() {
        return  instance;
    }

    private static final Color TILE_COLOR = Color.YELLOW;
    private static final Color PLAYER_COLOR = Color.RED;
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

    public void renderPlayer(Player player) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                player.position.x,
                player.position.y,
                1,
                1,
                PLAYER_COLOR,
                PLAYER_COLOR,
                PLAYER_COLOR,
                PLAYER_COLOR);
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