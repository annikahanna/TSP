package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import org.junit.Test;
import test.TestTourHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class OrderedCrossover {

    /**
     * Tests if the order of city-ids is the not same after the crossover has been executed
     */
    @Test
    public void doCrossover() {
        ICrossover orderedCrossover = new crossover.OrderedCrossover();

        Tour tour01 = new Tour();
        Tour tour02 = new Tour();

        ArrayList<City> randomizedCities1 = (ArrayList<City>) TestTourHelper.generateRandomCities(280, true);
        ArrayList<City> randomizedCities2 = (ArrayList<City>) TestTourHelper.randomizeCities(randomizedCities1);
        tour01.setCities(randomizedCities1);
        tour02.setCities(randomizedCities2);

        List<Integer> idsBefore = TestTourHelper.getTourCityIds(tour01);
        Tour newTour = orderedCrossover.doCrossover(tour01, tour02);
        List<Integer> idsAfter = TestTourHelper.getTourCityIds(newTour);
        
        assertNotEquals(idsAfter, idsBefore);
    }

}