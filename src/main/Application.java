package main;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import data.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import base.City;
import base.Population;
import base.Tour;
import bruteforce.Permutation;
import crossover.ICrossover;
import mutation.IMutation;
import random.MersenneTwisterFast;
import selection.ISelection;

public class Application {
    private ArrayList<City> availableCities;
    private double[][] distances;

    private ISelection selection;
    private ICrossover crossover;
    private IMutation mutation;

    public void startupHSQLDB() {
        HSQLDBManager.instance.startup();
        HSQLDBManager.instance.init();
    }

    public void shutdownHSQLDB() {
        HSQLDBManager.instance.shutdown();
    }

    public void printMatrix(double[][] matrix) {
        DecimalFormat decimalFormat = new DecimalFormat("000.00");

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix[0].length; columnIndex++)
                System.out.print(decimalFormat.format(matrix[rowIndex][columnIndex]) + "\t");
            System.out.println();
        }
    }

    public void loadData() {
        Permutation permutation = new Permutation();
        System.out.println("--- GeneticAlgorithm.loadData()");
        InstanceReader instanceReader = new InstanceReader(Configuration.instance.dataFilePath);
        instanceReader.open();
        TSPLIBReader tspLibReader = new TSPLIBReader(instanceReader);

        availableCities = tspLibReader.getCities();
        System.out.println("availableCities (size) : " + availableCities.size());

        distances = tspLibReader.getDistances();
        printMatrix(distances);

        instanceReader.close();

        System.out.println();
        // permutation.generate(tspLibReader, false);
    }

    public void initConfiguration() {
        System.out.println("--- GeneticAlgorithm.initConfiguration()");
        System.out.println();
    }

    public Population startPopulation() {
        System.out.println("--- GeneticAlgorithm.startPopulation()");

        Population population = new Population();
        ArrayList<Tour> tours = new ArrayList<Tour>();
        MersenneTwisterFast randomizer = new MersenneTwisterFast();


        for (int i = 0; i < 5; i++) {
            Tour tour = new Tour();
            ArrayList<City> cities = new ArrayList<City>(availableCities);
            int rounds = 279;
            for (int j = 0; j < 280; j++) {
                rounds--;
                int rand = randomizer.nextInt(0, rounds);
                tour.addCity(cities.get(rand));
                cities.remove(rand);

            }
            tours.add(tour);
        }
        population.setTours(tours);
        return population;


    }

    public Tour getBestTour(Population population) {
        for (int i = 0; i < population.getSize() - 1; i++) {
            for (int j = i + 1; j < population.getSize(); j++) {
                if (population.getSingleTour(i).getFitness() > population.getSingleTour(j).getFitness()) {
                    Tour temp = population.getSingleTour(i);
                    population.setSingleTour(i, population.getSingleTour(j));
                    population.setSingleTour(j, temp);
                }
            }
        }
        return population.getSingleTour(0);
    }


    public void execute() {
        System.out.println("--- GeneticAlgorithm.execute()");
        Population start = startPopulation();
        JSONParser parser = new JSONParser();
        JSONArray scenarios = new JSONArray();

        try {
            scenarios = (JSONArray) parser.parse(new FileReader(Configuration.instance.userDirectory + Configuration.instance.fileSeparator + "configuration" + Configuration.instance.fileSeparator + "genetic_algorithm_tsp.json" ));
        } catch (Exception e) {
            e.getStackTrace();
        }

        for (int i = 0; i < 25; i++) {
            Population scenarioPopulation = start;
            ArrayList<Tour> select = new ArrayList<Tour>();
            Tour cross = new Tour();
            long mutationrate = 0;
            long iteration = 0;
            JSONObject scenario = (JSONObject) scenarios.get(i);
            System.out.println("Scenario " + scenario.get("id"));
            try {
                Class clazz = Class.forName(scenario.get("selection").toString());
                selection = (ISelection) clazz.newInstance();
            } catch (Exception e) {
                e.getStackTrace();
            }
            try {
                Class clazz = Class.forName(scenario.get("crossover").toString());
                crossover = (ICrossover) clazz.newInstance();
            } catch (Exception e) {
                e.getStackTrace();
            }
            try {
                Class clazz = Class.forName(scenario.get("mutation").toString());
                mutation = (IMutation) clazz.newInstance();
            } catch (Exception e) {
                e.getStackTrace();
            }
            Tour bestTour = getBestTour(start);
            while (bestTour.getFitness() > Configuration.instance.optimum) {
                select = selection.doSelection(scenarioPopulation);
                System.out.println("selction done");
                cross = crossover.doCrossover(select.get(0), select.get(1));
                if (mutationrate % 200 == 0) {
                    cross = mutation.doMutation(cross);
                }
                scenarioPopulation.addTourToPopulation(cross);
                bestTour = getBestTour(scenarioPopulation);
                iteration++;

                if (iteration == 1000000) {
                    break;
                }
                System.out.println(bestTour.getFitness());
            }
            System.out.println("Best Tour found with the value of " + bestTour.getFitness());

        }
    }


    public static void main(String... args) {

        Application application = new Application();
        application.startupHSQLDB();
        application.loadData();

        application.initConfiguration();
        application.execute();
        application.shutdownHSQLDB();
    }
}