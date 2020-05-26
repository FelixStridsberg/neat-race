package com.vadeen.race.gui;

import com.vadeen.neat.gui.visualization.VisualPanel;
import com.vadeen.race.game.Car;
import com.vadeen.race.game.RaceContext;

import java.awt.*;
import java.util.List;

/**
 * Graphic representation of the context and cars.
 */
public class TrackPanel extends VisualPanel {

    private final RaceContext context;
    private final CarPainter carPainter = new CarPainter();
    private final DebugPainter debugPainter = new DebugPainter();

    private boolean paintDebug = false;

    public TrackPanel(RaceContext context) {
        super();
        this.context = context;
    }

    @Override
    public float getAspectRatio() {
        return 4/3f;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        scaleContext(g2);
        paintBackground(g2);

        if (paintDebug)
            debugPainter.paint(g2, context);

        List<Car> cars = context.getTrack().getCars();
        carPainter.paint(g2, cars);
    }

    private void paintBackground(Graphics g) {
        g.drawImage(context.getTrack().getBackground(), 0, 0, null);
    }

    private void scaleContext(Graphics2D g2) {
        float scale = getWidth()/800.0f;
        g2.scale(scale, scale);
    }

    public void setDebugTrack(boolean debug) {
        paintDebug = debug;
    }
}
