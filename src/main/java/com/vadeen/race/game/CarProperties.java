package com.vadeen.race.game;

public class CarProperties {
    private final float acceleration;
    private final float turnSpeed;
    private final float maxSpeed;

    public CarProperties(float acceleration, float turnSpeed, float maxSpeed) {
        this.acceleration = acceleration;
        this.turnSpeed = turnSpeed;
        this.maxSpeed = maxSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }
}
