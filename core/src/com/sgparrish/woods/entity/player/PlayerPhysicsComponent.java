package com.sgparrish.woods.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsComponent;
import com.sgparrish.woods.entity.tile.TilePhysicsComponent;
import com.sgparrish.woods.game.PhysicsManager;

public class PlayerPhysicsComponent extends PhysicsComponent {

    private final PhysicsManager physics;

    private Fixture carried;
    private int numCarried;

    private static final CircleShape shape = new CircleShape();
    private static final FixtureDef fixtureDef = new FixtureDef();
    private static final BodyDef bodyDef = new BodyDef();

    static {
        shape.setRadius(TilePhysicsComponent.HALF_SENSOR_SIZE);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(100, 100);
    }


    public PlayerPhysicsComponent(Entity parent) {
        super(parent);

        physics = PhysicsManager.getInstance();

        body = physics.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(parent);
        setCarried(0);
    }

    public Vector2 getTopMiddle() {
        Vector2 position = body.getPosition();
        return new Vector2(position.x,
                position.y + TilePhysicsComponent.HALF_SENSOR_SIZE
                        + (TilePhysicsComponent.TILE_SIZE * numCarried));
    }

    public void addCarried() {
        setCarried(numCarried + 1);
    }

    private void setCarried(int tiles) {
        numCarried = tiles;
        if (carried != null) {
            body.destroyFixture(carried);
        }

        if (tiles > 0) {
            PolygonShape tileShape = new PolygonShape();
            tileShape.setAsBox(TilePhysicsComponent.HALF_SENSOR_SIZE,
                    TilePhysicsComponent.HALF_TILE_SIZE * tiles,
                    new Vector2(
                            0,
                            TilePhysicsComponent.HALF_TILE_SIZE * (1 + tiles)
                    ),
                    0.0f);

            FixtureDef tileFixture = new FixtureDef();
            tileFixture.shape = tileShape;
            tileFixture.density = 0.5f;
            tileFixture.friction = 0.4f;
            tileFixture.restitution = 0.0f;

            carried = body.createFixture(tileFixture);
            tileShape.dispose();
        }
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
        Gdx.gl.glLineWidth(1);
        ShapeRenderer debugRenderer = new ShapeRenderer();
        debugRenderer.setProjectionMatrix(physics.camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.WHITE);
        debugRenderer.line(body.getPosition(), getTopMiddle());
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}
