package test;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestTourHelper {

    /**
     * Don´t use it!
     */
    private TestTourHelper() {
    }

    /**
     * Creates a dummy tour with {@code size} cities with ascending city ids starting with 0
     *
     * @param size count of cities
     * @return tour with {@paramref size} cities
     */
    public static Tour getAscendingTour(int size) {
        Tour tour = new Tour();

        for (int i = 0; i < size; i++) {
            tour.addCity(new City(i, i, i));
        }

        return tour;
    }

    /**
     * Returns the list of city ids of a {@code tour}
     *
     * @param tour
     * @return
     */
    public static List<Integer> getTourCityIds(Tour tour) {
        ArrayList<Integer> cityIds = new ArrayList<>();

        for (City city : tour.getCities()) {
            cityIds.add(city.getId());
        }

        return cityIds;
    }

    /**
     * Generates cities
     *
     * @param count     count
     * @param randomize randomize the generated cities
     * @return List containing the generated cities
     */
    public static List<City> generateRandomCities(int count, boolean randomize) {
        MersenneTwisterFast random = Configuration.instance.Random;
        List<City> generatedCitites = new ArrayList<>();
        for (int i = 0; i < count; i++)
            generatedCitites.add(new City(i, random.nextDouble() * 200, random.nextDouble() * 200));
        if (randomize)
            Collections.shuffle(generatedCitites, random);
        return generatedCitites;
    }

    /**
     * Generates cities
     *
     * @param count count
     * @return List containing the generated cities
     */
    public static List<City> generateCitites(int count) {
        return generateRandomCities(count, false);
    }

    /**
     * Randomzies a given List of Cities
     *
     * @param citiesToRandomize Citites to randomzie
     * @return a new List containing the randomized cities
     */
    public static List<City> randomizeCities(List<City> citiesToRandomize) {
        List<City> randomizedCities = new ArrayList<>(citiesToRandomize);
        Collections.shuffle(randomizedCities, Configuration.instance.Random);
        return randomizedCities;
    }

}