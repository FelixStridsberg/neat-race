package com.vadeen.race.game;

import java.awt.*;

/**
 * A car is represented as a dot that has a speed and a rotation.
 * We don't care about the size of the car, we only check if the point (center of the car) is within the bounds of the
 * track.
 */
public class Car {
    private final CarProperties properties;
    private final CarPosition position;
    private final Color color;

    private float speed = 0.0f;
    private int currentCheckpoint = 0;
    private boolean crashed = false;
    private float fitness = 0.0f;
    private int noProgressCount = 0;

    public Car(CarProperties properties, CarPosition position, Color color) {
        this.properties = properties;
        this.position = position;
        this.color = color;
    }

    public void updatePosition() {
        if (crashed)
            return;

        float rotation = position.getRotation();
        float dx = (float)(speed * Math.cos(rotation));
        float dy = (float)(speed * Math.sin(rotation));

        position.addX(dx);
        position.addY(dy);
    }

    public void accelerate(double value) {
        speed += properties.getAcceleration() * value;
        speed = Math.min(speed, properties.getMaxSpeed());
        speed = Math.max(speed, 0);         // Do not allow reverse
    }

    public void turn(double value) {
        if (speed < 0.1)
            return;

        position.addRotation((float)(properties.getTurnSpeed() * value));
    }

    public void increaseNoProgressCount() {
        noProgressCount++;
    }

    public void resetNoProgressCount() {
        noProgressCount = 0;
    }

    public int getNoProgressCount() {
        return noProgressCount;
    }

    public Color getColor() {
        return color;
    }

    public int getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public void setCurrentCheckpoint(int currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    public float getFitness() {
        return fitness;
    }

    public void addFitness() {
        fitness += 1.0f;
    }

    public Point getPosition() {
        return new Point((int)position.getX(), (int)position.getY());
    }

    public float getRotation() {
        return position.getRotation();
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isCrashed() {
        return crashed;
    }
}
