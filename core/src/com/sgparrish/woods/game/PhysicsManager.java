package com.sgparrish.woods.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;


public class PhysicsManager {
    private static PhysicsManager ourInstance = new PhysicsManager();

    public static PhysicsManager getInstance() {
        return ourInstance;
    }

    private PhysicsManager() {
        Box2D.init();
    }

    public OrthographicCamera camera;
    public World world;

    public void start() {
        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Physics Initialization
        world = new World(new Vector2(0, -60), true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
                PhysicsComponent componentA;
                componentA = (PhysicsComponent) entityA.get(PhysicsComponent.class);
                PhysicsComponent componentB;
                componentB = (PhysicsComponent) entityB.get(PhysicsComponent.class);
                componentA.beginContact(contact);
                componentB.beginContact(contact);
            }

            @Override
            public void endContact(Contact contact) {
                Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
                PhysicsComponent componentA;
                componentA = (PhysicsComponent) entityA.get(PhysicsComponent.class);
                PhysicsComponent componentB;
                componentB = (PhysicsComponent) entityB.get(PhysicsComponent.class);
                componentA.endContact(contact);
                componentB.endContact(contact);
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
                System.out.println(fixtureA.getBody().getType().toString() + ", " + fixtureB.getBody().getType().toString());
                return true;
            }
        });
    }
}
