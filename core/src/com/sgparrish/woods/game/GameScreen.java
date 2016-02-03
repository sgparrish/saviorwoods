package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.sgparrish.woods.entity.Player;

public class GameScreen implements Screen {

    private float TIME_STEP = 1 / 60f;

    private PhysicsManager physics;

    private Box2DDebugRenderer debugRenderer;
    private Player p;

    private Body bodytest;

    public GameScreen() {

        physics = PhysicsManager.getInstance();
        physics.start();

        // Physics Initialization
        debugRenderer = new Box2DDebugRenderer();

        p = new Player();


        // Create Ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, 10));
        Body groundBody = physics.world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(physics.camera.viewportWidth, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);

        groundBox.dispose();

        BodyDef bodyDef;
        Body body;
        PolygonShape square = new PolygonShape();
        square.setAsBox(10, 10);
        FixtureDef fixtureDef;
        PrismaticJointDef prismaticJointDef;
        for (int i = 0; i < 40; i++) {
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(300, 30 + 20 * i);
            body = physics.world.createBody(bodyDef);
            body.setUserData(this);

            fixtureDef = new FixtureDef();
            fixtureDef.shape = square;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.0f;
            body.createFixture(fixtureDef);
            body.setFixedRotation(true);
            body.setGravityScale(0.5f);

            prismaticJointDef = new PrismaticJointDef();
            prismaticJointDef.bodyA = groundBody;
            prismaticJointDef.bodyB = body;
            prismaticJointDef.localAxisA.x = 0;
            prismaticJointDef.localAxisA.y = 1;
            prismaticJointDef.localAnchorA.x = 300;
            prismaticJointDef.collideConnected = true;
            physics.world.createJoint(prismaticJointDef);
        }

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(200, 600);
        bodyDef.allowSleep = true;
        bodytest = physics.world.createBody(bodyDef);
        bodytest.setUserData(this);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = square;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        PolygonShape square2 = new PolygonShape();
        square2.setAsBox(10, 10, new Vector2(0, 20), 0);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = square2;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;


        bodytest.createFixture(fixtureDef);
        bodytest.createFixture(fixtureDef2);
        bodytest.setAwake(false);
        bodytest.setFixedRotation(true);
        bodytest.setGravityScale(0.5f);

        square.dispose();
        square2.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // TODO: accumulator or something to do a physics step every 1/60th of a second, instead of every call of render
        physics.world.step(TIME_STEP, 6, 2);
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
