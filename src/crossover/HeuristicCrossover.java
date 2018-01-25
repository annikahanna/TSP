package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.TreeMap;

public class HeuristicCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        //step 1

        City startCity = getRandomCity(tour01.getCities());

        //step 2
        Tour childTour = new Tour();
        ArrayList<City> citiesLeft = new ArrayList<>(tour01.getCities());
        citiesLeft.remove(startCity);
        childTour.addCity(startCity);

        while(!citiesLeft.isEmpty())
        {
            City momCity = childTour.getCity(childTour.getSize()-1);

            int index = tour01.getCities().indexOf(momCity);
            City leftNeighbour1 = tour01.getCity((index-1) < 0 ? tour01.getSize()-1 : index-1);
            City rightNeighbour1 = tour01.getCity((index+1)> tour01.getSize()-1?0:index+1);

            index = tour02.getCities().indexOf(momCity);
            City leftNeighbour2 = tour01.getCity((index-1) < 0 ? tour01.getSize()-1 : index-1);
            City rightNeighbour2 = tour01.getCity((index+1)> tour01.getSize()-1?0:index+1);

            TreeMap<Double, City> map = new TreeMap<>();
            map.put(getCityDistance(momCity, leftNeighbour1),leftNeighbour1);
            map.put(getCityDistance(momCity, rightNeighbour1),rightNeighbour1);
            map.put(getCityDistance(momCity, leftNeighbour2),leftNeighbour2);
            map.put(getCityDistance(momCity, rightNeighbour2),rightNeighbour2);

            City nearestNeighbour = map.pollFirstEntry().getValue();

            if(!childTour.containsCity(nearestNeighbour))
            {
                childTour.addCity(nearestNeighbour);
                citiesLeft.remove(nearestNeighbour);
            }
            else
            {
                nearestNeighbour = map.pollFirstEntry().getValue();
                if(!childTour.containsCity(nearestNeighbour)) {
                    childTour.addCity(nearestNeighbour);
                    citiesLeft.remove(nearestNeighbour);
                }
                else
                {
                    City randomCity = getRandomCity(citiesLeft);
                    childTour.addCity(randomCity);
                    citiesLeft.remove(randomCity);
                }
            }
        }
        return childTour;
    }

    private City getRandomCity(ArrayList<City> cities)
    {
        //TODO Singelton random
        MersenneTwisterFast mtf = new MersenneTwisterFast();
        int randomIndex = mtf.nextInt(0, cities.size()-1);
        return cities.get(randomIndex);
    }

    private static double getCityDistance(City c1, City c2)
    {
        return Tour.euclideanDistance(c1.getX(),c1.getY(),c2.getX(),c2.getY());
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}