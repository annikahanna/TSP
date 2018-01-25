package test.mutation;

import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.TestTourHelper;

import java.util.List;

public class ExchangeMutation {

    private TestTourHelper tourHelper;

    @Before
    public void SetupTest()
    {
        tourHelper = new TestTourHelper();
    }

    @Test
    public void TestInsertion()
    {
        Tour tour = tourHelper.getAscendingTour(10);
        List<Integer> cityIds = tourHelper.getTourCityIds(tour);

        IMutation exchangeMutation = new mutation.ExchangeMutation();
        exchangeMutation.doMutation(tour);

        List<Integer> mutatedCityIds = tourHelper.getTourCityIds(tour);

        Assert.assertNotEquals(cityIds, mutatedCityIds);
    }
}