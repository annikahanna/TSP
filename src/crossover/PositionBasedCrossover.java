package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class PositionBasedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {

        ArrayList<City> tour01Cities = tour01.getCities();
        ArrayList<City> tour02Cities = tour02.getCities();
        Tour childTour = new Tour();
        MersenneTwisterFast randomizer = new MersenneTwisterFast();
        City noRealCity = new City(-1, -1, -1);

        //initialize cities of childTour with '-1'
        for (City city : tour01Cities) {
            childTour.addCity(noRealCity);
        }

        for (int i = 0; i<tour01Cities.size(); i++) {
            //marks city to be inherited from the first parent (tour01) to childTour (and not to be used from the second parent (tour02))
            boolean select = randomizer.nextBoolean();
            if (select) {
                //add the selected city to the childTour in the same position
                childTour.addCity(i, tour01Cities.get(i));
            }
        }

        //delete all selected cities from the second parent
        tour02Cities.removeAll(childTour.getCities());

        //marks the city which is used as replacement for the gaps of childTour
        int tour2CitiesIndex = 0;
        //add the missing cities from tour02 to childTour
        for (int i = 0; i<tour01Cities.size(); i++) {
            //if there isn't a "real" city at the specified index
            if (childTour.getCity(i).equals(noRealCity)) {
                //add the city from the retained tour02Cities
                childTour.addCity(i, tour02Cities.get(tour2CitiesIndex));
                tour2CitiesIndex++;
            }
        }

        return childTour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}