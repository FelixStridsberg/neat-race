package com.vadeen.race.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {
    private final String resourcePath;

    public ResourceLoader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Image loadImage(String filename) throws IOException {
        try (InputStream imageStream = getResource(filename)) {
            return ImageIO.read(imageStream);
        }
    }

    public <T> T loadJson(String filename, Class<T> valueType) throws IOException {
        try (InputStream jsonStream = getResource(filename)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonStream, valueType);
        }
    }

    private InputStream getResource(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(resourcePath + fileName);

        if (stream == null)
            throw new IOException("Unable to load resource '" + fileName + "'.");

        return stream;
    }
}
