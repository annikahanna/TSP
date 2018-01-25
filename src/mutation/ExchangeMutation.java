package mutation;

import base.Tour;
import main.Configuration;

import java.util.Collections;

public class ExchangeMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        int swapIndex1 = Configuration.instance.Random.nextInt(tour.getSize());
        int swapIndex2 = Configuration.instance.Random.nextInt(tour.getSize());

        while(swapIndex1 == swapIndex2) {
            swapIndex2 = Configuration.instance.Random.nextInt(tour.getSize());
        }

        Collections.swap(tour.getCities(), swapIndex1, swapIndex2);
        return null;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}