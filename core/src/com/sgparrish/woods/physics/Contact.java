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

    public Contact (CollisionPair collisionPair, float delta) {
        Rectangle rectangleA = collisionPair.datumA.collidable.getFutureRectangle(collisionPair.collisionTime, delta);
        Rectangle rectangleB = collisionPair.datumB.collidable.getFutureRectangle(collisionPair.collisionTime, delta);

        pointA = new Vector2();
        pointB = new Vector2();
        time = collisionPair.collisionTime;

        // Get the constant coordinates first
        switch(collisionPair.side) {
            case LEFT:
                pointA.x = rectangleB.x;
                pointB.x = rectangleB.x;
                break;
            case RIGHT:
                pointA.x = rectangleA.x;
                pointB.x = rectangleA.x;
                break;
            case TOP:
                pointA.y = rectangleA.y;
                pointB.y = rectangleA.y;
                break;
            case BOTTOM:
                pointA.y = rectangleB.y;
                pointB.y = rectangleB.y;
                break;
        }


        List<Float> list = new ArrayList<Float>();

        // Separate into problems by axis
        switch(collisionPair.side) {
            case LEFT:
            case RIGHT:
                // Okay, we have 4 y values, and we need the middle 2
                // So we put them all in a list, and sort it (because that's the simplest way i guess)
                list.add(rectangleA.y);
                list.add(rectangleB.y);
                list.add(rectangleA.y + rectangleA.height);
                list.add(rectangleB.y + rectangleB.height);
                Collections.sort(list);
                pointA.y = list.get(1);
                pointB.y = list.get(2);
                break;
            case TOP:
            case BOTTOM:
                // Okay, we have 4 x values, and we need the middle 2
                // So we put them all in a list, and sort it (because that's the simplest way i guess)
                list.add(rectangleA.x);
                list.add(rectangleB.x);
                list.add(rectangleA.x + rectangleA.width);
                list.add(rectangleB.x + rectangleB.width);
                Collections.sort(list);
                pointA.x = list.get(1);
                pointB.x = list.get(2);
                break;
        }
    }
}
