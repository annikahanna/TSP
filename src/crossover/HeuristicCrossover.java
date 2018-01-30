package crossover;

import base.City;
import base.Tour;

import java.util.ArrayList;
import java.util.TreeMap;

public class HeuristicCrossover extends AbstractCrossover {

    @Override
    public Tour doCrossover(Tour tour01, Tour tour02) {

        City startCity = CrossoverHelper.getRandomCity(tour01.getCities());
        Tour childTour = new Tour();
        ArrayList<City> citiesLeft = new ArrayList<City>(tour01.getCities());
        citiesLeft.remove(startCity);
        childTour.addCity(startCity);

        while (!citiesLeft.isEmpty()) {
            City momCity = childTour.getCity(childTour.getSize() - 1);

            int index = tour01.getCities().indexOf(momCity);
            City leftNeighbour1 = tour01.getCity((index - 1) < 0 ? tour01.getSize() - 1 : index - 1);
            City rightNeighbour1 = tour01.getCity((index + 1) > tour01.getSize() - 1 ? 0 : index + 1);

            index = tour02.getCities().indexOf(momCity);
            City leftNeighbour2 = tour02.getCity((index - 1) < 0 ? tour02.getSize() - 1 : index - 1);
            City rightNeighbour2 = tour02.getCity((index + 1) > tour02.getSize() - 1 ? 0 : index + 1);

            TreeMap<Double, City> map = new TreeMap<Double, City>();
            map.put(CrossoverHelper.getCityDistance(momCity, leftNeighbour1), leftNeighbour1);
            map.put(CrossoverHelper.getCityDistance(momCity, rightNeighbour1), rightNeighbour1);
            map.put(CrossoverHelper.getCityDistance(momCity, leftNeighbour2), leftNeighbour2);
            map.put(CrossoverHelper.getCityDistance(momCity, rightNeighbour2), rightNeighbour2);

            //pull 1st Entry
            City neighbour = map.pollFirstEntry().getValue();

            if (!childTour.containsCity(neighbour)) {
                childTour.addCity(neighbour);
                citiesLeft.remove(neighbour);
            } else {
                //poll 2nd Entry
                neighbour = map.pollFirstEntry().getValue();
                if (!childTour.containsCity(neighbour)) {
                    childTour.addCity(neighbour);
                    citiesLeft.remove(neighbour);
                } else {
                    City randomCity = CrossoverHelper.getRandomCity(citiesLeft);
                    childTour.addCity(randomCity);
                    citiesLeft.remove(randomCity);
                }
            }
        }
        return childTour;
    }

}