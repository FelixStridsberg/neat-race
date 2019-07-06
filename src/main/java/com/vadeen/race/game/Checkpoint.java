package com.vadeen.race.game;

import java.awt.*;

/**
 * Checkpoints are circles on the track so we can track progress.
 */
public class Checkpoint extends Point {

    public final static int SIZE = 100;

    public boolean contains(Point p) {
        double dx = Math.abs(p.x - this.x);
        double dy = Math.abs(p.y - this.y);
        return Math.hypot(dx, dy) <= SIZE/2;
    }
}
