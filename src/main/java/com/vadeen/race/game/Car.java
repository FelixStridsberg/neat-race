package com.vadeen.race.game;

import java.awt.*;

/**
 * A car is represented as at dot that has a speed and a rotation.
 * We don't care about the size of the car, we only check if the point (center of the car) is within the bounds of the
 * track.
 *
 * @todo Remove gui stuff from here.
 */
public class Car {
    private final CarProperties properties;
    private final Color color;

    private boolean crashed = false;

    private float fitness = 0.0f;

    private float x = 285.0f;
    private float y = 115.0f;
    private float rotation = 0.0f;
    private float speed = 0.0f;

    private int checkpoint = 0;

    private int lastProgress = 0;

    public Car(Color color, CarProperties properties) {
        this.color = color;
        this.properties = properties;
    }

    public Color getColor() {
        return color;
    }

    public int getCurrentCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public float getSpeed() {
        return speed;
    }

    public float getFitness() {
        return fitness;
    }

    public void addFitness() {
        fitness += 1.0f;
    }

    public Point getPosition() {
        return new Point((int)x, (int)y);
    }

    public float getRotation() {
        return rotation;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void accelerate(double value) {
        speed += properties.getAcceleration() * value;
        speed = Math.min(speed, properties.getMaxSpeed());
        speed = Math.max(speed, 0);         // Do not allow reverse
    }

    public void turn(double value) {
        if (speed < 0.1)
            return;

        rotation += properties.getTurnSpeed() * value;
    }

    public void update() {
        if (crashed)
            return;

        float dx = (float)(speed * Math.cos(rotation));
        float dy = (float)(speed * Math.sin(rotation));

        x += dx;
        y += dy;

        lastProgress++;
    }

    public void progress() {
        lastProgress = 0;
    }

    public int getLastProgress() {
        return lastProgress;
    }
}
