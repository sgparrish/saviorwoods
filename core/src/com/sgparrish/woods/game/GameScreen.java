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
        for(int i = 0; i < 20; i++) {
            world.worldMap.put(new Coordinates(i, 0), new TileEntity());
        }
        world.worldMap.put(new Coordinates(5, 1), new TileEntity());

        player.world = world;
        player.position.set(1,1);
        entities.add(player);
        entities.add(world);

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
