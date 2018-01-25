package bruteforce;

import data.TSPLIBReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class Permutation {


    long dimension = 2l;

    double distance = 0;

    ArrayList<CityCombination> ergebnis = new ArrayList<>();


    public void generate(TSPLIBReader tsplibReader) {

        //Schleife für 4 Mrd. Wiederholungen
        for (long k = 0l; k < dimension; k++) {

            ArrayList<Integer> cities = new ArrayList<>();
            distance = 0;

            //Schleife die, die Liste füllt
            for (int j = 0; j < 280; j++) {

                cities.add(j);
            }


            //Städte werden gemischt
            Collections.shuffle(cities);

            //Distanzen von Stadt zu Stadt werden addiert
            for (int i = 0; i < 278; i++) {

                distance = distance + tsplibReader.getDistance(cities.get(i), cities.get(i + 1));
            }

            //Von der letzten Stadt geht man zur ersten zurück
            distance = distance + tsplibReader.getDistance(cities.get(279), cities.get(0));
            ergebnis.add(new CityCombination(cities, distance));
            //zum testen

          /*  for(int m = 0; m<280; m++)
            System.out.println(cities.get(m));
            System.out.println(distance);*/
        }
    }
}
