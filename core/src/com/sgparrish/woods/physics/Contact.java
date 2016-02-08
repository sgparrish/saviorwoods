package com.sgparrish.woods.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contact {
    public final Vector2 pointA;
    public final Vector2 pointB;
    public final float time;

    public Contact(CollisionPair collisionPair, float delta) {
        Rectangle rectangleA = collisionPair.datumA.collidable.getFutureRectangle(collisionPair.collisionTime, delta);
        Rectangle rectangleB = collisionPair.datumB.collidable.getFutureRectangle(collisionPair.collisionTime, delta);

        pointA = new Vector2();
        pointB = new Vector2();
        time = collisionPair.collisionTime;


        List<Float> list = new ArrayList<Float>();

        // Get the constant coordinates first
        if (collisionPair.normal.y == 0) {
            if (collisionPair.normal.x == 1) {
                pointA.x = rectangleB.x;
                pointB.x = rectangleB.x;
            } else if (collisionPair.normal.x == -1) {
                pointA.x = rectangleA.x;
                pointB.x = rectangleA.x;
            }
            // Okay, we have 4 y values, and we need the middle 2
            // So we put them all in a list, and sort it (because that's the simplest way i guess)
            list.add(rectangleA.y);
            list.add(rectangleB.y);
            list.add(rectangleA.y + rectangleA.height);
            list.add(rectangleB.y + rectangleB.height);
            Collections.sort(list);
            pointA.y = list.get(1);
            pointB.y = list.get(2);
        } else if (collisionPair.normal.x == 0) {
            if (collisionPair.normal.y == 1) {
                pointA.y = rectangleB.y;
                pointB.y = rectangleB.y;
            } else if (collisionPair.normal.y == -1) {
                pointA.y = rectangleA.y;
                pointB.y = rectangleA.y;
            }
            // Okay, we have 4 x values, and we need the middle 2
            // So we put them all in a list, and sort it (because that's the simplest way i guess)
            list.add(rectangleA.x);
            list.add(rectangleB.x);
            list.add(rectangleA.x + rectangleA.width);
            list.add(rectangleB.x + rectangleB.width);
            Collections.sort(list);
            pointA.x = list.get(1);
            pointB.x = list.get(2);
        }
    }
}
