package mutation;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.ArrayList;

public class InsertionMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        ArrayList<City> cities = tour.getCities();
        int selectedCityIndex = Configuration.instance.Random.nextInt(tour.getSize());

        City selectedCity = cities.get(selectedCityIndex);
        cities.remove(selectedCity);

        int insertAtIndex = Configuration.instance.Random.nextInt(tour.getSize());

        while (insertAtIndex == selectedCityIndex) {
            insertAtIndex = Configuration.instance.Random.nextInt(tour.getSize());
        }

        cities.add(insertAtIndex, selectedCity);
        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}