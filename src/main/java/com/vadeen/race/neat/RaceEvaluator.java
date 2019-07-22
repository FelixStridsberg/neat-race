package com.vadeen.race.neat;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomePropagator;
import com.vadeen.neat.gui.Gui;
import com.vadeen.neat.species.Species;
import com.vadeen.race.game.Car;
import com.vadeen.race.game.RaceContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The RaceEvaluator runs all the cars in one race.
 */
public class RaceEvaluator {

    private static final int TICK_TIMEOUT = 5000;
    private static final int MAX_TICKS_WITH_NO_PROGRESS = 100;

    private final RaceContext raceContext;
    private List<CarEvaluator> carEvaluators;

    public RaceEvaluator(RaceContext raceContext, List<Species> species) {
        this.raceContext = raceContext;
        this.carEvaluators = createCarEvaluators(species);
    }

    public void evaluateAll() {
        raceContext.reset();

        int noProgress = 0;
        for (int i = 0; i < TICK_TIMEOUT && noProgress < MAX_TICKS_WITH_NO_PROGRESS; i++) {
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

            if (carEvaluator.getCar().getLastProgress() > MAX_TICKS_WITH_NO_PROGRESS)
                carEvaluator.getCar().setCrashed(true);
        }

        return progress;
    }

    private List<CarEvaluator> createCarEvaluators(List<Species> species) {
        List<CarEvaluator> carEvaluators = new ArrayList<>();

        // Create propagators and add cars to track.
        for (Species s : species) {
            Color speciesColor = Gui.colorOfId(s.getId());

            for (Genome g : s.getGenomes()) {
                Car car = new Car(speciesColor);
                GenomePropagator propagator = new GenomePropagator(g);
                CarEvaluator carEvaluator = new CarEvaluator(raceContext, car, g, propagator);

                carEvaluators.add(carEvaluator);
                raceContext.addCar(car);
            }
        }
        return carEvaluators;
    }
}