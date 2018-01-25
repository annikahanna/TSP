package test;

import base.City;
import base.Tour;
import base.Population;

import main.Configuration;

import java.util.ArrayList;
import java.util.List;

public class TestTourHelper {

    /**
     * Creates a dummy tour with {@code size} cities with ascending city ids starting with 0
     * @param size count of cities
     * @return tour with {@paramref size} cities
     */
    public Tour getAscendingTour(int size)
    {
        Tour tour = new Tour();

        for(int i = 0; i < size; i++) {
            tour.addCity(new City(i, i, i));
        }

        return tour;
    }

    /**
     * Returns the list of city ids of a {@code tour}
     * @param tour
     * @return
     */
    public List<Integer> getTourCityIds(Tour tour)
    {
        ArrayList<Integer> cityIds = new ArrayList<>();

        for(City city : tour.getCities()) {
            cityIds.add(city.getId());
        }

        return cityIds;
    }

    /**
     * Creates a random Population
     * @param populationSize Desired size of population
     * @param tourSize Desired size of tour
     * @return
     */
    public Population getAscendingPopulation(int populationSize, int tourSize, boolean isRandom, double[] cityXy){

        Population p = new Population();
        Tour tour = null;

        for(int k = 0; k < populationSize; k++){
            tour = new Tour();

            for(int i = 0; i < tourSize; i++) {
                if(isRandom)
                    tour.addCity(new City(i, Configuration.instance.Random.nextDouble(),  Configuration.instance.Random.nextDouble()));
                else
                    if(k > 0)
                        tour.addCity(new City(i+1, cityXy[i],  cityXy[i+1]));
                    else
                        tour.addCity(new City(i, cityXy[9+i],  cityXy[10+i]));
            }
            p.addTourToPopulation(tour);
        }

        return p;
    }
}