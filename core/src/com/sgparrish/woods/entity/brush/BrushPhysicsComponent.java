package com.sgparrish.woods.entity.brush;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.game.PhysicsManager;

public class BrushPhysicsComponent extends PhysicsComponent {

    private static final PolygonShape shape = new PolygonShape();
    private static final BodyDef bodyDef = new BodyDef();
    static {
        shape.setAsBox(640, 30, new Vector2(640, -30f), 0.0f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    private final PhysicsManager physics;

    public BrushPhysicsComponent(Entity parent) {
        super(parent);

        physics = PhysicsManager.getInstance();

        body = physics.world.createBody(bodyDef);
        body.createFixture(shape, 0.0f);
        body.setUserData(parent);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {

    }
}
