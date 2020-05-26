package com.vadeen.race.game;

import java.awt.*;
import java.util.Objects;

/**
 * Checkpoints are circles on the track so we can track progress.
 */
public class Checkpoint extends Point {

    private final float size;

    public Checkpoint(int x, int y, float size) {
        super(x, y);
        this.size = size;
    }

    public boolean contains(Point p) {
        double dx = Math.abs(p.x - this.x);
        double dy = Math.abs(p.y - this.y);
        return Math.hypot(dx, dy) <= size/2;
    }

    public float getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Checkpoint that = (Checkpoint) o;
        return java.lang.Float.compare(that.size, size) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }
}
