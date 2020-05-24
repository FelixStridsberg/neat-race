package com.vadeen.race.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The race context contains everything needed for evaluating and simulating a race.
 */
public class RaceContext {

    private final Track track;
    private final List<Sensor> sensors;

    public RaceContext(Track track, List<Sensor> sensors) {
        this.track = track;
        this.sensors = sensors;
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
        car.update();
        track.update(car);

        return car.getFitness() > startFitness;
    }

    public Track getTrack() {
        return track;
    }

    public List<Float> getSensorValues(Car car) {
        List<Float> values = new ArrayList<>();

        for (Sensor s : sensors) {
            values.add((float) s.getValue(track, car));
        }

        return values;
    }
}
