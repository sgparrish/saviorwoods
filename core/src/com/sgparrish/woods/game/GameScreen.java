package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.player.PlayerEntity;
import com.sgparrish.woods.entity.tile.TileEntity;

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
        entities.add(new PlayerEntity());
        entities.add(new TileEntity());


        // Create Ground
        /*
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, 10));
        Body groundBody = physics.world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(physics.camera.viewportWidth, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);

        groundBox.dispose();
*/



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
