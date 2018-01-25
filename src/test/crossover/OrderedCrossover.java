package test.crossover;

import base.City;
import base.Tour;
import org.junit.Test;

import java.util.ArrayList;

public class OrderedCrossover {

    @Test
    public void doCrossover(Tour tour01, Tour tour02) {
    }

    /**
     * helper method to create some cities
     *
     * @param count
     * @return
     */
    private static ArrayList<City> generateCities(int count) {
        ArrayList<City> rtn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rtn.add(new City(i, 1, 1));
        }
        return rtn;
    }
}