package com.vadeen.race.io;

import com.vadeen.race.game.CarProperties;
import com.vadeen.race.game.Sensor;

import java.io.IOException;
import java.util.List;

public class Settings {

    private final List<Sensor> sensors;
    private final CarProperties carProperties;

    public static Settings fromResources() throws IOException {
        SettingsLoader resourceLoader = new SettingsLoader();
        return resourceLoader.loadSettings();
    }

    public Settings(List<Sensor> sensors, CarProperties carProperties) {
        this.sensors = sensors;
        this.carProperties = carProperties;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public CarProperties getCarProperties() {
        return carProperties;
    }
}
