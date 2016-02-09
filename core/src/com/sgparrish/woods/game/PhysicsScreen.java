package com.sgparrish.woods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.physics.*;

public class PhysicsScreen implements Screen {

    private World world;
    private Collidable player;

    public PhysicsScreen() {
        world = new World();
        world.debugRenderer = new DebugRenderer();
        player = new Collidable(new CollisionListener() {
            @Override
            public void collision(Collidable other, Vector2 force, Vector2 normal, Contact contact) {
                world.debugRenderer.renderNormal(normal, contact);
            }
        });
        //player.position.set(0.9122961f, 1.0287564f);
        player.position.set(2, 2);
        player.dimension.set(0.95f, 0.95f);
        player.velocity.set(0, 0);
        player.properties.mass = 1.0f;
        player.properties.elasticity = 0.0f;
        player.properties.friction = 0.0f;
        world.addCollidable(player);

        int xmin = 0;
        int ymin = 0;
        int xmax = 20;
        int ymax = 10;

        if (false) {
            Collidable body = new Collidable();
            body.position.set(xmin, ymin);
            body.dimension.set(xmax - xmin, 1);
            body.properties.mass = 0;
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(xmin, ymax);
            body.dimension.set(xmax - xmin, 1);
            body.properties.mass = 0;
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(xmin, ymin + 1);
            body.dimension.set(1, ymax - ymin - 1);
            body.properties.mass = 0;
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(xmax - 1, ymin + 1);
            body.dimension.set(1, ymax - ymin - 1);
            body.properties.mass = 0;
            world.addCollidable(body);
        } else {
            for (int x = xmin; x < xmax; x++) {
                Collidable body = new Collidable();
                body.position.set(x, ymin);
                body.dimension.set(1, 1);
                body.properties.mass = 0;
                world.addCollidable(body);
                body = new Collidable();
                body.position.set(x, ymax);
                body.dimension.set(1, 1);
                body.properties.mass = 0;
                world.addCollidable(body);
            }
            for (int y = ymin + 1; y < ymax; y++) {
                Collidable body = new Collidable();
                body.position.set(xmin, y);
                body.dimension.set(1, 1);
                body.properties.mass = 0;
                world.addCollidable(body);
                body = new Collidable();
                body.position.set(xmax - 1, y);
                body.dimension.set(1, 1);
                body.properties.mass = 0;
                world.addCollidable(body);
            }

            Collidable body = new Collidable();
            body.position.set(5, 1);
            body.dimension.set(1, 1);
            world.addCollidable(body);
            body = new Collidable();
            body.position.set(7, 1);
            body.dimension.set(1, 1);
            world.addCollidable(body);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        float vel = 0.05f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            player.velocity.x -= vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            player.velocity.x += vel;
        } else {
            // player.velocity.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            player.velocity.y += vel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            player.velocity.y -= vel;
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
