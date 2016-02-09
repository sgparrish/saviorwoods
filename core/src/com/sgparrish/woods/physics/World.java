package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Vector2;
import com.sgparrish.woods.util.Accumulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class World {

    private static final float TIME_STEP = 1f / 60f;

    public DebugRenderer debugRenderer;
    private Accumulator accumulator;
    private ArrayList<Collidable> collidables;
    private ArrayList<CollisionDatum> collisionData;
    private PriorityQueue<CollisionPair> collisionPairs;

    public World() {
        accumulator = new Accumulator(TIME_STEP);
        collidables = new ArrayList<Collidable>();
        collisionData = new ArrayList<CollisionDatum>();
        collisionPairs = new PriorityQueue<CollisionPair>();
    }

    public void step(float delta) {

        accumulator.accumulate(delta);

        //while (accumulator.next()) {

            collisionData.clear();
            collisionPairs.clear();

            generateCollisionData(delta);
            initialCollisionCheck(delta);
            collisionIteration(delta);
       // }
    }

    public void addCollidable(Collidable collidable) {
        collidables.add(collidable);
    }

    public void removeCollidable(Collidable collidable) {
        collidables.remove(collidable);
    }

    private void generateCollisionData(float delta) {
        for (Collidable collidable : collidables) {
            if (collidable.active) collisionData.add(new CollisionDatum(collidable, delta));
        }
        if (debugRenderer != null) debugRenderer.render(collisionData, collisionPairs);
    }

    private void initialCollisionCheck(float delta) {
        for (int indexA = 0; indexA < collisionData.size(); indexA++) {
            for (int indexB = indexA + 1; indexB < collisionData.size(); indexB++) {
                CollisionDatum datumA = collisionData.get(indexA);
                CollisionDatum datumB = collisionData.get(indexB);
                if (datumA.aabb.overlaps(datumB.aabb)) {
                    collisionPairs.offer(new CollisionPair(datumA, datumB, 1.0f, delta));
                }
            }
        }

    }

    private void collisionIteration(float delta) {
        float lastCollisionTime = 0.0f;
        while (!collisionPairs.isEmpty()) {
            CollisionPair collisionPair = collisionPairs.poll();
            if (collisionPair.collisionTime != Float.MAX_VALUE) {
                collisionResponse(collisionPair, lastCollisionTime, delta);
                lastCollisionTime = collisionPair.collisionTime;
                if (debugRenderer != null) debugRenderer.render(collisionData, collisionPairs);
            }
        }
        applyVelocities(1.0f - lastCollisionTime, delta);

    }

    private void collisionResponse(CollisionPair collisionPair, float lastCollisionTime, float delta) {

        Contact contact = new Contact(collisionPair, delta);

        // Simulate everything up to this collision time
        applyVelocities(collisionPair.collisionTime - lastCollisionTime, delta);

        // Create Vectors for new velocities
        Vector2 forceA, forceB;

        // Notify both collidable objects of the collision
        forceA = collisionPair.datumA.collidable.collision(collisionPair.datumB.collidable,
                new Vector2(collisionPair.normal).scl(-1), contact);
        forceB = collisionPair.datumB.collidable.collision(collisionPair.datumA.collidable,
                new Vector2(collisionPair.normal), contact);

        // Call any collision listeners
        if (collisionPair.datumA.collidable.listener != null) {
            collisionPair.datumA.collidable.listener.collision(collisionPair.datumB.collidable,
                    new Vector2(forceA).scl(-1),
                    new Vector2(collisionPair.normal).scl(-1), contact);
        }
        if (collisionPair.datumB.collidable.listener != null) {
            collisionPair.datumB.collidable.listener.collision(collisionPair.datumA.collidable,
                    new Vector2(forceB).scl(-1),
                    new Vector2(collisionPair.normal), contact);
        }

        // Apply forces
        if (forceA != null) {
            collisionPair.datumA.collidable.applyForce(forceA);
        }
        if (forceB != null) {
            collisionPair.datumB.collidable.applyForce(forceB);
        }

        // Render this contact...
        if (debugRenderer != null) debugRenderer.renderContact(contact);

        // Process new possible collisions from state changes (bounces, etc)
        float timeRemaining = (1.0f - collisionPair.collisionTime);
        processNewCollisions(collisionPair, timeRemaining, delta);
    }

    public void applyVelocities(float timeToSimulate, float delta) {
        // Simulate everything up to collisionTime
        for (CollisionDatum datum : collisionData) {
            datum.collidable.applyVelocity(timeToSimulate, delta);
        }
    }

    private void processNewCollisions(CollisionPair collisionPair, float timeRemaining, float delta) {
        // Test if AABB must be regenerated
        collisionPair.datumA.refreshAABB(timeRemaining, delta);
        collisionPair.datumB.refreshAABB(timeRemaining, delta);
        // If AABBs must be regenerated

        if (collisionPair.datumA.regenPairs) {
            // Regenerate collision pairs for all pairs involving this datum
            regenerateCollisionPairs(collisionPair.datumA, timeRemaining, delta);
        } else {
            // Rerun collision checks for all pairs involving this datum
            rerunCollisionChecks(collisionPair.datumA, timeRemaining, delta);
        }
        if (collisionPair.datumB.regenPairs) {
            regenerateCollisionPairs(collisionPair.datumB, timeRemaining, delta);
        } else {
            rerunCollisionChecks(collisionPair.datumB, timeRemaining, delta);
        }
    }

    private void regenerateCollisionPairs(CollisionDatum datum, float timeRemaining, float delta) {
        // First remove all pairs in priority queue involving this datum
        Iterator<CollisionPair> iterator = collisionPairs.iterator();
        while (iterator.hasNext()) {
            CollisionPair collisionPair = iterator.next();
            if (collisionPair.datumA == datum || collisionPair.datumB == datum) {
                iterator.remove();
            }
        }

        // Now iterate all other collidable objects, and generate necessary pairs
        // This functionally also reruns collision checks, as collision pairs run
        // collision checks on construction
        for (CollisionDatum otherDatum : collisionData) {
            if (datum != otherDatum) {
                if (datum.aabb.overlaps(otherDatum.aabb)) {
                    collisionPairs.offer(new CollisionPair(datum, otherDatum, timeRemaining, delta));
                }
            }
        }
    }

    private void rerunCollisionChecks(CollisionDatum datum, float timeRemaining, float delta) {

        ArrayList<CollisionPair> temporaryPairs = new ArrayList<CollisionPair>();

        // Rerun collision on all pairs in priority queue involving this datum
        Iterator<CollisionPair> iterator = collisionPairs.iterator();
        while (iterator.hasNext()) {
            CollisionPair collisionPair = iterator.next();
            if (collisionPair.datumA == datum || collisionPair.datumB == datum) {
                collisionPair.runCollisionCheck(timeRemaining, delta);
                // Remove this, because changing the order won't occur unless an add event occurs
                // It can't be added until this iteration is complete though
                iterator.remove();
                temporaryPairs.add(collisionPair);
            }
        }

        // Re-add all collision pairs
        for (CollisionPair collisionPair : temporaryPairs) {
            collisionPairs.offer(collisionPair);
        }
    }

}
