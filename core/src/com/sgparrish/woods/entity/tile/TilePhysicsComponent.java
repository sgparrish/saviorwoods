package com.sgparrish.woods.entity.tile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.entity.player.PlayerEntity;
import com.sgparrish.woods.entity.player.PlayerPhysicsComponent;
import com.sgparrish.woods.game.PhysicsManager;

public class TilePhysicsComponent extends PhysicsComponent {

    public static final float TILE_SIZE = 60f;
    public static final float THREE_QUARTER_TILE_SIZE = TILE_SIZE * 0.75f;
    public static final float HALF_TILE_SIZE = TILE_SIZE / 2.0f;
    public static final float QUARTER_TILE_SIZE = TILE_SIZE / 4.0f;
    public static final float SENSOR_SIZE = 59.6f;
    public static final float HALF_SENSOR_SIZE = SENSOR_SIZE / 2.0f;

    private final PhysicsManager physics;

    public boolean canFall;

    private static final PolygonShape squareShape = new PolygonShape();
    private static final PolygonShape sensorShape = new PolygonShape();
    private static final FixtureDef squareFixture = new FixtureDef();
    private static final FixtureDef sensorFixture = new FixtureDef();
    private static final BodyDef bodyDef = new BodyDef();

    static {
        squareShape.setAsBox(HALF_TILE_SIZE, HALF_TILE_SIZE);
        sensorShape.setAsBox(HALF_SENSOR_SIZE, HALF_TILE_SIZE, new Vector2(0f, -SENSOR_SIZE), 0f);
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

    public TileMapEntity tileMapEntity;

    public TilePhysicsComponent(Entity tileEntity) {
        super(tileEntity);

        physics = PhysicsManager.getInstance();

        canFall = true;

        body = physics.world.createBody(bodyDef);
        body.createFixture(squareFixture);
        body.createFixture(sensorFixture);
        body.setUserData(parent);
    }

    public void fall() {
        Vector2 position = body.getPosition();
        if (canFall) {
            body.setTransform(position.add(0, -TILE_SIZE), 0.0f);
        }
    }

    @Override
    public void update(float delta) {
        final PlayerPhysicsComponent[] playerPhysicsComponent = {tileMapEntity.player.get(PlayerPhysicsComponent.class)};
        Vector2 contactPoint = playerPhysicsComponent[0].getTopMiddle();
        Vector2 position = body.getPosition();
        if (contactPoint.y >= position.y - THREE_QUARTER_TILE_SIZE &&
                contactPoint.y <= position.y - HALF_TILE_SIZE &&
                contactPoint.x < position.x + QUARTER_TILE_SIZE &&
                contactPoint.x > position.x - QUARTER_TILE_SIZE) {
            physics.runOutsidePhysicsStep(new Runnable() {
                @Override
                public void run() {
                    physics.world.destroyBody(body);
                    playerPhysicsComponent[0] = tileMapEntity.player.get(PlayerPhysicsComponent.class);
                    tileMapEntity.removeTile(parent);
                    playerPhysicsComponent[0].addCarried();
                }
            });
        }

    }

    @Override
    public void render() {
    }

    @Override
    public void beginContact(Contact contact, final Entity other, boolean imA) {
        Fixture myFixture = (imA ? contact.getFixtureA() : contact.getFixtureB());

        if (myFixture.isSensor()) {
            canFall = false;
        }
    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {
        Fixture myFixture = (imA ? contact.getFixtureA() : contact.getFixtureB());

        if (myFixture.isSensor()) {
            canFall = true;
        }
    }
}
