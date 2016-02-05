package com.sgparrish.woods.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.PhysicsSpriteEntity;
import com.sgparrish.woods.util.Accumulator;

import java.util.LinkedList;
import java.util.List;


public class PhysicsManager {

    private static final float TIME_STEP = 1 / 60f;

    private static PhysicsManager ourInstance = new PhysicsManager();
    public static PhysicsManager getInstance() {
        return ourInstance;
    }

    public OrthographicCamera camera;
    public World world;

    private Accumulator accumulator;
    private List<Runnable> postStepRunnables;

    private PhysicsManager() {
        Box2D.init();
    }

    public void start() {
        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280 / 64, 720 / 64);

        // Physics Initialization
        world = new World(new Vector2(0, -60), true);

        // Private members
        accumulator = new Accumulator(TIME_STEP);
        postStepRunnables = new LinkedList<Runnable>();

        // Redirect all contact events to the physics components responsible for them
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                PhysicsSpriteEntity entityA = (PhysicsSpriteEntity) contact.getFixtureA().getBody().getUserData();
                PhysicsSpriteEntity entityB = (PhysicsSpriteEntity) contact.getFixtureB().getBody().getUserData();
                if (entityA != null) {
                    entityA.beginContact(contact, entityB, true);
                }
                if (entityB != null) {
                    entityB.beginContact(contact, entityA, false);
                }
            }

            @Override
            public void endContact(Contact contact) {
                PhysicsSpriteEntity entityA = (PhysicsSpriteEntity) contact.getFixtureA().getBody().getUserData();
                PhysicsSpriteEntity entityB = (PhysicsSpriteEntity) contact.getFixtureB().getBody().getUserData();
                if (entityA != null) {
                    entityA.endContact(contact, entityB, true);
                }
                if (entityB != null) {
                    entityB.endContact(contact, entityA, false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                //System.out.println(fixtureA.getBody().getType().toString() + ", " + fixtureB.getBody().getType().toString());
                return true;
            }
        });
    }

    public void step(float delta) {
        accumulator.accumulate(delta);
        while (accumulator.next()) {
            world.step(TIME_STEP, 6, 2);
            for (Runnable runnable : postStepRunnables) {
                runnable.run();
            }
            postStepRunnables.clear();
        }
    }

    public void runOutsidePhysicsStep(Runnable runnable) {
        postStepRunnables.add(runnable);
    }
}
