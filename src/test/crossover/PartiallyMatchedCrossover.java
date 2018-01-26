package test.crossover;

import base.City;
import base.Tour;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class PartiallyMatchedCrossover {

    private Tour parent1;
    private Tour parent2;
    private crossover.PartiallyMatchedCrossover crosser;
    private City city1;
    private City city2;
    private City city3;

    @Before
    public void init() {
        crosser = new crossover.PartiallyMatchedCrossover();
        parent1 = new Tour();
        parent1.addCity(city1 = new City(1, 10.0, 10.0));
        parent1.addCity(city2 = new City(2, 20.0, 20.0));
        parent1.addCity(city3 = new City(3, 30.0, 30.0));

        parent2 = new Tour();
        parent2.addCity(city3);
        parent2.addCity(city2);
        parent2.addCity(city1);
    }

    @Test
    public void doCrossoverChildNotNull() {
        Tour child = crosser.doCrossover(parent1, parent2);
        assertNotNull(child);
    }

    /*Dieser Test prüft, ob das zurückgegebene Kind genau dem expectedChild entspricht, was bei n>3 extrem unwahrscheinlich ist.
    * Kann aber für Debuggingfälle mit 3 Elementen behalten werden*/
    /*    @Test
    public void partiallyMatchedTest() {
        assertEquals(Arrays.toString(expectedChild.getCities().toArray()),Arrays.toString(crosser.doCrossover(parent1,parent2).getCities().toArray()));
    }*/

    @Test
    public void doCrossoverChildLength() {
        Tour child = crosser.doCrossover(parent1, parent2);
        assertEquals(3, child.getCities().size());
    }

    @Test
    public void doCrossoverCheckElement4Exists() {
        Tour result = crosser.doCrossover(parent1, parent2);
        assertTrue(result.getCities().containsAll(parent1.getCities()));
    }

    @Test
    public void doCrossoverCheckAllParentElementsAreInChild() {
        Tour result = crosser.doCrossover(parent1, parent2);

        ArrayList<Integer> cityIDsResult = new ArrayList<>();
        ArrayList<Integer> cityIDsParent1 = new ArrayList<>();

        for (int i = 0; i < result.getSize(); i++) {
            cityIDsResult.add(result.getCities().get(i).getId());
        }
        for (int i = 0; i < parent1.getSize(); i++) {
            cityIDsParent1.add(parent1.getCities().get(i).getId());
        }

        /*System.out.println(Arrays.toString(cityIDsParent1.toArray()));
        System.out.println(Arrays.toString(cityIDsResult.toArray()));*/

        assertTrue(cityIDsResult.containsAll(cityIDsParent1));
    }

    @Test
    public void doCrossoverCheckElement290NotExists() {
        Tour result = crosser.doCrossover(parent1, parent2);

        ArrayList<Integer> cityIDsResult = new ArrayList<>();

        for (int i = 0; i < result.getSize(); i++) {
            cityIDsResult.add(parent1.getCities().get(i).getId());
        }
        assertFalse(cityIDsResult.contains(290));
    }

    @Test
    public void doCrossoverCheckForDuplicates() {
        int routeLength = parent1.getCities().size();
        Tour child = crosser.doCrossover(parent1, parent2);
        /*System.out.println(Arrays.toString(parent1.getCities().toArray()));
        System.out.println(Arrays.toString(child.getCities().toArray()));*/
        HashSet<City> allChildCities = new HashSet<>(child.getCities());
        assertEquals(routeLength, allChildCities.size());
    }


}