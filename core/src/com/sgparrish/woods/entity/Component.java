package com.sgparrish.woods.entity;

public abstract class Component {

    protected final Entity parent;

    public Component(Entity parent) {
        this.parent = parent;
    }

    public abstract void update(float delta);

    public abstract void render();
}
