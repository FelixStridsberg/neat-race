package com.vadeen.race.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadeen.neat.Neat;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.genome.GenomeMutator;
import com.vadeen.neat.gui.VisualPanel;
import com.vadeen.neat.species.Species;
import com.vadeen.neat.species.SpeciesFactory;
import com.vadeen.race.game.RaceContext;
import com.vadeen.race.game.Sensor;
import com.vadeen.race.game.Track;
import com.vadeen.race.neat.GenomeEvaluator;
import com.vadeen.race.neat.RaceEvaluator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Race {
    private static int TARGET_FITNESS = 200;
    private static int MAX_GENERATIONS = 500;

    private static final List<Sensor> SENSORS = Arrays.asList(
            new Sensor(1.6f),
            new Sensor(1.2f),
            new Sensor(0.8f), new Sensor(0.0f), new Sensor(-0.8f),
            new Sensor(-1.2f),
            new Sensor(-1.6f)
    );

    private final Neat neat;
    private final RaceContext raceContext;
    private final RaceSimulator simulator;
    private final VisualPanel visualPanel;

    private Race(Neat neat, RaceContext raceContext, RaceSimulator simulator, VisualPanel visualPanel) {
        this.neat = neat;
        this.raceContext = raceContext;
        this.simulator = simulator;
        this.visualPanel = visualPanel;
    }

    private void run() throws JsonProcessingException {
        Genome bestGenome = null;
        float lastFitness = 0.0f;

        for (int i = 0; i < MAX_GENERATIONS; i++) {
            // Reset context (i.e. remove old cars)
            raceContext.reset();

            // Evolve to next generation.
            Generation g = neat.evolve();

            // Show info about the new generation.
            visualPanel.addGeneration(i, g);


            // Simulate the best genome if it is better than the last simulation we saw.
            bestGenome = g.getBestGenome();
            if (bestGenome.getFitness() > lastFitness) {
                raceContext.reset();
                List<Genome> allGenomes = copyAllGenomes(g);
                simulator.simulate(new RaceEvaluator(raceContext, allGenomes));
                lastFitness = bestGenome.getFitness();
            }

            // Quit if we reach the target fitness.
            if (bestGenome.getFitness() >= TARGET_FITNESS) {
                System.out.println("Target fitness reached!");
                break;
            }
        }

        // Print the best genome json so it can be restored.
        printBestGenome(bestGenome);

        // Let the best genome shine by it self.
        simulator.simulate(new RaceEvaluator(raceContext, Collections.singletonList(bestGenome)));
    }

    public static void main(String[] args) throws IOException {
        Track track = Track.fromFiles(new File("src/main/resources/tracks"), "pecker");
        RaceContext raceContext = new RaceContext(track, SENSORS);

        GenomeEvaluator evaluator = new GenomeEvaluator(raceContext);
        Neat neat = createNEAT(evaluator);

        JFrame simulatorFrame = new JFrame("NEAT Race");
        simulatorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        simulatorFrame.setBounds(0, 200, 800, 600);

        RaceSimulator simulator = new RaceSimulator(simulatorFrame, raceContext);


        VisualPanel visualPanel = new VisualPanel();
        JFrame visualPanelFrame = new JFrame("GenomeTree");
        visualPanelFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        visualPanelFrame.setBackground(Color.BLACK);
        visualPanelFrame.setBounds(800, 200, 800, 600);
        visualPanelFrame.add(visualPanel);
        visualPanelFrame.setVisible(true);

        Race race = new Race(neat, raceContext, simulator, visualPanel);
        race.run();
    }

    private static Neat createNEAT(GenomeEvaluator evaluator) {
        Neat neat = Neat.create(evaluator, 7, 3);

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

    private static List<Genome> copyAllGenomes(Generation gen) {
        List<Genome> allGenomes = new ArrayList<>();
        for (Species s : gen.getSpecies()) {
            for (Genome g : s.getGenomes()) {
                allGenomes.add(Genome.copy(g));
            }
        }
        return allGenomes;
    }

    private void printBestGenome(Genome best) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Best genome: " + mapper.writeValueAsString(best));
    }
}
