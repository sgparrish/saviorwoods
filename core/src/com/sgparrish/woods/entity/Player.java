package com.sgparrish.woods.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.sgparrish.woods.game.PhysicsManager;

public class Player extends Entity {

    private final PhysicsManager physics;

    public Body getBody() {
        return body;
    }

    private final Body body;

    public Player() {
        super();

        physics = PhysicsManager.getInstance();

        // Create body, circle, and fixture
        // (This code makes a circle, I guess)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 300);
        body = physics.world.createBody(bodyDef);
        body.setUserData(this);
        CircleShape circle = new CircleShape();
        circle.setRadius(30f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);


        circle.dispose();

        add(new CommandComponent(this));
    }
}
