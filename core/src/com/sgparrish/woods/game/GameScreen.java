package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sgparrish.woods.entity.*;
import com.sgparrish.woods.util.DebugRenderer;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private ArrayList<Entity> entities;

    public GameScreen() {
        entities = new ArrayList<Entity>();
        Player player = new Player();
        World world = new World();
        for (int x = 0; x < 20; x++) {
            world.worldMap.put(new Coordinates(x, 0), new TileEntity());
            world.worldMap.put(new Coordinates(x, 10), new TileEntity());
        }
        for (int y = 1; y < 10; y++) {
            world.worldMap.put(new Coordinates(0, y), new TileEntity());
            world.worldMap.put(new Coordinates(19, y), new TileEntity());
        }
        world.worldMap.put(new Coordinates(5, 1), new TileEntity());
        world.worldMap.put(new Coordinates(5, 9), new TileEntity());
        world.worldMap.put(new Coordinates(7, 1), new TileEntity());
        world.worldMap.put(new Coordinates(7, 2), new TileEntity());
        world.worldMap.put(new Coordinates(7, 3), new TileEntity());
        world.worldMap.put(new Coordinates(7, 4), new TileEntity());
        world.worldMap.put(new Coordinates(7, 9), new TileEntity());
        world.worldMap.put(new Coordinates(9, 1), new TileEntity());
        world.worldMap.put(new Coordinates(9, 9), new TileEntity());

        world.bodies.add(player.body);
        player.body.position.set(1.5f, 1);
        entities.add(world);
        entities.add(player);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        DebugRenderer.getInstance().start();
        for (Entity entity : entities) {
            entity.render();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
