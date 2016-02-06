package com.sgparrish.woods.physics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class World {
    private ArrayList<Collidable> collidables;
    private ArrayList<CollisionDatum> collisionData;
    private PriorityQueue<CollisionPair> collisionPairs;

    public void step(float delta) {

        collisionData.clear();
        collisionPairs.clear();

        generateCollisionData(delta);

        initialCollisionCheck(delta);

        collisionIteration(delta);
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
    }

    private void initialCollisionCheck(float delta) {
        for (int indexA = 0; indexA < collisionData.size(); indexA++) {
            for (int indexB = indexA + 1; indexB < collisionData.size(); indexB++) {
                CollisionDatum datumA = collisionData.get(indexA);
                CollisionDatum datumB = collisionData.get(indexB);

                if (datumA.getAABB().overlaps(datumB.getAABB())) {
                    collisionPairs.offer(new CollisionPair(datumA, datumB, 1.0f, delta));
                }
            }
        }
    }

    private void collisionIteration(float delta) {
        float lastCollisionTime = 0.0f;
        while (!collisionPairs.isEmpty()) {
            CollisionPair collisionPair = collisionPairs.poll();
            if (collisionPair.collisionTime < 0) continue;
            collisionResponse(collisionPair, lastCollisionTime, delta);
            lastCollisionTime = collisionPair.collisionTime;
        }
    }

    private void collisionResponse(CollisionPair collisionPair, float lastCollisionTime, float delta) {
        Contact contact = new Contact(collisionPair, delta);

        // Simulate everything up to this collision time
        applyVelocities(collisionPair.collisionTime - lastCollisionTime, delta);

        // Notify both collidable objects of the collision
        collisionPair.datumA.collidable.collision(collisionPair.datumB.collidable,
                collisionPair.side.getOpposite(), contact);
        collisionPair.datumB.collidable.collision(collisionPair.datumA.collidable,
                collisionPair.side, contact);

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
        for (int index = 0; index < collisionData.size(); index++) {
            CollisionDatum otherDatum = collisionData.get(index);
            if (datum.getAABB().overlaps(otherDatum.getAABB())) {
                collisionPairs.offer(new CollisionPair(datum, otherDatum, timeRemaining, delta));
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
