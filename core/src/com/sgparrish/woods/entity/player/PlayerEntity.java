package com.sgparrish.woods.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsFactory;
import com.sgparrish.woods.entity.PhysicsSpriteEntity;
import com.sgparrish.woods.entity.tile.TileEntity;
import com.sgparrish.woods.entity.tile.TileMapEntity;

import java.util.LinkedList;

public class PlayerEntity extends PhysicsSpriteEntity {

    private TileMapEntity tileMapEntity;

    private boolean facingRight;
    private LinkedList<TileEntity> carriedTiles;
    private Fixture carriedFixture;

    public PlayerEntity() {
        super();

        body = PhysicsFactory.getPlayerBody();
        body.setUserData(this);

        facingRight = true;
        carriedTiles = new LinkedList<TileEntity>();
    }

    private void updateCarriedFixture() {
        if (carriedFixture != null) body.destroyFixture(carriedFixture);
        carriedFixture = PhysicsFactory.attachPlayerCarriedFixture(carriedTiles.size(), body);
    }

    public void addTile(TileEntity tileEntity) {
        carriedTiles.add(tileEntity);
        tileEntity.getBody().setActive(false);
        tileMapEntity.removeTile(tileEntity);
        updateCarriedFixture();
    }

    public void removeTile(TileEntity tileEntity) {
        carriedTiles.remove(tileEntity);
        updateCarriedFixture();
    }

    public void removeTile(int index) {
        carriedTiles.remove(index);
        updateCarriedFixture();
    }

    public void setTileMapEntity(TileMapEntity tileMapEntity) {
        this.tileMapEntity = tileMapEntity;
    }


    public Vector2 getLeft() {
        Vector2 position = body.getPosition();
        return new Vector2(position.x - PhysicsFactory.HALF_SENSOR_SIZE, position.y);
    }

    public Vector2 getRight() {
        Vector2 position = body.getPosition();
        return new Vector2(position.x + PhysicsFactory.HALF_SENSOR_SIZE, position.y);
    }

    public void lift() {
        Vector2 point, ray;
        if (facingRight) {
            point = getRight();
            ray = getRight().add(PhysicsFactory.HALF_SENSOR_SIZE, 0.0f);
        } else {
            point = getLeft();
            ray = getLeft().add(-PhysicsFactory.HALF_SENSOR_SIZE, 0.0f);
        }

        physics.world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getBody().getUserData() instanceof TileEntity) {
                    TileEntity tileEntity = (TileEntity) fixture.getBody().getUserData();
                    addTile(tileEntity);
                }
                return 0;
            }
        }, point, ray);
    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {

    }

    public Vector2 getTopMiddle() {
        Vector2 position = body.getPosition();
        return new Vector2(position.x,
                position.y + PhysicsFactory.HALF_SENSOR_SIZE + (PhysicsFactory.TILE_SIZE * carriedTiles.size()));
    }

    @Override
    public void update(float delta) {
        Vector2 position = body.getPosition();
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            body.applyLinearImpulse(new Vector2(-10 * delta, 0), position, true);
            facingRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            body.applyLinearImpulse(new Vector2(10 * delta, 0), position, true);
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            body.applyLinearImpulse(new Vector2(0, 30 * delta), position, true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            body.applyLinearImpulse(new Vector2(0, -30 * delta), position, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            lift();
        }
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
