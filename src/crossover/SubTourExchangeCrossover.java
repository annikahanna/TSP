package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class SubTourExchangeCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {

        ArrayList<City> tour01Cities = tour01.getCities();
        ArrayList<City> tour02Cities = tour02.getCities();
        Tour childTour01 = tour01;
        Tour childTour02 = tour02;
        MersenneTwisterFast randomizer = new MersenneTwisterFast();

        //set random search-width, but never the same size as the parent widths
        int width = randomizer.nextInt(tour01Cities.size() - 1);

        //set random indexes for both ArrayLists (attention to the right bound because of the width of the subtours!)
        int index01 = randomizer.nextInt(tour01Cities.size() - width);
        int index02 = randomizer.nextInt(tour01Cities.size() - width);

        //repeat until there is a match or a forced child
        do {
            //iterate through the tour01Cities...
            do {
                //... and search for a match in the tour02Cities
                do {
                    //check, if there are the same elements in both subtours
                    boolean hasNumber = true;
                    for (int i = width - 1; (hasNumber) && (i >= 0); i--) {
                        hasNumber = false;
                        for (int j = width - 1; j >= 0; j--) {
                            if (tour01Cities.get(index01 + i).equals(tour02Cities.get(index02 + j))) hasNumber = true;
                        }
                    }

                    //if match, crossover the matches --> 2 new children
                    if (hasNumber) {
                        for (int i = width - 1; i >= 0; i--) {
                            childTour01.addCity(index01 + i, tour02Cities.get(index02 + i));
                            childTour02.addCity(index02 + i, tour01Cities.get(index01 + i));
                        }

                        //let the children fight! survival/return of the fittest child!
                        if (childTour01.compareTo(childTour02) <= 0) return childTour02;
                        else return childTour01;
                    }

                    //new index has to be in bounds and starts after the max-index from the min-index 0 (loop)
                    index02 = (index02 + 1) % (tour02Cities.size() - width);
                }
                while ((0 < index02) && (index02 < (tour02Cities.size() - width))) ;

                //new index has to be in bounds and starts after the max-index from the min-index 0 (loop)
                index01 = (index01 + 1) % (tour01Cities.size() - width);
            }
            while ((0 < index01) && (index01 < (tour01Cities.size() - width))) ;

            //if no-match, reduce the width
            width--;
        } while (width >= 2);

        //if width was already 2 (or less), then the fittest parent survives and gets returned!
        if (tour01.compareTo(tour02) <= 0) {
            //!!!DELETE THE tour01, since it is weaker!
            return tour02;
        } else {
            //!!!DELETE THE tour02, since it is weaker!
            return tour01;
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}