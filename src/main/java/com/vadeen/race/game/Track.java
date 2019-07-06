package com.vadeen.race.game;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Track {

    private final Image background;
    private final Polygon bounds;
    private final List<Checkpoint> checkpoints;

    private final List<Car> cars = new ArrayList<>();

    public static Track fromFiles(File base, String name) throws IOException {
        File backgroundFile = new File(base, name + ".png");
        File jsonFile = new File(base, name + ".json");
        File checkpointJsonFile = new File(base, name + "_checkpoints.json");

        ObjectMapper mapper = new ObjectMapper();

        Point[] points = mapper.readValue(jsonFile, Point[].class);
        Polygon bounds = new Polygon();
        for (Point p : points) {
            bounds.addPoint(p.x, p.y);
        }

        Checkpoint[] checkpoints = mapper.readValue(checkpointJsonFile, Checkpoint[].class);
        Image background = ImageIO.read(backgroundFile);

        return new Track(background, bounds, Arrays.asList(checkpoints));
    }

    public Track(Image background, Polygon bounds, List<Checkpoint> checkpoints) {
        this.background = background;
        this.bounds = bounds;
        this.checkpoints = checkpoints;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
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

    public void update(Car c) {
        c.update();

        // Outside track.
        if (!bounds.contains(c.getPosition())) {
            c.setCrashed(true);
        }

        // TODO clean up this shit.
        int current = c.getCurrentCheckpoint();
        Checkpoint cp = checkpoints.get(current);
        if (!cp.contains(c.getPosition())) {
            Checkpoint nextCp;
            if (current == checkpoints.size() - 1)
                nextCp = checkpoints.get(0);
            else
                nextCp = checkpoints.get(current + 1);

            if (nextCp.contains(c.getPosition())) {
                c.addFitness();
                if (current == checkpoints.size() - 1)
                    c.setCheckpoint(0);
                else
                    c.setCheckpoint(current + 1);
            }

        }
    }

    public Image getBackground() {
        return background;
    }

    public Polygon getBounds() {
        return bounds;
    }
}
