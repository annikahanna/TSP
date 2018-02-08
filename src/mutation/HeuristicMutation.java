package mutation;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HeuristicMutation implements IMutation {

    class CityIndex
    {
        int index;
        City city;

        public CityIndex(int index, City city)
        {
            this.index = index;
            this.city = city;
        }
    }

    private ArrayList<CityIndex> randomCities = new ArrayList<CityIndex>();
    private ArrayList<Integer> integers = new ArrayList<Integer>();
    private ArrayList<City> parent;

    public Tour doMutation(Tour tour)
    {
        randomCities.clear();
        integers.clear();

        parent = (ArrayList<City>)tour.getCities().clone();

        int max = 8;

        if(tour.getSize() < 8)
            max = tour.getSize();

        int randomCount = Configuration.instance.Random.nextInt(2, max);

        for(int i = 0; i < randomCount; i++)
        {
            int randomCityId = Configuration.instance.Random.nextInt(tour.getSize());

            while(containsCityIndex(tour.getCity(randomCityId).getId())) {
                randomCityId = Configuration.instance.Random.nextInt(tour.getSize());
            }

            integers.add(randomCityId);
            randomCities.add(new CityIndex(randomCityId, tour.getCity(randomCityId)));
        }

        permute(randomCities, 0);
        tour.setCities(highest);
        return tour;
    }

    private boolean containsCityIndex(int cityId)
    {
        for(CityIndex index : randomCities) {
            if(index.index == cityId)
                return true;
        }

        return false;
    }

    private ArrayList<City> highest;

    private void permute(ArrayList<CityIndex> cities, int k) {
        for(int i = 0; i < integers.size(); i++) {
            parent.set(integers.get(i), cities.get(i).city);

            if(highest == null)
                continue;

            Tour tour = new Tour();
            tour.setCities(parent);

            Tour highestTour = new Tour();
            highestTour.setCities(highest);

            if(tour.getFitness() > highestTour.getFitness())
                highest = tour.getCities();
        }

        for (int i = k; i < cities.size(); i++) {
            Collections.swap(cities, i, k);

            if(highest == null)
                highest = (ArrayList<City>)parent.clone();

            permute(cities, k + 1);
            Collections.swap(cities, k, i);
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}