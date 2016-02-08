package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.player.PlayerEntity;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private PhysicsManager physics;

    private Box2DDebugRenderer debugRenderer;

    private ArrayList<Entity> entities;

    public GameScreen() {

        physics = PhysicsManager.getInstance();
        physics.start();

        // Physics Initialization
        debugRenderer = new Box2DDebugRenderer();

        entities = new ArrayList<Entity>();
        PlayerEntity player = new PlayerEntity();
        entities.add(player);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        physics.step(delta);
        for (Entity entity : entities) {
            entity.update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Entity entity : entities) {
            entity.render();
        }

        debugRenderer.render(physics.world, physics.camera.combined);
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
