package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.race.game.Checkpoint;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TrackDefinitionLoader extends ResourceLoader {

    private static final String RESOURCE_PATH = "tracks/";

    private static class TrackJson {
        @JsonProperty
        private java.util.List<Point> vector;

        @JsonProperty
        private List<Checkpoint> checkpoints;
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

        Image backgroundImage = loadImage(name+ ".png");
        return new TrackDefinition(backgroundImage, bounds, json.checkpoints);
    }
}
