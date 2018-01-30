package crossover;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class SubTourExchangeCrossover extends AbstractCrossover {

    public Tour doCrossover(Tour tour01, Tour tour02) {

        ArrayList<City> tour01Cities = new ArrayList<City>(tour01.getCities());
        ArrayList<City> tour02Cities = new ArrayList<City>(tour02.getCities());
        Tour childTour01 = new Tour();
        childTour01.setCities(tour01Cities);
        Tour childTour02 = new Tour();
        childTour02.setCities(tour02Cities);
        MersenneTwisterFast randomizer = Configuration.instance.Random;

        //set random search-width, but never the same size as the parent widths and minimum width 2
        int width = randomizer.nextInt(tour01Cities.size() - 2) + 2;

        //set random indexes for both ArrayLists (attention to the right bound because of the width of the subtours!)
        int index01 = randomizer.nextInt(tour01Cities.size() - width);
        int index02 = randomizer.nextInt(tour01Cities.size() - width);
        int index01original = index01;
        int index02original = index02;

        //repeat until there is a match or a forced child
        do {
            //iterate through the tour01Cities...
            do {
                //... and search for a match in the tour02Cities
                do {
                    ArrayList<City> subtour01 = new ArrayList<City>();
                    ArrayList<City> subtour02 = new ArrayList<City>();
                    for (int i = width - 1; i >= 0; i--) {
                        subtour01.add(tour01Cities.get(i + index01));
                        subtour02.add(tour02Cities.get(i + index02));
                    }

                    boolean hasNumber = subtour01.containsAll(subtour02);

                    //if match, crossover the matches --> 2 new children
                    if (hasNumber) {
                        for (int i = width - 1; i >= 0; i--) {
                            childTour01.addCity(index01 + i, tour02.getCities().get(index02 + i));
                            childTour02.addCity(index02 + i, tour01.getCities().get(index01 + i));
                        }

                        //let the children fight! survival/return of the fittest child!
                        boolean comp = childTour01.compareTo(childTour02) > 0;
                        if (comp) return childTour02;
                        else return childTour01;
                    }

                    //new index has to be in bounds and starts after the max-index from the min-index 0 (loop)
                    index02 = (index02 + 1) % (tour02Cities.size() - width + 1);
                }
                while (index02original != index02);

                //new index has to be in bounds and starts after the max-index from the min-index 0 (loop)
                index01 = (index01 + 1) % (tour01Cities.size() - width + 1);
            }
            while (index01original != index01);

            //if no-match, reduce the width
            width--;
        } while (width >= 2);

        //if width was already 2 (or less), then the fittest parent survives and gets returned!
        if (tour01.compareTo(tour02) <= 0) {
            tour01 = null;
            return tour02;
        } else {
            tour02 = null;
            return tour01;
        }
    }

}