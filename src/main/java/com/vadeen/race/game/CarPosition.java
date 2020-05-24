package com.vadeen.race.game;

public class CarPosition {
    private float x;
    private float y;
    private float rotation;

    public CarPosition(CarPosition copy) {
        this(copy.x, copy.y, copy.rotation);
    }

    public CarPosition(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public void addX(float x) {
        this.x += x;
    }

    public float getY() {
        return y;
    }

    public void addY(float y) {
        this.y += y;
    }

    public float getRotation() {
        return rotation;
    }

    public void addRotation(float rotation) {
        this.rotation += rotation;
    }
}
