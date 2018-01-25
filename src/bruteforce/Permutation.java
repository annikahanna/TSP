package bruteforce;

import data.TSPLIBReader;

import java.util.ArrayList;
import java.util.Collections;

public class Permutation {


    long dimension = 4000000000l;
    ArrayList<Integer> cities = new ArrayList<>();
    double distance = 0;

    //Schleife für 4 Mrd. Wiederholungen
    public void generate(TSPLIBReader tsplibReader) {
        for (long k = 0l; k < dimension; k++) {
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
            //zum testen
            //System.out.println(distance);
        }
    }
}
