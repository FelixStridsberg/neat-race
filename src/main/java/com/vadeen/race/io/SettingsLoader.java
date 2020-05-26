package com.vadeen.race.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.race.game.CarProperties;
import com.vadeen.race.game.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsLoader extends ResourceLoader {

    private static class SettingsJson {
        @JsonProperty
        private String trackName;

        @JsonProperty
        private Boolean debugTrack;

        @JsonProperty
        private List<Float> sensors;

        @JsonProperty
        private CarPropertiesJson carProperties;
    }

    private static class CarPropertiesJson {
        @JsonProperty
        private float acceleration;

        @JsonProperty
        private float turnSpeed;

        @JsonProperty
        private float maxSpeed;
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

        CarPropertiesJson carPropJson = json.carProperties;
        CarProperties carProperties = new CarProperties(
                carPropJson.acceleration, carPropJson.turnSpeed, carPropJson.maxSpeed);

        return new Settings(sensors, carProperties, json.trackName, json.debugTrack);
    }
}
