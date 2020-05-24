package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadeen.race.game.Checkpoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TrackLoader {
    private static final String RESOURCE_PATH = "tracks/";

    private static class TrackJson {
        @JsonProperty
        private java.util.List<Point> vector;

        @JsonProperty
        private List<Checkpoint> checkpoints;
    }

    public TrackDefinition loadDefinition(String name) throws IOException {
        TrackJson json = loadJson(name);
        Polygon bounds = new Polygon();
        for (Point p : json.vector) {
            bounds.addPoint(p.x, p.y);
        }

        Image backgroundImage = loadBackground(name);
        return new TrackDefinition(backgroundImage, bounds, json.checkpoints);
    }

    private Image loadBackground(String name) throws IOException {
        try (InputStream imageStream = getResource(name + ".png")) {
            return ImageIO.read(imageStream);
        }
    }

    private TrackJson loadJson(String name) throws IOException {
        try (InputStream jsonStream = getResource(name + ".json")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonStream, TrackJson.class);
        }
    }

    private InputStream getResource(String fileName) throws IOException {
        ClassLoader classLoader = TrackDefinition.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(RESOURCE_PATH + fileName);

        if (stream == null)
            throw new IOException("Unable to load resource 'tracks/" + fileName + "'.");

        return stream;
    }
}
