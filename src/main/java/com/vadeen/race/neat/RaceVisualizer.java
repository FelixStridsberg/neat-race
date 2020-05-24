package com.vadeen.race.neat;

import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.gui.visualization.Visualizer;
import com.vadeen.race.game.RaceContext;

public class RaceVisualizer implements Visualizer {
    private final RaceContext raceContext;
    private RaceEvaluator evaluator;

    public RaceVisualizer(RaceContext raceContext) {
        this.raceContext = raceContext;
    }

    @Override
    public int getFramesPerSecond() {
        return 60;
    }

    @Override
    public void setup(Generation generation) {
        raceContext.reset();
        evaluator = new RaceEvaluator(raceContext, generation.getSpecies());
    }

    @Override
    public boolean tick() {
        evaluator.tick();
        return true;
    }
}
