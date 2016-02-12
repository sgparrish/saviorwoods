package com.sgparrish.woods.physics;

public class Range {
    public float min;
    public float max;

    public Range() {
        min = Float.POSITIVE_INFINITY;
        max = Float.NEGATIVE_INFINITY;
    }

    public Range(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public void add(float value) {
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public void add(Range other) {
        add(other.min);
        add(other.max);
    }

    public float getLength() {
        return max - min;
    }

    public Range overlap(Range other) {
        if (max < other.min || min > other.max) {
            return null;
        }
        return new Range(Math.max(min, other.min), Math.min(max, other.max));
    }
}
