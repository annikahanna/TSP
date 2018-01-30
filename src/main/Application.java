package main;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

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
        int rounds = 5;

        for (int i = 0; i < rounds; i++) {
            Tour currentTour = new Tour();
            ArrayList<City> availableCitiesForCurrentTour = availableCities;
            for (int y = 0; y < rounds; y++) {
                int r = 0;
                if (availableCitiesForCurrentTour.size() - 1 != 0) {
                    r = randomizer.nextInt(availableCitiesForCurrentTour.size() - 1);


                    currentTour.addCity(availableCitiesForCurrentTour.get(r));
                    availableCitiesForCurrentTour.remove(r);
                }
            }
                if (tours.contains(currentTour)) {
                    i--;
                    continue;
                } else {
                    tours.add(currentTour);
                }

            population.setTours(tours);
        }
        return population;

    }

    public void execute()  {
        System.out.println("--- GeneticAlgorithm.execute()");
        Population start = startPopulation();
        JSONParser parser = new JSONParser();
        JSONArray scenarios = new JSONArray();

       try {
           scenarios = (JSONArray) parser.parse(new FileReader("D:/tsp/01_tsp/configuration/genetic_algorithm_tsp.json"));
       }
       catch (Exception e) {
           e.getStackTrace();
       }

      for(int i = 0; i< 25; i++) {
           JSONObject scenario = (JSONObject) scenarios.get(i);
           while(s)



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