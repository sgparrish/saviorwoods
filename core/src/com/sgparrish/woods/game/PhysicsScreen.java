package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.physics.*;

public class PhysicsScreen implements Screen {

    private World world;
    Collidable player;

    public PhysicsScreen() {
        world = new World();
        world.debugRenderer = new DebugRenderer();
        player = new Collidable(new CollisionListener() {
            @Override
            public void collision(Collidable other, Vector2 normal, Contact contact) {
                world.debugRenderer.renderNormal(normal, contact);
            }
        });
        player.position.set(5, 5);
        player.dimension.set(0.95f, 0.95f);
        player.velocity.set(-1, -1);
        player.properties.elasticity = 0.4f;
        player.properties.friction = 0.4f;
        world.addCollidable(player);

        int xmin = 3;
        int ymin = 3;
        int xmax = 10;
        int ymax = 8;
        Collidable body = new Collidable();
        body.position.set(xmin, ymin);
        body.dimension.set(xmax, 1);
        world.addCollidable(body);
        body = new Collidable();
        body.position.set(xmin, ymax);
        body.dimension.set(xmax, 1);
        world.addCollidable(body);
        body = new Collidable();
        body.position.set(xmin, ymin+1);
        body.dimension.set(1, ymax-1);
        world.addCollidable(body);
        body = new Collidable();
        body.position.set(xmax-1, ymin+1);
        body.dimension.set(1, ymax-1);
        world.addCollidable(body);
        /*
        for (int x = 0; x < xmax; x++) {
            Collidable body = new Collidable();
            body.position.set(x, 0);
            body.dimension.set(1, 1);
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(x, ymax);
            body.dimension.set(1, 1);
            world.addCollidable(body);
        }
        for (int y = 1; y < ymax; y++) {
            Collidable body = new Collidable();
            body.position.set(0, y);
            body.dimension.set(1, 1);
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(xmax - 1, y);
            body.dimension.set(1, 1);
            world.addCollidable(body);
        }

        Collidable body = new Collidable();
        body.position.set(5,1);
        body.dimension.set(1, 1);
        world.addCollidable(body);
        body = new Collidable();
        body.position.set(7, 1);
        body.dimension.set(1, 1);
        world.addCollidable(body);
        */
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        float vel = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            player.velocity.x = -vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            player.velocity.x = vel;
        } else {
           // player.velocity.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            player.velocity.y = vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            player.velocity.y = -vel;
        } else {
           // player.velocity.y = 0;
        }

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
