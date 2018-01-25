package test.selection;

import org.junit.Assert;
import org.junit.Test;
import test.TestTourHelper;
import base.Population;
import base.Tour;
import java.util.ArrayList;
import main.Configuration;

public class TournamentSelection {

    @Test
    public void testChooseWinner(){

        System.out.println("Start: Test for chooseWinner...");

        selection.TournamentSelection tournamentTest = new selection.TournamentSelection();
        TestTourHelper th = new TestTourHelper();

        // Predetermined city values
        double[] cityXy = { 0.8508406745503213, 0.62015861142947,
                            0.3654690037829972, 0.45100442912763694,
                            0.6603852108802494, 0.4282687461714646,
                            0.4439261588527674, 0.5354219131524653,
                            0.26853096947312616, 0.12153728057149471,
                            0.19977584164129536, 0.29089480896264963,
                            0.9735214053401597, 0.005951016767797412,
                            0.004431732722091741, 0.2691923317627898,
                            0.6958280332056649, 0.09098436726373071,
                            0.5769226931269651, 0.04681994205305173};

        // Create population with given city coods
        Population p = th.getAscendingPopulation(2,10, false, cityXy);

        // Pick supposed winner
        ArrayList<Tour> supposedWinner = new ArrayList<>();
        supposedWinner.add(p.getSingleTour(1));

        int contender1 = 0;
        int contender2 = 1;

        double fitnessContender1 = p.getSingleTour(contender1).getFitness();
        double fitnessContender2 = p.getSingleTour(contender2).getFitness();

        // Check if fitness scores are calculated correctly
        Assert.assertEquals(5.962530321754594, fitnessContender1, Configuration.instance.ASSERT_DELTA);
        Assert.assertEquals(2.229896099026284, fitnessContender2, Configuration.instance.ASSERT_DELTA);

        ArrayList<Tour> winners = new ArrayList<>();
        tournamentTest.chooseWinner(p, winners, fitnessContender1, fitnessContender2, contender1, contender2);

        // Check if correct winner is chosen
        Assert.assertEquals(supposedWinner, winners);

        System.out.println("Stop: Test for chooseWinner successfully!");
    }

    @Test
    public void testDoSelection(){

        System.out.println("Start: Test for doSelection...");

        selection.TournamentSelection doSelectionTest = new selection.TournamentSelection();
        TestTourHelper th = new TestTourHelper();

        // Create random population
        Population p = th.getAscendingPopulation(100,280, true, null);

        doSelectionTest.doSelection(p);

        System.out.println("Stop: Test for doSelection successfully!");
    }
}