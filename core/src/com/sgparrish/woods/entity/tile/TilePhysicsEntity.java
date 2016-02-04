package com.sgparrish.woods.entity.tile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.entity.util.Accumulator;
import com.sgparrish.woods.game.PhysicsManager;

public class TilePhysicsEntity extends PhysicsComponent {

    private final Accumulator accumulator;

    private final PhysicsManager physics;

    private boolean canMove;

    private static final PolygonShape squareShape = new PolygonShape();
    private static final PolygonShape sensorShape = new PolygonShape();
    private static final FixtureDef squareFixture = new FixtureDef();
    private static final FixtureDef sensorFixture = new FixtureDef();
    private static final BodyDef bodyDef = new BodyDef();

    static {
        squareShape.setAsBox(30f, 30f);
        sensorShape.setAsBox(30f, 30f, new Vector2(0f, -60f), 0f);
        squareFixture.shape = squareShape;
        squareFixture.density = 0.5f;
        squareFixture.friction = 0.5f;
        squareFixture.restitution = 0.0f;
        sensorFixture.shape = sensorShape;
        sensorFixture.isSensor = true;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(300, 600);
    }

    public TilePhysicsEntity(Entity tileEntity) {
        super(tileEntity);

        physics = PhysicsManager.getInstance();

        accumulator = new Accumulator(2);

        canMove = true;

        body = physics.world.createBody(bodyDef);
        body.createFixture(squareFixture);
        body.createFixture(sensorFixture);
        body.setUserData(this.parent);
    }

    @Override
    public void update(float delta) {
        Vector2 position = body.getPosition();
        accumulator.accumulate(delta);
        while (accumulator.next()) {
            if (canMove) {
                body.setTransform(position.add(0, -60f), 0.0f);
            }
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {
        if (imA) {
            if (contact.getFixtureA().isSensor()) {
                canMove = false;
            }
        } else {
            if (contact.getFixtureB().isSensor()) {
                canMove = false;
            }
        }
    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {
        if (imA) {
            if (contact.getFixtureA().isSensor()) {
                canMove = true;
            }
        } else {
            if (contact.getFixtureB().isSensor()) {
                canMove = true;
            }
        }
    }
}
