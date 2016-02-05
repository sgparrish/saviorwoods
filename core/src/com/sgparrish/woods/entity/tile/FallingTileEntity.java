package com.sgparrish.woods.entity.tile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsFactory;

public class FallingTileEntity extends TileEntity {

    public FallingTileEntity() {
        super();

        body = PhysicsFactory.getTileBody();
        body.setUserData(this);

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

    @Override
    public void update(float delta) {
        Vector2 topMiddle = tileMapEntity.getPlayer().getTopMiddle();
        Vector2 position = body.getPosition();
        if (topMiddle.y >= position.y - PhysicsFactory.THREE_QUARTER_TILE_SIZE &&
                topMiddle.y <= position.y - PhysicsFactory.HALF_TILE_SIZE &&
                topMiddle.x < position.x + PhysicsFactory.QUARTER_TILE_SIZE &&
                topMiddle.x > position.x - PhysicsFactory.QUARTER_TILE_SIZE) {
            tileMapEntity.getPlayer().addTile(this);
        }
    }

    @Override
    public void render() {

    }

    public void fall() {
        Vector2 position = body.getPosition();
        if (canFall) {
            body.setTransform(position.add(0, -PhysicsFactory.TILE_SIZE), 0.0f);
        }
    }
}
