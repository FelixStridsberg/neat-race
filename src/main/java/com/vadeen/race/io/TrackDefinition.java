package com.vadeen.race.io;

import com.vadeen.race.game.Checkpoint;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class TrackDefinition {
    private final Image backgroundImage;
    private final Polygon bounds;
    private final List<Checkpoint> checkpoints;

    public static TrackDefinition fromResource(String name) throws IOException {
        TrackDefinitionLoader loader = new TrackDefinitionLoader();
        return loader.loadDefinition(name);
    }

    public TrackDefinition(Image backgroundImage, Polygon bounds, List<Checkpoint> checkpoints) {
        this.backgroundImage = backgroundImage;
        this.bounds = bounds;
        this.checkpoints = checkpoints;
    }

    public Image getBackground() {
        return backgroundImage;
    }

    public Polygon getBounds() {
        return bounds;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
