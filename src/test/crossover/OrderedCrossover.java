package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import org.junit.Before;
import org.junit.Test;
import test.TestTourHelper;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class OrderedCrossover {

    private Tour tour01;
    private Tour tour02;

    private Tour childTour;

    @Before
    public void doCrossover() {
        ICrossover orderedCrossover = new crossover.OrderedCrossover();

        tour01 = new Tour();
        tour02 = new Tour();

        ArrayList<City> randomizedCities1 = (ArrayList<City>) TestTourHelper.generateRandomCities(280, true);
        ArrayList<City> randomizedCities2 = (ArrayList<City>) TestTourHelper.randomizeCities(randomizedCities1);
        tour01.setCities(randomizedCities1);
        tour02.setCities(randomizedCities2);

        childTour = orderedCrossover.doCrossover(tour01, tour02);
    }

    @Test
    public void doCrossoverChildNotNull() {
        assertNotNull(childTour);
    }

    @Test
    public void doCrossoverChildLength() {
        assertEquals(tour01.getSize(), childTour.getSize());
    }

    @Test
    public void doCrossoverCheckElement4Exists() {
        assertTrue(childTour.getCities().get(4) != null);
    }

    @Test
    public void doCrossoverCheckForDuplicates() {
        HashSet<City> tempResult = new HashSet<>(childTour.getCities());
        assertEquals(tour01.getSize(), tempResult.size());
    }

    @Test
    public void doCrossoverCheckElements() {
        assertTrue(childTour.getCities().containsAll(childTour.getCities()) && childTour.getCities().containsAll(tour01.getCities()));
    }

}