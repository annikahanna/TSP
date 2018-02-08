package crossover;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the OrderedCrossover algorithm
 */
public class OrderedCrossover extends AbstractCrossover {

    @Override
    public Tour doCrossover(Tour tour01, Tour tour02) {
        MersenneTwisterFast random = Configuration.instance.Random;

        int citiesCount = tour01.getSize();
        int startIndex = random.nextInt(0, citiesCount - 1);
        int length = random.nextInt(1, citiesCount - startIndex);

        ArrayList<City> childCities = new ArrayList<City>();

        //extract sublist of cities given by the random generated range
        List<City> p1Cities = new ArrayList<City>(tour01.getCities().subList(startIndex, startIndex + length));
        List<City> p2Cities = new ArrayList<City>(tour02.getCities());
        p2Cities.removeAll(p1Cities);

        for (int i = 0; i < citiesCount; i++)
            if (i < startIndex || i >= startIndex + length)
                childCities.add(p2Cities.remove(0));
            else
                childCities.add(p1Cities.remove(0));

        //create and return new tour
        Tour newTour = new Tour();
        newTour.setCities(childCities);
        return newTour;
    }

}