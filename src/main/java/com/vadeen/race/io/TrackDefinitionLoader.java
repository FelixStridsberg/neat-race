package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vadeen.race.game.CarPosition;
import com.vadeen.race.game.Checkpoint;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackDefinitionLoader extends ResourceLoader {

    private static final String RESOURCE_PATH = "tracks/";

    private static class CarPositionJson {
        @JsonProperty
        private float x;

        @JsonProperty
        private float y;

        @JsonProperty
        private float rotation;
    }

    private static class CheckpointJson {
        @JsonProperty
        private int size;

        @JsonProperty
        private int x;

        @JsonProperty
        private int y;
    }

    private static class TrackJson {
        @JsonProperty
        private CarPositionJson startPosition;

        @JsonProperty
        private List<Point> vector;

        @JsonProperty
        private List<CheckpointJson> checkpoints;
    }

    public TrackDefinitionLoader() {
        super(RESOURCE_PATH);
    }

    public TrackDefinition loadDefinition(String name) throws IOException {
        TrackJson json = loadJson(name + ".json", TrackJson.class);

        Polygon bounds = new Polygon();
        for (Point p : json.vector) {
            bounds.addPoint(p.x, p.y);
        }

        List<Checkpoint> checkpoints = new ArrayList<>();
        for (CheckpointJson c : json.checkpoints) {
            checkpoints.add(new Checkpoint(c.x, c.y, c.size));
        }

        float rotation = (float)Math.toRadians(json.startPosition.rotation);
        CarPosition startPosition = new CarPosition(json.startPosition.x, json.startPosition.y, rotation);

        Image backgroundImage = loadImage(name+ ".png");
        return new TrackDefinition(backgroundImage, startPosition, bounds, checkpoints);
    }
}
