package test.crossover;

import base.Tour;
import crossover.ICrossover;
import org.junit.Assert;
import org.junit.Test;
import test.TestTourHelper;

import java.util.List;

public class OrderedCrossover {

    /**
     * Tests if the order of city-ids is the not same after the crossover has been executed
     */
    @Test
    public void doCrossover() {
        ICrossover orderedCrossover = new crossover.OrderedCrossover();

        Tour tour01 = new Tour();
        tour01.setCities(HelperMethods.generateCities(10));
        Tour tour02 = new Tour();
        tour02.setCities(HelperMethods.generateCities(10));

        List<Integer> idsBefore = TestTourHelper.getTourCityIds(tour01);
        Tour newTour = orderedCrossover.doCrossover(tour01, tour02);
        List<Integer> idsAfter = TestTourHelper.getTourCityIds(newTour);

        Assert.assertNotEquals(idsAfter, idsBefore);
    }

}