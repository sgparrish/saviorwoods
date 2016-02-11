package com.sgparrish.woods.entity;

import com.badlogic.gdx.math.Vector2;

public class CollisionShape {

    // Aka how close two objects must be to "touch"
    public static final float SPACE_GAMMA = 0.01f;

    public final Vector2[] points;
    public final CollisionPoint[] collisionPoints;

    private CollisionShape(int numPoints, int numCollisionPoints) {
        this.points = new Vector2[numPoints];
        this.collisionPoints = new CollisionPoint[numCollisionPoints];
    }

    public static CollisionShape createDiamond(float width, float height) {
        CollisionShape diamond = new CollisionShape(4, 8);

        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;

        // This is used over hard coded indices so this can be rearranged
        int index = 0;

        // Here in case I change where object's centers are - this makes it bottom middle
        Vector2 masterOffset = new Vector2(0, 0);

        //    2
        //    /\ 1
        //  3 \/
        //     4

        // Right 1
        diamond.points[index++] = new Vector2(halfWidth, halfHeight).add(masterOffset);
        // Top 2
        diamond.points[index++] = new Vector2(0, height).add(masterOffset);
        // Left 3
        diamond.points[index++] = new Vector2(-halfWidth, halfHeight).add(masterOffset);
        // Bottom 4
        diamond.points[index++] = new Vector2(0, 0).add(masterOffset);

        index = 0;
        Vector2 offset;


        // 6  2   5
        //    /\  4
        // 3  \/
        // 7   1  8

        // Bottom
        offset = new Vector2(0, -SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, -1).nor());

        // Top
        offset = new Vector2(0, height + SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, 1).nor());

        // Left
        offset = new Vector2(-halfWidth - SPACE_GAMMA, halfHeight).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, 0).nor());

        // Right
        offset = new Vector2(halfWidth + SPACE_GAMMA, halfHeight).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, 0).nor());

        // Top Right - diagonals are 'tucked in' by SPACE_GAMMA
        offset = new Vector2(halfWidth - SPACE_GAMMA, height - SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, 1).nor());

        // Top Left
        offset = new Vector2(-halfWidth + SPACE_GAMMA, height - SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, 1).nor());

        // Bottom Left
        offset = new Vector2(-halfWidth + SPACE_GAMMA, SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, -1).nor());

        // Bottom Right
        offset = new Vector2(halfWidth - SPACE_GAMMA, SPACE_GAMMA).add(masterOffset);
        diamond.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, -1).nor());

        return diamond;
    }

    public static CollisionShape createOctagon(float width, float height) {
        CollisionShape octagon = new CollisionShape(8, 12);

        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;
        float quartWidth = width / 4.0f;
        float quartHeight = height / 4.0f;

        // This is used over hard coded indices so this can be rearranged
        int index = 0;

        // Here in case I change where object's centers are - this makes it bottom middle
        Vector2 masterOffset = new Vector2(0, 0);

        //   3 _ 2
        //  4 / \  1
        //   |   |
        //  5 \_/  8
        //   6   7

        // Right Top 1
        octagon.points[index++] = new Vector2(halfWidth, halfHeight + quartHeight).add(masterOffset);
        // Top Right 2
        octagon.points[index++] = new Vector2(quartWidth, height).add(masterOffset);
        // Top Left 3
        octagon.points[index++] = new Vector2(-quartWidth, height).add(masterOffset);
        // Left Top 4
        octagon.points[index++] = new Vector2(-halfWidth, halfHeight + quartHeight).add(masterOffset);
        // Left Bottom 5
        octagon.points[index++] = new Vector2(-halfWidth, quartHeight).add(masterOffset);
        // Bottom Left 6
        octagon.points[index++] = new Vector2(-quartWidth, 0).add(masterOffset);
        // Bottom Right 7
        octagon.points[index++] = new Vector2(quartWidth, 0).add(masterOffset);
        // Right Bottom 8
        octagon.points[index++] = new Vector2(halfWidth, quartHeight).add(masterOffset);

        index = 0;
        Vector2 offset;

        // 10 04_03 09
        //  05 / \ 07
        //    |   |
        //  06 \_/ 08
        // 11 02 01 12

        // Bottom Right 1
        offset = new Vector2(quartWidth, -SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, -1).nor());

        // Bottom Left 2
        offset = new Vector2(-quartWidth, -SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, -1).nor());

        // Top Right 3
        offset = new Vector2(quartWidth, height + SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, 1).nor());

        // Top Left 4
        offset = new Vector2(quartWidth, height + SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(0, 1).nor());

        // Left Top 5
        offset = new Vector2(-halfWidth - SPACE_GAMMA, halfHeight + quartHeight).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, 0).nor());

        // Left Bottom 6
        offset = new Vector2(-halfWidth - SPACE_GAMMA, quartHeight).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, 0).nor());

        // Right Top 7
        offset = new Vector2(halfWidth + SPACE_GAMMA, halfHeight + quartHeight).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, 0).nor());

        // Right Bottom 8
        offset = new Vector2(halfWidth + SPACE_GAMMA, quartHeight).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, 0).nor());

        // Top Right Corner 9
        offset = new Vector2(halfWidth - SPACE_GAMMA, height - SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, 1).nor());

        // Top Left Corner 10
        offset = new Vector2(-halfWidth + SPACE_GAMMA, height - SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, 1).nor());

        // Bottom Left Corner 11
        offset = new Vector2(-halfWidth + SPACE_GAMMA, SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(-1, -1).nor());

        // Bottom Right Corner 12
        offset = new Vector2(halfWidth - SPACE_GAMMA, SPACE_GAMMA).add(masterOffset);
        octagon.collisionPoints[index++] = new CollisionPoint(offset, new Vector2(1, -1).nor());

        return octagon;
    }
}
