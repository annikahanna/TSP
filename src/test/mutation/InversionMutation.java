package test.mutation;

import base.City;
import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.TestTourHelper;

import java.util.ArrayList;
import java.util.List;

public class InversionMutation {

    private TestTourHelper tourHelper;

    @Before
    public void SetupTest()
    {
        tourHelper = new TestTourHelper();
    }

    @Test
    public void TestInversion()
    {
        Tour tour = tourHelper.getAscendingTour(10);
        List<Integer> cityIds = tourHelper.getTourCityIds(tour);
        IMutation inversionMutation = new mutation.InversionMutation();

        Tour mutatedTour = inversionMutation.doMutation(tour);

        List<Integer> mutatedCityIds = tourHelper.getTourCityIds(mutatedTour);

        Assert.assertNotEquals(cityIds, mutatedCityIds);
    }
}