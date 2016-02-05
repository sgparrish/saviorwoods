package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.game.PhysicsManager;

public class PhysicsFactory {

    public static final float TILE_SIZE = 1f;
    public static final float THREE_QUARTER_TILE_SIZE = TILE_SIZE * 0.75f;
    public static final float HALF_TILE_SIZE = TILE_SIZE / 2.0f;
    public static final float QUARTER_TILE_SIZE = TILE_SIZE / 4.0f;
    public static final float SENSOR_SIZE = 0.95f;
    public static final float HALF_SENSOR_SIZE = SENSOR_SIZE / 2.0f;

    // Player static objects
    private static final CircleShape PLAYER_SHAPE = new CircleShape();
    private static final FixtureDef PLAYER_FIXTURE_DEF = new FixtureDef();
    private static final BodyDef PLAYER_BODY_DEF = new BodyDef();

    static {
        PLAYER_SHAPE.setRadius(HALF_SENSOR_SIZE);
        PLAYER_FIXTURE_DEF.shape = PLAYER_SHAPE;
        PLAYER_FIXTURE_DEF.density = 0.5f;
        PLAYER_FIXTURE_DEF.friction = 0.1f;
        PLAYER_FIXTURE_DEF.restitution = 0.0f;

        PLAYER_BODY_DEF.type = BodyDef.BodyType.DynamicBody;
        PLAYER_BODY_DEF.fixedRotation = true;
        PLAYER_BODY_DEF.allowSleep = false;
    }

    public static Body getPlayerBody() {
        PhysicsManager physics = PhysicsManager.getInstance();

        Body playerBody = physics.world.createBody(PLAYER_BODY_DEF);
        playerBody.createFixture(PLAYER_FIXTURE_DEF);
        return playerBody;
    }

    public static Fixture attachPlayerCarriedFixture(int count, Body playerBody) {
        if (count <= 0) return null;


        PolygonShape carriedTileShape = new PolygonShape();
        carriedTileShape.setAsBox(HALF_SENSOR_SIZE,
                HALF_TILE_SIZE * count,
                new Vector2(
                        0,
                        HALF_TILE_SIZE * (1 + count)
                ),
                0.0f);

        FixtureDef carriedTileFixtureDef = new FixtureDef();
        carriedTileFixtureDef.shape = carriedTileShape;
        carriedTileFixtureDef.density = 0.0f;
        carriedTileFixtureDef.friction = 0.1f;
        carriedTileFixtureDef.restitution = 0.0f;

        Fixture carriedTileFixture = playerBody.createFixture(carriedTileFixtureDef);
        carriedTileShape.dispose();
        return carriedTileFixture;
    }

    // Tile static objects
    private static final PolygonShape TILE_SHAPE = new PolygonShape();
    private static final PolygonShape TILE_SENSOR_SHAPE = new PolygonShape();
    private static final FixtureDef TILE_FIXTURE_DEF = new FixtureDef();
    private static final FixtureDef TILE_SENSOR_FIXTURE_DEF = new FixtureDef();
    private static final BodyDef TILE_BODY_DEF = new BodyDef();

    static {
        TILE_SHAPE.setAsBox(HALF_TILE_SIZE, HALF_TILE_SIZE);
        TILE_SENSOR_SHAPE.setAsBox(HALF_SENSOR_SIZE, HALF_TILE_SIZE, new Vector2(0f, -TILE_SIZE), 0f);
        TILE_FIXTURE_DEF.shape = TILE_SHAPE;
        TILE_FIXTURE_DEF.density = 0.1f;
        TILE_FIXTURE_DEF.friction = 0.5f;
        TILE_FIXTURE_DEF.restitution = 0.0f;
        TILE_SENSOR_FIXTURE_DEF.shape = TILE_SENSOR_SHAPE;
        TILE_SENSOR_FIXTURE_DEF.isSensor = true;
        TILE_BODY_DEF.type = BodyDef.BodyType.KinematicBody;
        TILE_BODY_DEF.fixedRotation = true;
    }

    public static Body getTileBody() {
        PhysicsManager physics = PhysicsManager.getInstance();

        Body tileBody = physics.world.createBody(TILE_BODY_DEF);
        tileBody.createFixture(TILE_FIXTURE_DEF);
        tileBody.createFixture(TILE_SENSOR_FIXTURE_DEF);
        return tileBody;
    }

    private static final PolygonShape BRUSH_SHAPE_DEF = new PolygonShape();
    private static final BodyDef BRUSH_BODY_DEF = new BodyDef();

    static {
        BRUSH_SHAPE_DEF.setAsBox(10, 1, new Vector2(10, -1), 0.0f);
        BRUSH_BODY_DEF.type = BodyDef.BodyType.StaticBody;
    }

    public static Body getBrushBody() {
        PhysicsManager physics = PhysicsManager.getInstance();

        Body brushBody = physics.world.createBody(BRUSH_BODY_DEF);
        brushBody.createFixture(BRUSH_SHAPE_DEF, 0.0f);
        return brushBody;
    }

}
