package crossover;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.ArrayList;

public class CrossoverHelper {

    /**
     * Don´t use it!
     */
    private CrossoverHelper() {
    }

    protected static City getRandomCity(ArrayList<City> cities) {
        int randomIndex = Configuration.instance.Random.nextInt(0, cities.size() - 1);
        return cities.get(randomIndex);
    }

    protected static double getCityDistance(City c1, City c2) {
        return Tour.euclideanDistance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }

}
