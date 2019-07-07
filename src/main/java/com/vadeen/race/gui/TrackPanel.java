package com.vadeen.race.gui;

import com.vadeen.race.game.Car;
import com.vadeen.race.game.Track;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Graphic representation of the track and cars.
 */
public class TrackPanel extends JPanel {

    private final Track track;

    public TrackPanel(Track track) {
        super();
        this.track = track;

        setBackground(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        paintBackground(g);
        paintCars(g);
    }

    private void paintBackground(Graphics g) {
        g.drawImage(track.getBackground(), 0, 0, null);
    }


    private void paintCars(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        List<Car> cars = track.getCars();
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);

            Point p = c.getPosition();
            Graphics2D g2c = (Graphics2D)g2.create();
            g2c.rotate(c.getRotation(), p.x, p.y);

            g2c.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Center car
            p.translate(-13, -4);

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
        g.drawLine(p.x - size, p.y - size, p.x + size, p.y + size);
        g.drawLine(p.x + size, p.y - size, p.x - size, p.y + size);
    }
}
