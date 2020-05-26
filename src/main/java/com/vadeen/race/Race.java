package com.vadeen.race;

import com.vadeen.neat.Neat;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.genome.GenomeMutator;
import com.vadeen.neat.gui.NeatGui;
import com.vadeen.neat.species.SpeciesFactory;
import com.vadeen.race.game.CarProperties;
import com.vadeen.race.game.RaceContext;
import com.vadeen.race.game.Sensor;
import com.vadeen.race.game.Track;
import com.vadeen.race.gui.TrackPanel;
import com.vadeen.race.io.Settings;
import com.vadeen.race.neat.GenomeEvaluator;
import com.vadeen.race.neat.RaceVisualizer;

import java.io.IOException;
import java.util.List;

public class Race {
    private final Neat neat;
    private final RaceContext raceContext;

    public static void main(String[] args) throws IOException {
        Settings settings = Settings.fromResources();
        Track track = Track.fromResources(settings.getTrackName());

        List<Sensor> sensors = settings.getSensors();
        CarProperties carProperties = settings.getCarProperties();
        RaceContext raceContext = new RaceContext(track, sensors, carProperties);

        GenomeEvaluator evaluator = new GenomeEvaluator(raceContext);
        Neat neat = createNEAT(evaluator, sensors.size());

        Race race = new Race(neat, raceContext);
        race.run(settings);
    }

    private Race(Neat neat, RaceContext raceContext) {
        this.neat = neat;
        this.raceContext = raceContext;
    }

    private void run(Settings settings) {
        TrackPanel tp = new TrackPanel(raceContext);
        tp.setDebugTrack(settings.isDebugTrack());

        RaceVisualizer visualizer = new RaceVisualizer(raceContext);

        NeatGui gui = new NeatGui(neat, visualizer, tp);
        gui.run();
    }

    private static Neat createNEAT(GenomeEvaluator evaluator, int inputs) {
        Neat neat = Neat.create(evaluator, inputs, 2);

        neat.getGenerationFactory().setPopulationSize(50);

        // Config mutator
        GenomeMutator mutator = neat.getMutator();
        mutator.setWeightPerturbingFactor(9.0f);
        mutator.setNodeMutationProbability(0.1f);
        mutator.setConnectionMutationProbability(0.95f);
        mutator.setWeightMutationProbability(0.8f);

        // Config comparator
        GenomeComparator comparator = neat.getGenomeComparator();
        comparator.setDisjointFactor(0.1f);
        comparator.setExcessFactor(0.1f);
        comparator.setWeightDiffFactor(0.06f);

        // Config species factory
        SpeciesFactory speciesFactory = neat.getSpeciesFactory();
        speciesFactory.setDistanceThreshold(3);

        return neat;
    }
}
