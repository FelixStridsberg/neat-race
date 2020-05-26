package com.vadeen.race.game;

import java.awt.*;

/**
 * A sensor can detect how far from a car the bounds of a track is.
 */
public class Sensor {

    private final float rotation;

    public Sensor(float rotation) {
        this.rotation = rotation;
    }

    public int getValue(Track track, Car c) {
        float rot = c.getRotation() + rotation;
        Point pos = c.getPosition();

        for (int i = 0; i < 100; i++) {
            float dx = (float)(i*Math.cos(rot));
            float dy = (float)(i*Math.sin(rot));

            int x = (int)(pos.x + dx);
            int y = (int)(pos.y + dy);

            if (!track.getBounds().contains(x, y))
                return i;
        }

        return 1000;
    }

    public float getRotation() {
        return rotation;
    }
}
