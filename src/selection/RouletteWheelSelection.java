package selection;

import java.util.ArrayList;
import base.Population;
import base.Tour;
import main.Configuration;;

public class RouletteWheelSelection implements ISelection {
    /**
     * Implementation for roulette wheel selection
     * @param population Given population
     * @return ArrayList of selected Tours
     */

    public ArrayList<Tour> doSelection(Population population) {

        double populationFitness = calculatePopulationFitness(population);

        double[] rouletteWheel = fillRouletteWheel(population, populationFitness) ;

        ArrayList<Tour> selectedTours = performSelection(population, rouletteWheel);

        return selectedTours;
    }

    private double calculatePopulationFitness(Population population)
    {
        double populationFitness = 0;
        for (int i=0; i < population.getSize(); i++)
            populationFitness = populationFitness + population.getSingleTour(i).getFitness();
        return populationFitness;
    }

    private double[] fillRouletteWheel(Population population, double populationFitness)
    {
        double[] rouletteWheel;
        rouletteWheel = new double[population.getSize()];
        rouletteWheel[0] = Math.round(((population.getSingleTour(0).getFitness() / populationFitness) - 0.001) * 1000.0) / 1000.0;
        for (int i = 1; i < rouletteWheel.length - 1; i++) {
            rouletteWheel[i] = Math.round((rouletteWheel[i - 1] + ((population.getSingleTour(i).getFitness() / populationFitness - 0.001))) * 1000.0) / 1000.0;
        }
        rouletteWheel[rouletteWheel.length - 1] = 1.0;
        return rouletteWheel;
    }

    private ArrayList<Tour> performSelection(Population population, double[] rouletteWheel)
    {
        ArrayList<Tour> selectedTours;
        selectedTours = new ArrayList<>();

        int c = 1, i = 0;
        double r = randomDouble();
        while (c <= Configuration.instance.ROULETTE_COUNT) {
            if (r <= rouletteWheel[i]) {
                Tour a = population.getSingleTour(i);
                if (!alreadySelected(selectedTours, a)) {
                    selectedTours.add(a);
                    c++;
                    i = 0;
                    r = randomDouble();
                }
                else {
                    i = 0;
                    r = randomDouble();
                }
            } else if (i < rouletteWheel.length) {
                i++;
            } else {
                i = 0;
                r = randomDouble();
            }

        }
        return selectedTours;
    }

    private double randomDouble()
    {
        return Math.round(Configuration.instance.Random.nextDouble() * 1000.0) / 1000.0;
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