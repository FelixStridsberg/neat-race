package com.vadeen.race.gui;

import com.vadeen.neat.Neat;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.genome.GenomeMutator;
import com.vadeen.neat.gui.NeatGui;
import com.vadeen.neat.species.SpeciesFactory;
import com.vadeen.race.game.RaceContext;
import com.vadeen.race.game.Sensor;
import com.vadeen.race.game.Track;
import com.vadeen.race.neat.GenomeEvaluator;
import com.vadeen.race.neat.Visualizer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Race {

    private static final List<Sensor> SENSORS = Arrays.asList(
            new Sensor(1.2f),
            new Sensor(0.8f), new Sensor(0.0f), new Sensor(-0.8f),
            new Sensor(-1.2f)
    );

    private final Neat neat;
    private final RaceContext raceContext;

    private Race(Neat neat, RaceContext raceContext) {
        this.neat = neat;
        this.raceContext = raceContext;
    }

    private void run() {
        TrackPanel tp = new TrackPanel(raceContext);
        Visualizer visualizer = new Visualizer(raceContext);

        NeatGui gui = new NeatGui(neat, visualizer, tp);
        gui.run();
    }

    public static void main(String[] args) throws IOException {
        Track track = Track.fromFiles(new File("src/main/resources/tracks"), "pecker");
        RaceContext raceContext = new RaceContext(track, SENSORS);

        GenomeEvaluator evaluator = new GenomeEvaluator(raceContext);
        Neat neat = createNEAT(evaluator);

        Race race = new Race(neat, raceContext);
        race.run();
    }

    private static Neat createNEAT(GenomeEvaluator evaluator) {
        Neat neat = Neat.create(evaluator, SENSORS.size(), 2);

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
