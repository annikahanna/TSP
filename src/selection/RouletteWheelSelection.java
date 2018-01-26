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
            populationFitness = populationFitness + population.getSingleTour(i).getFitness();
        }

        double[] rouletteWheel ;
        rouletteWheel = new double[population.getSize()];
        rouletteWheel[0] = Math.round((population.getSingleTour(0).getFitness() / populationFitness) *1000000.0)/1000000.0;
        System.out.print(rouletteWheel[0] + " / ");
        for (int i=1 ; i < rouletteWheel.length ; i++) {
            rouletteWheel[i] = Math.round( (rouletteWheel[i - 1] + (population.getSingleTour(i).getFitness() / populationFitness)) *1000000.0) /1000000.0;
            System.out.print(rouletteWheel[i] + " / ");
        }

        ArrayList<Tour> selectedTours;
        selectedTours = new ArrayList<>();

        int c = 1, i=0;
        double r = Math.round(Configuration.instance.Random.nextDouble() *1000000.0)/1000000.0;
        while (c<=Configuration.instance.ROULETTE_COUNT)
        {
            if(r <= rouletteWheel[i])
            {
                Tour a = population.getSingleTour(i);
                if (alreadySelected(selectedTours, a)==false)
                {
                    selectedTours.add( a);
                    System.out.println();
                    System.out.println( c + " + " + r + " + " + i);
                    c++;
                    i=0;
                    r= Math.round(Configuration.instance.Random.nextDouble() *1000000.0)/1000000.0;
                }
                else
                {
                    System.out.println( "Duplikat " + i );
                    i=0;
                    r= Math.round(Configuration.instance.Random.nextDouble() *1000000.0)/1000000.0;
                }
            }
            else if(i<rouletteWheel.length)
            {
                i++;
            }
            else
            {
                i=0;
                r= Math.round(Configuration.instance.Random.nextDouble() *1000000.0)/1000000.0;
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