package test.mutation;

import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Test;
import test.TestTourHelper;

import java.util.List;

public class InversionMutation {

    @Test
    public void TestInversion()
    {
        Tour tour = TestTourHelper.getAscendingTour(10);
        List<Integer> cityIds = TestTourHelper.getTourCityIds(tour);
        IMutation inversionMutation = new mutation.InversionMutation();

        Tour mutatedTour = inversionMutation.doMutation(tour);

        List<Integer> mutatedCityIds = TestTourHelper.getTourCityIds(mutatedTour);

        Assert.assertNotEquals(cityIds, mutatedCityIds);
    }
}