package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.race.game.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsLoader extends ResourceLoader {

    private static class SettingsJson {
        @JsonProperty
        private List<Float> sensors;
    }

    public SettingsLoader() {
        super("");
    }

    public Settings loadSettings() throws IOException {
        SettingsJson json = loadJson("settings.json", SettingsJson.class);

        List<Sensor> sensors = new ArrayList<>();
        for (Float angle : json.sensors) {
            sensors.add(new Sensor(angle));
        }

        return new Settings(sensors);
    }
}
