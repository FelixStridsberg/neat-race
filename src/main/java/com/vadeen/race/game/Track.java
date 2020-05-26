package com.vadeen.race.game;

import com.vadeen.race.io.TrackDefinition;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The track contains information about the boundaries, checkpoints and a background image.
 * The checkpoints consists of points in the middle of the track. A radius is added to the points to make them fill the
 * track. It do not matter if the checkpoints overlap a bit.
 */
public class Track {

    private final Polygon bounds;
    private final List<Checkpoint> checkpoints;
    private final CarPosition startPosition;
    private final Image background;
    private final List<Car> cars = new ArrayList<>();

    public static Track fromResources(String name) throws IOException {
        TrackDefinition trackDefinition = TrackDefinition.fromResource(name);

        Polygon bounds = trackDefinition.getBounds();
        List<Checkpoint> checkpoints = trackDefinition.getCheckpoints();
        CarPosition startPosition = trackDefinition.getStartPosition();
        Image background = trackDefinition.getBackground();
        return new Track(bounds, checkpoints, startPosition, background);
    }

    public Track(Polygon bounds, List<Checkpoint> checkpoints, CarPosition startPosition, Image background) {
        this.background = background;
        this.startPosition = startPosition;
        this.bounds = bounds;
        this.checkpoints = checkpoints;
    }

    public void update(Car car) {
        car.updatePosition();

        if (!bounds.contains(car.getPosition())) {
            car.setCrashed(true);
            return;
        }

        updateCheckpoint(car);
    }

    private void updateCheckpoint(Car car) {
        int checkpointIndex = car.getCurrentCheckpoint();

        Checkpoint checkpoint = checkpoints.get(checkpointIndex);
        if (checkpoint.contains(car.getPosition()))
            return;

        int nextCheckpointIndex = (checkpointIndex + 1) % checkpoints.size();
        Checkpoint nextCheckpoint = checkpoints.get(nextCheckpointIndex);

        if (nextCheckpoint.contains(car.getPosition())) {
            car.addFitness();
            car.setCurrentCheckpoint(nextCheckpointIndex);
        }
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void clearCars() {
        cars.clear();
    }

    public List<Car> getCars() {
        return cars;
    }

    public Image getBackground() {
        return background;
    }

    public Polygon getBounds() {
        return bounds;
    }

    public CarPosition getStartPosition() {
        return new CarPosition(startPosition);
    }
}
