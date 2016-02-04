package com.sgparrish.woods.entity.tile;

public class MapKey {
    public int x;
    public int y;

    public MapKey(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapKey mapKey = (MapKey) o;

        return x == mapKey.x && y == mapKey.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
