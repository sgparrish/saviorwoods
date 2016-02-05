package com.sgparrish.woods.entity.tile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.entity.PhysicsFactory;
import com.sgparrish.woods.entity.PhysicsSpriteEntity;
import com.sgparrish.woods.entity.player.PlayerEntity;
import com.sgparrish.woods.util.Accumulator;

public class TileMapEntity extends PhysicsSpriteEntity {

    private BiMap<MapKey, TileEntity> keyToTileMap;
    private BiMap<TileEntity, MapKey> tileToKeyMap;
    private final Vector2 position;
    private final Vector2 dimension;

    private final Accumulator accumulator;
    private PlayerEntity player;

    public TileMapEntity() {
        super();

        keyToTileMap = HashBiMap.create();
        tileToKeyMap = keyToTileMap.inverse();
        position = new Vector2();
        dimension = new Vector2();
        accumulator = new Accumulator(1f);
    }

    @Override
    public void beginContact(Contact contact, Entity other, boolean imA) {

    }

    @Override
    public void endContact(Contact contact, Entity other, boolean imA) {

    }

    public Vector2 getWorldCoords(int x, int y) {
        return new Vector2(((float) x) * PhysicsFactory.TILE_SIZE + PhysicsFactory.HALF_TILE_SIZE,
                ((float) y) * PhysicsFactory.TILE_SIZE + PhysicsFactory.HALF_TILE_SIZE);
    }

    public void addTile(int x, int y) {
        TileEntity tileEntity = new TileEntity();
        tileEntity.setTileMapEntity(this);
        tileEntity.getBody().setTransform(getWorldCoords(x, y), 0.0f);
        keyToTileMap.put(new MapKey(x, y), tileEntity);
    }

    public void removeTile(Entity tileEntity) {
        tileToKeyMap.remove(tileEntity);
    }

    public void setBounds(int x, int y, int w, int h) {
        this.position.x = x;
        this.position.y = y;
        this.dimension.x = w;
        this.dimension.y = h;
    }

    private void fall() {
        for (int y = 1; y <= 13; y++) {
            for (int x = 0; x <= 21; x++) {
                TileEntity tileEntity = keyToTileMap.get(new MapKey(x, y));
                if (tileEntity != null) {
                    if (tileEntity.canFall() &&
                            keyToTileMap.get(new MapKey(x, y - 1)) == null) {
                        keyToTileMap.remove(new MapKey(x, y));
                        keyToTileMap.put(new MapKey(x, y - 1), tileEntity);
                        tileEntity.fall();
                    }
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        accumulator.accumulate(delta);
        while (accumulator.next()) {
            fall();
        }
        for (int y = 1; y <= 13; y++) {
            for (int x = 0; x <= 21; x++) {
                TileEntity tileEntity = keyToTileMap.get(new MapKey(x, y));
                if (tileEntity != null) {
                    tileEntity.update(delta);
                }
            }
        }
    }

    @Override
    public void render() {
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
