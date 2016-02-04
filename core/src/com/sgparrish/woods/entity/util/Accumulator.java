package com.sgparrish.woods.entity.util;

public class Accumulator {

    private float count;
    private float period;

    public Accumulator(float period) {
        this.count = 0;
        this.period = period;
    }

    public void accumulate(float value) {
        count += value;
    }

    public boolean hasNext() {
        return count >= period;
    }

    public boolean next() {
        if (count >= period) {
            count -= period;
            return true;
        }
        return false;
    }

}
