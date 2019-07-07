package com.vadeen.race.neat;

import com.vadeen.neat.species.Species;
import com.vadeen.race.game.RaceContext;

import java.util.List;

/**
 * Wrapper to interface the RaceEvaluator with the NEAT network.
 */
public class GenomeEvaluator implements com.vadeen.neat.genome.GenomeEvaluator {

    private final RaceContext raceContext;

    public GenomeEvaluator(RaceContext raceContext) {
        this.raceContext = raceContext;
    }

    @Override
    public void evaluateAll(List<Species> species) {
        RaceEvaluator evaluator = new RaceEvaluator(raceContext, species);
        evaluator.evaluateAll();
    }
}
