package selection;

import base.Population;
import base.Tour;
import main.Configuration;
import java.util.ArrayList;

public class TournamentSelection implements ISelection {

    /**
     * Implementation for Tournament selection
     * @param population Given population
     * @return Selected 'winners' of the combat
     */
    public ArrayList<Tour> doSelection(Population population) {

        // Randomly choose contenders
        Population contenders = chooseContenders(population);

        return mortalKombat(contenders);
    }

    /**
     * Randomly choose contenders for the fight
     * @param population Given population
     * @return Chosen contenders
     */
    private Population chooseContenders(Population population){

        Population contenders = new Population();

        // Choose random contenders from given population
        for(int i = 0; i < Configuration.instance.FIGHT_COUNT; i++)
            contenders.addTourToPopulation(population.getSingleTour(Configuration.instance.Random.nextInt(0, 99)));

        return contenders;
    }

    /**
     * Let the contenders fight to determine winners
     * @param contenders Chosen contenders
     * @return Winners of the combat
     */
    private ArrayList<Tour> mortalKombat(Population contenders){

        ArrayList<Tour> winners = new ArrayList<>();

        for(int k = 0; k < Configuration.instance.FIGHT_COUNT - 1; k++){

            // Only continue if at least 2 contenders remain
            if(contenders.getSize() > 1){

                // Randomly get 2 contenders from the contender list
                int contender1 = Configuration.instance.Random.nextInt(0, Configuration.instance.FIGHT_COUNT - (2*k) -1);
                int contender2 = Configuration.instance.Random.nextInt(0, Configuration.instance.FIGHT_COUNT - (2*k) -1);

                // Make sure they are different (likely with small contender numbers towards the end)
                while(contender1 == contender2)
                    contender2 = Configuration.instance.Random.nextInt(0, Configuration.instance.FIGHT_COUNT - (2*k) -1);

                // Get fitness of the contenders
                double fitnessContender1 = contenders.getSingleTour(contender1).getFitness();
                double fitnessContender2 = contenders.getSingleTour(contender2).getFitness();

                // Choose winner
                if(fitnessContender1 < fitnessContender2)
                    winners.add(contenders.getSingleTour(contender1));
                else
                    winners.add(contenders.getSingleTour(contender2));

                // Delete current contenders
                if(contender1 > contender2){
                    contenders.removeSingleTour(contender1);
                    contenders.removeSingleTour(contender2);
                }
                else{
                    contenders.removeSingleTour(contender2);
                    contenders.removeSingleTour(contender1);
                }

                // Remove empty spots in the contender list
                contenders.trimSingleTour();
            }
        }
        return winners;
    }

    public String toString() {return getClass().getSimpleName();}
}