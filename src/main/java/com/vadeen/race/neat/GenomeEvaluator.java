package com.vadeen.race.neat;

import com.vadeen.neat.genome.Genome;
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
    public void evaluateAll(List<Genome> genomes) {
        RaceEvaluator evaluator = new RaceEvaluator(raceContext, genomes);
        evaluator.evaluateAll();
    }
}
