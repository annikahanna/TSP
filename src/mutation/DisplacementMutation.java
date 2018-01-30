package mutation;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.ArrayList;
import java.util.Collections;

public class DisplacementMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        ArrayList<City> cities = tour.getCities();
        int count = Configuration.instance.Random.nextInt(1, cities.size());
        int index = Configuration.instance.Random.nextInt(cities.size() - count + 1);

        ArrayList<City> selectedCities = new ArrayList<City>(cities.subList(index, index + count - 1));

        for(City city : selectedCities) {
            cities.remove(city);
        }

        int targetIndex = Configuration.instance.Random.nextInt(cities.size());

        if(targetIndex == index)
            targetIndex = Configuration.instance.Random.nextInt(cities.size());

        Collections.reverse(selectedCities);

        for(City city : selectedCities) {
            cities.add(targetIndex, city);
        }

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}