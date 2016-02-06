package com.sgparrish.woods.physics;

public class MaterialProperties {

    public float friction;
    public float elasticity;
    public float mass;
    public float selfWeight; // How much do we care about other's properties?

    public MaterialProperties() {
        this(0.0f, 0.0f, 1.0f);
    }

    public MaterialProperties(float friction, float elasticity, float mass) {
        this(friction, elasticity, mass, 0.5f);
    }

    public MaterialProperties(float friction, float elasticity, float mass, float selfWeight) {
        this.friction = friction;
        this.elasticity = elasticity;
        this.mass = mass;
        this.selfWeight = selfWeight;
    }

    public float getFriction(MaterialProperties other) {
        float otherWeight = (1 - selfWeight);
        return friction * selfWeight + other.friction * otherWeight;
    }

    public float getElasticity(MaterialProperties other) {
        float otherWeight = (1 - selfWeight);
        return elasticity * selfWeight + other.elasticity * otherWeight;
    }
}
