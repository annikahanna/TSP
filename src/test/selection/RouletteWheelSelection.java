package test.selection;

import static org.junit.Assert.*;
import org.junit.Test;
import test.TestTourHelper;
import base.Population;
import base.Tour;
import java.util.ArrayList;
import main.Configuration;

public class RouletteWheelSelection {

    @Test
    public void testRouletteRunthrough() {
        //Test for the complete selection
        System.out.println("Start: Test if the selection runs without errors...");
        selection.RouletteWheelSelection t = new selection.RouletteWheelSelection();
        TestTourHelper th = new TestTourHelper();

        Population p = th.getAscendingPopulation(100, 280, true, null);

        ArrayList<Tour> tt = t.doSelection(p);
        System.out.println("Stop: The selection works without errors!");
    }

    @Test
    public void testCalculatePopulationFitness() {
        // Test for the calculation of the population fitness
        System.out.println("Start: Test for method calculatePopulationFitness...");

        selection.RouletteWheelSelection calculateTest = new selection.RouletteWheelSelection();
        TestTourHelper th = new TestTourHelper();

        // Create population with random city coods
        Population p = th.getAscendingPopulation(5, 10, true, null);
        // Store individual fitness of all Tours in Population in Array
        double[] individualFitness = new double[5];
        for (int i = 0; i < 5; i++)
            individualFitness[i] = p.getSingleTour(i).getFitness();
        double cumulatedFitness = individualFitness[0] + individualFitness[1] + individualFitness[2] + individualFitness[3] + individualFitness[4];
        double calculatedFitness = calculateTest.calculatePopulationFitness(p);
        assertEquals(cumulatedFitness, calculatedFitness,Configuration.instance.ASSERT_DELTA);
        System.out.println("Stop: Test for calculatePopulationFitness successfully!");
    }

    @Test
    public void testAlreadySelected() {
        // Test for the check if a Tour has already been selected
        System.out.println("Start: Test for method alreadySelected...");

        selection.RouletteWheelSelection rws = new selection.RouletteWheelSelection();
        TestTourHelper th = new TestTourHelper();

        // Create 2 populations with random city coods
        Population p1 = th.getAscendingPopulation(5, 10, true, null);
        Population p2 = th.getAscendingPopulation(1, 10, true, null);
        // Start alreadySelected with p1 as ArrayList and one tour from p1 as newTour
        boolean test1 = rws.alreadySelected(p1.getPopulation(), p1.getSingleTour(3));
        // start alreadySelected with p1 as ArrayList and a new Tour not included in p1
        boolean test2 = rws.alreadySelected(p1.getPopulation(), p2.getSingleTour(0));
        assertEquals(true, test1);
        assertEquals(false, test2);
        System.out.println("Stop: Test for alreadySelected successfully!");
    }
}