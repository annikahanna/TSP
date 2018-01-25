package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

/**
 * Implementation of the OrderedCrossover algorithm
 */
public class OrderedCrossover implements ICrossover {

    private static MersenneTwisterFast mtF = new MersenneTwisterFast();

    public Tour doCrossover(Tour tour01, Tour tour02) {

        int citiesCount = tour01.getSize();
        int startIndex = mtF.nextInt(0, citiesCount - 2);
        int length = mtF.nextInt(1, citiesCount - startIndex - 1);

        ArrayList<City> newCities = new ArrayList<>(tour02.getCities()); //workaround initial capacity
        ArrayList<City> citiesToAdd = new ArrayList<>(tour02.getCities());

        for (int i = startIndex; i < startIndex + length; i++) {
            //copy city from tour01
            newCities.set(i, tour01.getCity(i));
            //check for duplicate cities in parent2
            if (citiesToAdd.contains(newCities.get(i)))
                citiesToAdd.remove(newCities.get(i));
        }

        for (int i = 0; i < citiesCount; i++)
            if (i < startIndex || i > startIndex + length)
                newCities.set(i, citiesToAdd.remove(0));

        //create and return new tour
        Tour newTour = new Tour();
        newTour.setCities(newCities);
        return newTour;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}