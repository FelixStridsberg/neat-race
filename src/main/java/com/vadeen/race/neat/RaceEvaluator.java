package com.vadeen.race.neat;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomePropagator;
import com.vadeen.race.game.Car;
import com.vadeen.race.game.RaceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * The RaceEvaluator runs all the cars in one race.
 */
public class RaceEvaluator {

    private static final int MAX_TICKS_WITH_NO_PROGRESS = 100;

    private final RaceContext raceContext;
    private List<CarEvaluator> carEvaluators;

    public RaceEvaluator(RaceContext raceContext, List<Genome> genomes) {
        this.raceContext = raceContext;
        this.carEvaluators = createCarEvaluators(genomes);
    }

    public void evaluateAll() {
        int noProgress = 0;
        while (noProgress < MAX_TICKS_WITH_NO_PROGRESS) {
            if (tick()) {
                noProgress = 0;
            }
            else {
                noProgress++;
            }
        }
    }

    public boolean tick() {
        boolean progress = false;

        for (CarEvaluator carEvaluator : carEvaluators) {
            if (carEvaluator.getCar().isCrashed())
                continue;

            if (carEvaluator.evaluate())
                progress = true;
        }

        return progress;
    }

    private List<CarEvaluator> createCarEvaluators(List<Genome> genomes) {
        List<CarEvaluator> carEvaluators = new ArrayList<>();

        // Create propagators and add cars to track.
        for (Genome genome : genomes) {
            Car car = new Car();
            GenomePropagator propagator = new GenomePropagator(genome);
            CarEvaluator carEvaluator = new CarEvaluator(raceContext, car, genome, propagator);

            carEvaluators.add(carEvaluator);
            raceContext.addCar(car);
        }
        return carEvaluators;
    }
}