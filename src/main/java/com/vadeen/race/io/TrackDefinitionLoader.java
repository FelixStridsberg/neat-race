package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.race.game.Checkpoint;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackDefinitionLoader extends ResourceLoader {

    private static final String RESOURCE_PATH = "tracks/";

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

        Image backgroundImage = loadImage(name+ ".png");
        return new TrackDefinition(backgroundImage, bounds, checkpoints);
    }
}
