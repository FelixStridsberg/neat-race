package com.vadeen.race.io;

import com.vadeen.race.game.Sensor;

import java.io.IOException;
import java.util.List;

public class Settings {

    private final List<Sensor> sensors;

    public static Settings fromResources() throws IOException {
        SettingsLoader resourceLoader = new SettingsLoader();
        return resourceLoader.loadSettings();
    }

    public Settings(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
