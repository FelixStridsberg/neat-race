package com.vadeen.race.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The race context contains everything needed for evaluating and simulating a race.
 */
public class RaceContext {

    private final Track track;
    private final List<Sensor> sensors;
    private final CarProperties carProperties;

    public RaceContext(Track track, List<Sensor> sensors, CarProperties carProperties) {
        this.track = track;
        this.sensors = sensors;
        this.carProperties = carProperties;
    }

    // Reset race for a new evaluation.
    public void reset() {
        this.track.clearCars();
    }

    public void addCar(Car car) {
        this.track.addCar(car);
    }

    public boolean updateCar(Car car) {
        float startFitness = car.getFitness();
        car.updatePosition();
        track.update(car);

        return car.getFitness() > startFitness;
    }

    public Track getTrack() {
        return track;
    }

    public CarProperties getCarProperties() {
        return carProperties;
    }

    public List<Float> getSensorValues(Car car) {
        List<Float> values = new ArrayList<>();

        for (Sensor s : sensors) {
            values.add((float) s.getValue(track, car));
        }

        return values;
    }
}
