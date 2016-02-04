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

    /**
     * @param clazz the class or superclass to get the component for
     * @return the first component of type clazz (or a subclass of it)
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T get(Class clazz) {
        for (Class thisClass : components.keySet()) {
            Class subClass = thisClass;
            while (thisClass != Component.class) {
                if (clazz == thisClass) {
                    return (T) components.get(subClass);
                }
                else {
                    thisClass = thisClass.getSuperclass();
                }
            }
        }
        return null;
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
