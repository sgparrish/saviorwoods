package com.sgparrish.woods.entity.player;

import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.game.PhysicsManager;

public class PlayerPhysicsComponent extends PhysicsComponent {

    private final PhysicsManager physics;

    private static final CircleShape shape = new CircleShape();
    static {
        shape.setRadius(30f);
    }

    public PlayerPhysicsComponent(Entity parent) {
        super(parent);

        physics = PhysicsManager.getInstance();

        // Create body, circle, and fixture
        // (This code makes a circle, I guess)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 300);
        body = physics.world.createBody(bodyDef);
        body.setUserData(this.parent);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }
}
