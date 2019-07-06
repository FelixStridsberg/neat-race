package com.vadeen.race.gui;

import com.vadeen.race.game.RaceContext;
import com.vadeen.race.neat.RaceEvaluator;

import javax.swing.*;
import java.awt.*;

/**
 * The RaceSimulator visually simulates the car racing.
 */
public class RaceSimulator {

    private static final int MAX_TICKS_WITH_NO_PROGRESS = 100;

    public TrackPanel trackPanel;

    public RaceSimulator(JFrame window, RaceContext context) {
        trackPanel = new TrackPanel(context.getTrack());

        window.add(trackPanel);
        window.setVisible(true);
    }

    public void simulate(RaceEvaluator evaluator) {
        int noProgress = 0;
        while (noProgress < MAX_TICKS_WITH_NO_PROGRESS) {
            if (evaluator.tick()) {
                noProgress = 0;
            }
            else {
                noProgress++;
            }

            trackPanel.repaint();
            Toolkit.getDefaultToolkit().sync();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}