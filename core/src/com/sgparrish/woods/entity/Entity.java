package com.sgparrish.woods.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    private List<Component> components;

    public Entity() {
        components = new ArrayList<Component>();
    }

    public void add(Component component) {
        components.add(component);
    }

    public void remove(Component component) {
        components.remove(component);
    }

    public void update(float delta) {
        for (Component component : components) {
            component.update(delta);
        }
    }

    public void render() {
        for (Component component : components) {
            component.render();
        }
    }
}
