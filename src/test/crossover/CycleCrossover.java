package test.crossover;

import base.City;
import base.Tour;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import test.TestTourHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CycleCrossover {

    private crossover.CycleCrossover cc;

    private ArrayList<City> cities;

    private Tour testTour01;
    private Tour testTour02;

    @Before
    public void init() {
        cc = new crossover.CycleCrossover();
        testTour01 = new Tour();
        testTour02 = new Tour();

        cities = (ArrayList<City>) TestTourHelper.generateRandomCities(10, false);

        ArrayList<City> cities01 = (ArrayList<City>) TestTourHelper.randomizeCities(cities);
        ArrayList<City> cities02 = (ArrayList<City>) TestTourHelper.randomizeCities(cities01);

        testTour01.setCities(cities01);
        testTour02.setCities(cities02);
    }

    @Test
    public void testDoCrossoverSpecificResult() {

        ArrayList<City> tourList01 = new ArrayList<>();
        tourList01.add(cities.get(1));
        tourList01.add(cities.get(2));
        tourList01.add(cities.get(3));
        tourList01.add(cities.get(4));
        tourList01.add(cities.get(5));
        tourList01.add(cities.get(6));
        tourList01.add(cities.get(7));
        tourList01.add(cities.get(8));
        tourList01.add(cities.get(0));

        ArrayList<City> tourList02 = new ArrayList<>();
        tourList02.add(cities.get(4));
        tourList02.add(cities.get(1));
        tourList02.add(cities.get(2));
        tourList02.add(cities.get(8));
        tourList02.add(cities.get(7));
        tourList02.add(cities.get(6));
        tourList02.add(cities.get(0));
        tourList02.add(cities.get(3));
        tourList02.add(cities.get(5));

        ArrayList<City> tourListRes = new ArrayList<>();
        tourListRes.add(cities.get(1));
        tourListRes.add(cities.get(2));
        tourListRes.add(cities.get(3));
        tourListRes.add(cities.get(4));
        tourListRes.add(cities.get(7));
        tourListRes.add(cities.get(6));
        tourListRes.add(cities.get(0));
        tourListRes.add(cities.get(8));
        tourListRes.add(cities.get(5));

        Tour tour01 = new Tour();
        Tour tour02 = new Tour();
        Tour tourRes = new Tour();

        tour01.setCities(tourList01);
        tour02.setCities(tourList02);
        tourRes.setCities(tourListRes);

        List<Integer> idsNeeded = TestTourHelper.getTourCityIds(tourRes);

        List<Integer> idsResult = TestTourHelper.getTourCityIds(cc.doCrossover(tour01, tour02));

        assertEquals(idsNeeded, idsResult);
    }

    @Test
    public void doCrossoverChildNotNull() {
        Tour result = cc.doCrossover(testTour01, testTour02);
        assertNotNull(result);
    }

    @Test
    public void doCrossoverChildLength() {
        Tour result = cc.doCrossover(testTour01, testTour02);
        assertEquals(10, result.getCities().size());
    }

    @Test
    public void doCrossoverCheckElement4Exists() {
        Tour result = cc.doCrossover(testTour01, testTour02);
        assertTrue(result.getCities().contains(testTour01.getCity(4)));
    }

    @Test
    public void doCrossoverCheckElement10NotExists() {
        Tour result = cc.doCrossover(testTour01, testTour02);
        assertFalse(result.getCities().contains(20));
    }

    @Test
    public void doCrossoverCheckElements() {
        Tour expected = new Tour();
        expected.setCities(cities);
        Tour result = cc.doCrossover(testTour01, testTour02);
        assertTrue(expected.getCities().containsAll(result.getCities()) &&
                result.getCities().containsAll(expected.getCities()));
    }

    @Test
    public void doCrossoverCheckForDuplicates() {
        Tour result = cc.doCrossover(testTour01, testTour02);
        HashSet<City> tempResult = new HashSet<>(result.getCities());
        assertEquals(10, tempResult.size());
    }

}