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
    private RaceContext raceContext;
    private JFrame window;

    public RaceSimulator(JFrame window, RaceContext context) {
        trackPanel = new TrackPanel(context);
        this.raceContext = context;
        window.add(trackPanel);
        window.setVisible(true);
        this.window = window;
    }

    public void simulate(RaceEvaluator evaluator) {
        raceContext.reset();

        int noProgress = 0;
        while (noProgress < MAX_TICKS_WITH_NO_PROGRESS) {
            if (evaluator.tick()) {
                noProgress = 0;
            }
            else {
                noProgress++;
            }

            window.repaint();
            Toolkit.getDefaultToolkit().sync();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}