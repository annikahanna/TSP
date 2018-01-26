package test.mutation;

import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.TestTourHelper;

import java.util.List;

public class InsertionMutation {

    @Test
    public void TestInsertion()
    {
        Tour tour = TestTourHelper.getAscendingTour(10);
        List<Integer> cityIds = TestTourHelper.getTourCityIds(tour);

        IMutation insertionMutation = new mutation.InsertionMutation();
        insertionMutation.doMutation(tour);

        List<Integer> mutatedCityIds = TestTourHelper.getTourCityIds(tour);

        Assert.assertNotEquals(cityIds, mutatedCityIds);
    }
}