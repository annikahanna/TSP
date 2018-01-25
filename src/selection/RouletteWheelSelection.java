package selection;

import java.util.ArrayList;

import base.Population;
import base.Tour;
import main.Configuration;;

public class RouletteWheelSelection implements ISelection {

    public ArrayList<Tour> doSelection(Population population) {

        double populationFitness=0;
        for (int i=0; i < population.getSize(); i++)
        {
            populationFitness= populationFitness + population.getSingleTour(i).getFitness();
        }

        double[] rouletteWheel ;
        rouletteWheel = new double[population.getSize()];
        rouletteWheel[0] = Math.round((population.getSingleTour(0).getFitness() / populationFitness) *1000.0)/1000.0;

        for (int i=1 ; i < rouletteWheel.length ; i++)
        {
            rouletteWheel[i] = Math.round((population.getSingleTour(i).getFitness() / populationFitness) *1000.0)/1000.0 + rouletteWheel[i-1];
        }

        ArrayList<Tour> selectedTours;
        selectedTours = new ArrayList<>() ;

        int c = 1;
        while (c<=Configuration.instance.ROULETTE_COUNT) {

            for (int i = 0; i < rouletteWheel.length; i++) {
                double r = Math.round(Configuration.instance.Random.nextDouble(0.000, 1.000) * 1000.0) / 1000.0;
                if (r <= rouletteWheel[i]) {
                    Tour a = population.getSingleTour(i);
                    if (!alreadySelected(selectedTours, a)) {
                        selectedTours.add(a);
                        c++;
                        System.out.println(c);
                    } else
                        break;
                }
            }
        }
        return selectedTours;
    }

    private boolean alreadySelected(ArrayList<Tour> selectedTours, Tour newTour)
    {
        for(int i=0; i<selectedTours.size(); i++)
        {
            Tour a = selectedTours.get(i);
            if( newTour.compareTo(a) == 0)
            {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}