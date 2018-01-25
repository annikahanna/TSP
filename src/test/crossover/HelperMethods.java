package test.crossover;

import base.City;

import java.util.ArrayList;

public class HelperMethods {

    /**
     * helper method to create some cities
     *
     * @param count
     * @return
     */
    public static ArrayList<City> generateCities(int count) {
        ArrayList<City> rtn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rtn.add(new City(i, 1, 1));
        }
        return rtn;
    }
}
