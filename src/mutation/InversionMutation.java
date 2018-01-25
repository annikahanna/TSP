package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.Collections;

public class InversionMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        int startMutate = getStartMutateIndex(tour);
        int endMutate = getEndMutateIndex(tour, startMutate);

        return InverseCities(tour, startMutate, endMutate);
    }

    private int getStartMutateIndex(Tour tour)
    {
        MersenneTwisterFast random = new MersenneTwisterFast();
        return random.nextInt(tour.getCities().size());
    }

    private int getEndMutateIndex(Tour tour, int minIndex)
    {
        MersenneTwisterFast random = new MersenneTwisterFast();
        return random.nextInt(minIndex + 1, tour.getSize());
    }

    private Tour InverseCities(Tour tour, int from, int to)
    {
        ArrayList<City> reversedCities = tour.getCities();
        ArrayList<City> cities = new ArrayList<>(tour.getCities().subList(from, to));

        Collections.reverse(cities);

        for(int i = 0; i < cities.size(); i++) {
            reversedCities.set(from+i, cities.get(i));
        }

        tour.setCities(reversedCities);
        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}