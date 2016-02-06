package com.sgparrish.woods.game;

import com.badlogic.gdx.Screen;
import com.sgparrish.woods.physics.Collidable;
import com.sgparrish.woods.physics.DebugRenderer;
import com.sgparrish.woods.physics.World;

public class PhysicsScreen implements Screen {

    private World world;

    public PhysicsScreen () {
        world = new World();
        world.debugRenderer = new DebugRenderer();
        Collidable a = new Collidable();
        a.position.set(1, 1);
        a.dimension.set(1, 1);
        a.velocity.set(4, 2);
        a.properties.elasticity = 1.0f;
        Collidable b = new Collidable();
        b.position.set(10, 5);
        b.dimension.set(1, 1);
        b.velocity.set(0, 0);
        world.addCollidable(a);
        world.addCollidable(b);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.step(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
