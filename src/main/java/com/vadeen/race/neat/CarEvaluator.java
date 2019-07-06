package com.vadeen.race.neat;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomePropagator;
import com.vadeen.race.game.Car;
import com.vadeen.race.game.RaceContext;

import java.util.List;

/**
 * The CarEvaluator lets a Genome drive a Car.
 */
public class CarEvaluator {

    private RaceContext raceContext;
    private final Car car;
    private final Genome genome;
    private final GenomePropagator propagator;

    public CarEvaluator(RaceContext raceContext, Car car, Genome genome, GenomePropagator propagator) {
        this.raceContext = raceContext;
        this.car = car;
        this.genome = genome;
        this.propagator = propagator;
    }

    public Car getCar() {
        return car;
    }

    /**
     * @return true if the fitness increased.
     */
    public boolean evaluate() {
        List<Float> inputs = raceContext.getSensorValues(car);
        List<Float> outputs = propagator.propagate(inputs);

        car.accelerate(outputs.get(0)*2 - 1);
        car.turn(outputs.get(1)*2 - 1);

        boolean progress = raceContext.updateCar(car);
        genome.setFitness(car.getFitness());
        return progress;
    }
}
