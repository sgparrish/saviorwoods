package com.sgparrish.woods.physics;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class World {
    private ArrayList<Collidable> collidables;
    private ArrayList<CollisionDatum> collisionData;
    private PriorityQueue<CollisionPair> collisionPairs;

    public void step(float delta) {

        collisionData.clear();
        collisionPairs.clear();

        generateCollisionData();

        initialCollisionCheck();

        response();
    }

    private void generateCollisionData() {
        for (Collidable collidable : collidables) {
            collisionData.add(new CollisionDatum(collidable));
        }
    }

    private void initialCollisionCheck() {
        for (int indexA = 0; indexA < collidables.size(); indexA++) {
            for (int indexB = indexA + 1; indexB < collidables.size(); indexB++) {
                CollisionDatum datumA = collisionData.get(indexA);
                CollisionDatum datumB = collisionData.get(indexB);

                if (datumA.getAabb().overlaps(datumB.getAabb())) {
                    collisionPairs.offer(new CollisionPair(datumA, datumB));
                }
            }
        }
    }

    private void response() {
        while(!collisionPairs.isEmpty()) {
            CollisionPair collision = collisionPairs.poll();
            if(collision.intersectionT < 0); continue;
        }
    }

}
