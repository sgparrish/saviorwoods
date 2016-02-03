package com.sgparrish.woods.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    private Map<Class, Component> components;

    public Entity() {
        components = new HashMap<Class, Component>();
    }

    public void add(Component component) {
        components.put(component.getClass(), component);
    }

    public Component get(Class klass) {
        if (components.containsKey(klass)) {
            return components.get(klass);
        }
        return null;
    }

    public void remove(Component component) {
        components.remove(component);
    }

    public void update(float delta) {
        for (Component component : components.values()) {
            component.update(delta);
        }
    }

    public void render() {
        for (Component component : components.values()) {
            component.render();
        }
    }
}
