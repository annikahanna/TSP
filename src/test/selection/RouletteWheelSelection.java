package test.selection;

import org.junit.Test;
import test.TestTourHelper;
import base.Population;
import base.Tour;
import java.util.ArrayList;

public class RouletteWheelSelection {

    @Test
    public void testRoulette(){

        selection.RouletteWheelSelection t = new selection.RouletteWheelSelection();
        TestTourHelper th = new TestTourHelper();

        Population p = th.getAscendingPopulation(100, 280, true, null);

        ArrayList<Tour> tt = t.doSelection(p);

        System.out.println(tt);

    }
}