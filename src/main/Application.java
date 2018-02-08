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
        //start bruteforce
        permutation.generate(tspLibReader, false);
    }

    public void initConfiguration() {
        System.out.println("--- GeneticAlgorithm.initConfiguration()");
        System.out.println();
    }

    //creates new population at the beginnig of every new scenario
    public Population startPopulation() {
        System.out.println("--- GeneticAlgorithm.startPopulation()");

        Population population = new Population();
        ArrayList<Tour> tours = new ArrayList<Tour>();
        MersenneTwisterFast randomizer = new MersenneTwisterFast();


        for (int i = 0; i < 100; i++) {
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

    //sort population by minimal fitness = best Value
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
        JSONParser parser = new JSONParser();
        JSONArray scenarios = new JSONArray();

        //read all scenarios from json file
        try {
            scenarios = (JSONArray) parser.parse(new FileReader(Configuration.instance.userDirectory + Configuration.instance.fileSeparator + "configuration" + Configuration.instance.fileSeparator + "genetic_algorithm_tsp.json"));
        } catch (Exception e) {
            e.getStackTrace();
        }

        //iteration for every scenario
        for (int i = 0; i < 25; i++) {
            Population start = startPopulation();
            Population scenarioPopulation = start;
            ArrayList<Tour> select = new ArrayList<Tour>();
            Tour cross = new Tour();
            long iteration = 0;
            long mutationrate = 0;
            JSONObject scenario = (JSONObject) scenarios.get(i);
            System.out.println("Scenario " + scenario.get("id"));
            //choose right classes
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
            //starts algorithem until 1000000 iterations are done or best value found
            while (bestTour.getFitness() > Configuration.instance.optimum) {
                //do selection
                select = selection.doSelection(scenarioPopulation);
                //do crossover
                //matches all pairs
                for (int k = 0; k < select.size() - 1; k++) {
                    cross = crossover.doCrossover(select.get(k), select.get(k + 1));
                    //   System.out.println("crossover done");
                    //mutate one tour after 200 iterations
                    if (mutationrate % 200 == 0) {
                        cross = mutation.doMutation(cross);
                        System.out.println("mutation done");
                    }
                    mutationrate++;
                    //add tour to scenario
                    scenarioPopulation.addTourToPopulation(cross);
                }
                //finds new best tour
                bestTour = getBestTour(scenarioPopulation);
                System.out.println(bestTour.getFitness());
                iteration++;
                int scene = i + 1;
                //save new values in db
                HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(iteration,
                        1000000, bestTour.getFitness(), scene));
                //after 1.000.000 iterations break
                if (iteration == 1000000) {
                    break;
                }
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