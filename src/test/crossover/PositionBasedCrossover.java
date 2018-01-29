package test.crossover;

import base.City;
import base.Tour;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class PositionBasedCrossover {
    Tour parent1;
    Tour parent2;
    Tour expectedChild;
    private City city0;
    private City city1;
    private City city2;
    private City city3;
    private City city4;
    private City city5;
    private City city6;
    crossover.PositionBasedCrossover crosser;

    @Before
    public void init(){
        crosser = new crossover.PositionBasedCrossover();
        parent1 = new Tour();
        city0 = new City(0, 0.0, 0.0);
        city1 = new City(1, 10.0, 10.0);
        city2 = new City(2, 20.0, 20.0);
        city3 = new City(3, 30.0, 30.0);
        city4 = new City(4, 40.0, 40.0);
        city5 = new City(5, 50.0, 50.0);
        city6 = new City(6,60.0, 60.0);
        parent1.addCity(city1);
        parent1.addCity(city2);
        parent1.addCity(city3);
        parent1.addCity(city4);
        parent1.addCity(city5);

        parent2 = new Tour();
        parent2.addCity(city3);
        parent2.addCity(city2);
        parent2.addCity(city1);
        parent2.addCity(city4);
        parent2.addCity(city5);

        //not a real result...
        expectedChild = new Tour();
        expectedChild.addCity(city1);
        expectedChild.addCity(city2);
        expectedChild.addCity(city3);
        expectedChild.addCity(city4);
        expectedChild.addCity(city5);
    }

    @Test
    public void doCrossoverChildNotNull() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertNotNull(result);
    }

    @Test
    public void doCrossoverChildLength() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertEquals(5,result.getSize());
    }

    @Test
    public void doCrossoverCheckElement1Exists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertTrue(result.containsCity(city1));
    }

    @Test
    public void doCrossoverCheckElement2Exists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertTrue(result.containsCity(city2));
    }

    @Test
    public void doCrossoverCheckElement3Exists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertTrue(result.containsCity(city3));
    }

    @Test
    public void doCrossoverCheckElement4Exists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertTrue(result.containsCity(city4));
    }

    @Test
    public void doCrossoverCheckElement5Exists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertTrue(result.containsCity(city5));
    }


    @Test
    public void doCrossoverCheckElement0NotExists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertFalse(result.containsCity(city0));
    }

    @Test
    public void doCrossoverCheckElement6NotExists() {
        Tour result = crosser.doCrossover(parent1,parent2);
        assertFalse(result.containsCity(city6));
    }


    @Test
    public void doCrossoverCheckElements() {
        Tour result = crosser.doCrossover(parent1, parent2);
        assertTrue(expectedChild.getCities().containsAll(result.getCities()) && result.getCities().containsAll(expectedChild.getCities()));
    }

    @Test
    public void doCrossoverCheckForDuplicates() {
        Tour result = crosser.doCrossover(parent1, parent2);
        HashSet<City> tempResult = new HashSet<City>(result.getCities());
        assertEquals(5,tempResult.size());
    }
}