package test.crossover;

import base.City;
import base.Tour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CycleCrossover {
    @Test
    public void testDoCrossover(){

        City[] cities = new City[9];
        for(int i=0;i<9;i++)
        {
            cities[i] = new City(i+1,1,1);
        }

        ArrayList<City> tourList01 = new ArrayList<>();
        tourList01.add(cities[1-1]);
        tourList01.add(cities[2-1]);
        tourList01.add(cities[3-1]);
        tourList01.add(cities[4-1]);
        tourList01.add(cities[5-1]);
        tourList01.add(cities[6-1]);
        tourList01.add(cities[7-1]);
        tourList01.add(cities[8-1]);
        tourList01.add(cities[9-1]);

        ArrayList<City> tourList02 = new ArrayList<>();
        tourList02.add(cities[4-1]);
        tourList02.add(cities[1-1]);
        tourList02.add(cities[2-1]);
        tourList02.add(cities[8-1]);
        tourList02.add(cities[7-1]);
        tourList02.add(cities[6-1]);
        tourList02.add(cities[9-1]);
        tourList02.add(cities[3-1]);
        tourList02.add(cities[5-1]);

        ArrayList<City> tourListRes = new ArrayList<>();
        tourListRes.add(cities[1-1]);
        tourListRes.add(cities[2-1]);
        tourListRes.add(cities[3-1]);
        tourListRes.add(cities[4-1]);
        tourListRes.add(cities[7-1]);
        tourListRes.add(cities[6-1]);
        tourListRes.add(cities[9-1]);
        tourListRes.add(cities[8-1]);
        tourListRes.add(cities[5-1]);

        Tour tour01 = new Tour();
        tour01.setCities(tourList01);

        Tour tour02 = new Tour();
        tour02.setCities(tourList02);

        Tour tourRes = new Tour();
        tourRes.setCities(tourListRes);

        crossover.CycleCrossover cc = new crossover.CycleCrossover();

        Assert.assertEquals(tourRes, cc.doCrossover(tour01, tour02));
    }
}