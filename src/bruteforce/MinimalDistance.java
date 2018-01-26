package bruteforce;

import java.util.ArrayList;

//Sortiert eine Liste nach der kürzesten Distanz
public class MinimalDistance {

    public CityCombination sort (ArrayList<CityCombination> ergebnis){
        for (int i = 0; i < ergebnis.size() - 1; i++) {
            for (int j = i + 1; j < ergebnis.size(); j++) {
                if (ergebnis.get(i).getDistance() > ergebnis.get(j).getDistance()) {
                    CityCombination temp = ergebnis.get(i);
                    ergebnis.set(i, ergebnis.get(j));
                    ergebnis.set(j, temp);
                }
            }
        }
        return ergebnis.get(0);
    }
}
