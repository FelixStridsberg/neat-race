package com.vadeen.race.gui;

import com.vadeen.race.game.Car;

import java.awt.*;
import java.util.List;

public class CarPainter {

    public void paint(Graphics2D g, List<Car> cars) {
        for (Car c : cars) {
            Point p = c.getPosition();
            float rotation = c.getRotation();
            Graphics2D g2c = createRotatedContext(g, p, rotation);

            if (c.isCrashed())
                paintCrashedCar(g2c, p, c.getColor());
            else
                paintCar(g2c, p, c.getColor());

            g2c.dispose();
        }
    }

    private void paintCar(Graphics2D g, Point p, Color c) {
        g.setColor(c);
        g.fillRect(p.x, p.y, 26, 9);
        g.setColor(Color.BLACK);
        g.fillRect(p.x + 17, p.y + 1, 3, 7);
        g.fillRect(p.x + 4, p.y + 1, 2, 7);
        g.setColor(Color.RED);
        g.fillRect(p.x, p.y + 1, 1, 2);
        g.fillRect(p.x, p.y + 6, 1, 2);
    }

    private void paintCrashedCar(Graphics2D g, Point p, Color c) {
        int size = 3;
        g.setColor(c);
        g.setStroke(new BasicStroke(2));

        // Center point
        Point cp = new Point(p);
        cp.translate(13, 4);

        g.drawLine(cp.x - size, cp.y - size, cp.x + size, cp.y + size);
        g.drawLine(cp.x + size, cp.y - size, cp.x - size, cp.y + size);
    }

    private Graphics2D createRotatedContext(Graphics2D g, Point p, float rotation) {
        Graphics2D rotated = (Graphics2D) g.create();
        rotated.rotate(rotation, p.x, p.y);
        rotated.translate(-13, -4);
        rotated.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return rotated;
    }
}
