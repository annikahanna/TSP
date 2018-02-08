package crossover;

import base.City;
import base.Tour;

import java.util.ArrayList;
import java.util.List;

public class CycleCrossover extends AbstractCrossover {

    @Override
    public Tour doCrossover(Tour tour01, Tour tour02) {

        ArrayList<City> newTourArray = new ArrayList<City>();
        for (int i = 0; i < tour01.getSize(); i++)
            newTourArray.add(i, new City(-1, 0, 0));

        int index = 0;
        List<City> citiesLeft = new ArrayList<City>(tour02.getCities());
        int citiesLeftIndex = 0;
        while (newTourArray.get(index).getId() == -1) {
            newTourArray.set(index, tour01.getCity(index));
            citiesLeft.remove(tour01.getCity(index));
            City cityForNextIndex = tour02.getCities().get(index);
            index = tour01.getCities().indexOf(cityForNextIndex);
        }

        for (City city : newTourArray)
            if (city.getId() == -1)
                newTourArray.set(newTourArray.indexOf(city), citiesLeft.get(citiesLeftIndex++));

        Tour newTour = new Tour();
        newTour.setCities(newTourArray);
        return newTour;
    }

}