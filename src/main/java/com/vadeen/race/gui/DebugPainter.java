package com.vadeen.race.gui;

import com.vadeen.race.game.*;

import java.awt.*;
import java.util.List;

public class DebugPainter {

    public void paint(Graphics2D g, RaceContext context) {
        List<Checkpoint> checkpoints = context.getTrack().getCheckpoints();
        paintBounds(g, context.getTrack().getBounds());
        paintCheckpoints(g, checkpoints);
        paintCarSensors(g, context.getTrack(), context.getTrack().getCars(), context.getSensors());

        Car bestCar = context.getTrack().getBestCar();
        if (bestCar != null) {
            Checkpoint bestCheckpoint = checkpoints.get(bestCar.getCurrentCheckpoint());
            paintBestCheckpoint(g, bestCheckpoint);
        }
    }

    private void paintBounds(Graphics2D g, Polygon bounds) {
        g.setColor(Color.GREEN);
        g.drawPolygon(bounds);
    }

    private void paintCheckpoints(Graphics2D g, List<Checkpoint> checkpoints) {
        g.setColor(Color.RED);
        for (Checkpoint c : checkpoints) {
            int size = (int)c.getSize();
            g.drawOval(c.x - size/2, c.y - size/2, size, size);
        }
    }

    private void paintCarSensors(Graphics2D g, Track track, List<Car> cars, List<Sensor> sensors) {
        g.setColor(Color.YELLOW);
        for (Car car : cars) {
            Point p = car.getPosition();
            float carRotation = car.getRotation();

            for (Sensor s : sensors) {
                float rotation = s.getRotation() + carRotation;
                int len = s.getValue(track, car);

                float dx = (float)(len*Math.cos(rotation));
                float dy = (float)(len*Math.sin(rotation));

                int x = (int)(p.x + dx);
                int y = (int)(p.y + dy);

                g.drawLine(p.x, p.y, x, y);
            }
        }
    }

    private void paintBestCheckpoint(Graphics2D g, Checkpoint c) {
        int size = (int)c.getSize();
        g.setColor(Color.BLUE);
        g.drawOval(c.x - size/2, c.y - size/2, size, size);
    }
}
