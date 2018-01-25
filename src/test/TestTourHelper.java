package test;

import base.City;
import base.Tour;

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
}