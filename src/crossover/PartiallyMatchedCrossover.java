package crossover;

import base.City;
import base.Pair;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class PartiallyMatchedCrossover extends AbstractCrossover {

    private ArrayList<Pair> mapping = new ArrayList<>();

    public Tour doCrossover(Tour tour01, Tour tour02) {
        int crossoverpoint1, crossoverpoint2;

        /* Zufällig 2 Crossoverpunkte wählen */
        MersenneTwisterFast rand = Configuration.instance.Random;
        crossoverpoint1 = rand.nextInt(0, tour01.getCities().size() - 1);
        crossoverpoint2 = rand.nextInt(crossoverpoint1 + 1, tour01.getCities().size());
        //System.out.println("crossoverpoint1:" + crossoverpoint1 + " crossoverpoint2: "+ crossoverpoint2);

        /* Die Eltern direkt in die Kinder speichern */
        ArrayList<City> tour1Cities = new ArrayList<>(tour01.getCities());
        ArrayList<City> tour2Cities = new ArrayList<>(tour02.getCities());

        /*Crossoverbereich in den Eltern austauschen */
        City temp;
        for (int i = crossoverpoint1; i < crossoverpoint2; i++) {
            mapping.add(new Pair(tour2Cities.get(i), tour1Cities.get(i)));
            temp = tour2Cities.get(i);
            tour2Cities.set(i, tour1Cities.get(i));
            tour1Cities.set(i, temp);
        }

/*      for (int i= 0; i<mapping.size(); i++){
            System.out.println("Mapping: " + mapping.get(i).getFirst().toString() + mapping.get(i).getSecond().toString());
        }*/


        /* Zyklen im Mapping erkennen und entfernen */
        int value = 1;
        for (int i = 0; i < mapping.size(); i++) {
            for (int j = value; j < mapping.size(); j++) {
                if (mapping.get(i).getSecond() == mapping.get(j).getFirst()) {
                    mapping.get(i).setSecond(mapping.get(j).getSecond());
                    //System.out.println("mapping wird geändert: erste regel" + mapping.get(i).getSecond().toString() + " zweite regel:" + mapping.get(j).getFirst());
                }
            }
            value++;
        }

        /* Werte außerhalb des Crossovers entsprechend ändern - Child 1*/
        for (int i = 0; i < crossoverpoint1; i++) {
            for (int j = 0; j < mapping.size(); j++) {
                //System.out.println("child1 j: " + j);
                if (tour1Cities.get(i) == mapping.get(j).getFirst()) {
                    tour1Cities.set(i, mapping.get(j).getSecond());
                }
            }
        }
        for (int i = crossoverpoint2; i < tour1Cities.size(); i++) {
            for (int j = 0; j < mapping.size(); j++) {
                if (tour1Cities.get(i) == mapping.get(j).getFirst()) {
                    tour1Cities.set(i, mapping.get(j).getSecond());
                }
            }
        }
        /* Werte außerhalb des Crossovers entsprechend ändern - Child 2*/
        /* Hier müssen die Paare aus dem Mapping natürlich andersherum interpretiert werden*/
        for (int i = 0; i < crossoverpoint1; i++) {
            for (int j = 0; j < mapping.size(); j++) {
                if (tour2Cities.get(i) == mapping.get(j).getSecond()) {
                    tour2Cities.set(i, mapping.get(j).getFirst());
                }
            }
        }
        for (int i = crossoverpoint2; i < tour2Cities.size(); i++) {
            for (int j = 0; j < mapping.size(); j++) {
                if (tour2Cities.get(i) == mapping.get(j).getSecond()) {
                    tour2Cities.set(i, mapping.get(j).getFirst());
                }
            }
        }

        /*Neue Kinder-Objekte aus den Touren erstellen*/
        Tour child1 = new Tour();
        child1.setCities(tour1Cities);
        Tour child2 = new Tour();
        child2.setCities(tour2Cities);

        /*        System.out.println(Arrays.toString(child1.getCities().toArray()) + "\n" + Arrays.toString(child2.getCities().toArray()));*/

        /* Das fittere Kind auswählen und zurückgeben*/
        if (child1.compareTo(child2) != 1) {
            return child1;
        } else {
            return child2;
        }
    }

}