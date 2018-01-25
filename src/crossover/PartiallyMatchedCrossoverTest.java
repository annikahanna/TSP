package crossover;

import base.City;
import base.Tour;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PartiallyMatchedCrossoverTest {

    @Test
    public void partiallyMatchedTest() {
        Tour parent1 = new Tour();
        parent1.addCity(new City(1, 10.0, 10.0));
        parent1.addCity(new City(2, 20.0, 20.0));
        parent1.addCity(new City(3, 30.0, 30.0));

        Tour parent2 = new Tour();
        parent2.addCity(new City(3, 30.0, 30.0));
        parent2.addCity(new City(2, 20.0, 20.0));
        parent2.addCity(new City(1, 10.0, 10.0));

        Tour expectedChild = new Tour();
        expectedChild.addCity(new City(1, 10.0, 10.0));
        expectedChild.addCity(new City(2, 20.0, 20.0));
        expectedChild.addCity(new City(3, 30.0, 30.0));

        PartiallyMatchedCrossover abcabc = new PartiallyMatchedCrossover();

        assertEquals(Arrays.toString(expectedChild.getCities().toArray()),Arrays.toString(abcabc.doCrossover(parent1,parent2).getCities().toArray()));
    }

}
