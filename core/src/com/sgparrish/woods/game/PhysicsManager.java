package com.sgparrish.woods.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.entity.util.Accumulator;


public class PhysicsManager {

    private float TIME_STEP = 1 / 60f;

    private static PhysicsManager ourInstance = new PhysicsManager();

    public static PhysicsManager getInstance() {
        return ourInstance;
    }

    public OrthographicCamera camera;

    public World world;

    private Accumulator accumulator;

    private PhysicsManager() {
        Box2D.init();
    }


    public void start() {
        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Physics Initialization
        world = new World(new Vector2(0, /*-6*/0), true);

        accumulator = new Accumulator(TIME_STEP);

        // Redirect all contact events to the physics components responsible for them
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
                PhysicsComponent componentA;
                componentA = (PhysicsComponent) entityA.get(PhysicsComponent.class);
                PhysicsComponent componentB;
                componentB = (PhysicsComponent) entityB.get(PhysicsComponent.class);
                if (componentA != null) {
                    componentA.beginContact(contact, entityB, true);
                }
                if (componentB != null) {
                    componentB.beginContact(contact, entityA, false);
                }
            }

            @Override
            public void endContact(Contact contact) {
                Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
                PhysicsComponent componentA;
                componentA = (PhysicsComponent) entityA.get(PhysicsComponent.class);
                PhysicsComponent componentB;
                componentB = (PhysicsComponent) entityB.get(PhysicsComponent.class);
                if (componentA != null) {
                    componentA.endContact(contact, entityB, true);
                }
                if (componentB != null) {
                    componentB.endContact(contact, entityA, false);
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
        }
    }
}
