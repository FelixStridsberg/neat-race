package com.vadeen.race.io;

import com.vadeen.race.game.CarProperties;
import com.vadeen.race.game.Sensor;

import java.io.IOException;
import java.util.List;

public class Settings {

    private final List<Sensor> sensors;
    private final CarProperties carProperties;
    private final String trackName;
    private final boolean debugTrack;

    public static Settings fromResources() throws IOException {
        SettingsLoader resourceLoader = new SettingsLoader();
        return resourceLoader.loadSettings();
    }

    public Settings(List<Sensor> sensors, CarProperties carProperties, String trackName, boolean debugTrack) {
        this.sensors = sensors;
        this.carProperties = carProperties;
        this.trackName = trackName;
        this.debugTrack = debugTrack;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public CarProperties getCarProperties() {
        return carProperties;
    }

    public String getTrackName() {
        return trackName;
    }

    public boolean isDebugTrack() {
        return debugTrack;
    }
}
