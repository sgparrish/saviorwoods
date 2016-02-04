package com.sgparrish.woods.entity.tile;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.entity.Entity;
import com.sgparrish.woods.util.Accumulator;

import java.util.HashMap;
import java.util.Map;

public class TileMapEntity extends Entity {

    private final Map<MapKey, TileEntity> tilemap;
    private final Vector2 position;
    private final Vector2 dimension;

    private final Accumulator accumulator;
    public Entity player;

    public TileMapEntity() {
        super();

        tilemap = new HashMap<MapKey, TileEntity>();
        position = new Vector2();
        dimension = new Vector2();
        accumulator = new Accumulator(1f);
    }

    public Vector2 getWorldCoords(int x, int y) {
        return new Vector2(((float) x) * TilePhysicsComponent.TILE_SIZE + TilePhysicsComponent.HALF_TILE_SIZE,
                ((float) y) * TilePhysicsComponent.TILE_SIZE + TilePhysicsComponent.HALF_TILE_SIZE);
    }

    public void addTile(int x, int y) {
        TileEntity tileEntity = new TileEntity();
        TilePhysicsComponent component = tileEntity.get(TilePhysicsComponent.class);
        component.tileMapEntity = this;
        component.body.setTransform(getWorldCoords(x, y), 0.0f);
        tilemap.put(new MapKey(x, y), tileEntity);
    }

    public void removeTile(Entity tileEntity) {
        for (int y = 1; y <= 13; y++) {
            for (int x = 0; x <= 21; x++) {
                MapKey key = new MapKey(x, y);
                if (tileEntity == tilemap.get(key)) {
                    tilemap.remove(key);
                }
            }
        }
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
                TileEntity tileEntity = tilemap.get(new MapKey(x, y));
                if (tileEntity != null) {
                    if (tileEntity.<TilePhysicsComponent>get(TilePhysicsComponent.class).canFall &&
                            tilemap.get(new MapKey(x, y - 1)) == null) {
                        tilemap.remove(new MapKey(x, y));
                        tilemap.put(new MapKey(x, y - 1), tileEntity);
                        tileEntity.<TilePhysicsComponent>get(TilePhysicsComponent.class).fall();
                    }
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        accumulator.accumulate(delta);
        while (accumulator.next()) {
            fall();
        }
        for (TileEntity tileEntity : tilemap.values()) {
            tileEntity.update(delta);
        }
    }

    @Override
    public void render() {
        super.render();
    }
}
