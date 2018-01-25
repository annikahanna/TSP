package bruteforce;

import data.TSPLIBReader;

import java.util.*;

import static java.lang.Math.toIntExact;

public class Permutation {


    long dimension = 20l;

    double distance = 0;

    ArrayList<CityCombination> ergebnis = new ArrayList<>();


    public void generate(TSPLIBReader tsplibReader) {
        MinimalDistance min = new MinimalDistance();

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

        }
        Scanner scanner = new Scanner(System.in);
        String eval = scanner.nextLine();

        //Evaluierung der Eingabe
        switch (eval) {
            case "all":
                CityCombination tour = min.sort(ergebnis);
                System.out.println(tour.getDistance());

                for (int q = 0; q < tour.getCities().size(); q++) {
                    System.out.println(tour.getCities().get(q));
                }

                break;
            case "first":
                ArrayList<CityCombination> first = ergebnis;

                for (int v = ergebnis.size() / 4; v < ergebnis.size(); v++) {
                    first.remove(v);
                }

                CityCombination tourFirst = min.sort(first);
                System.out.println(tourFirst.getDistance());

                for (int q = 0; q < tourFirst.getCities().size(); q++) {
                    System.out.println(tourFirst.getCities().get(q));
                }

                break;
            case "middle":
                ArrayList<CityCombination> middle = ergebnis;

                for (int v = ergebnis.size() / 4 * 3; v < ergebnis.size(); v++) {
                    middle.remove(v);
                }

                for (int w = 0; w < ergebnis.size() / 4; w++) {
                    middle.remove(w);
                }

                CityCombination tourMiddle = min.sort(middle);
                System.out.println(tourMiddle.getDistance());

                for (int q = 0; q < tourMiddle.getCities().size(); q++) {
                    System.out.println(tourMiddle.getCities().get(q));
                }

                break;
            case "last":
                ArrayList<CityCombination> last = ergebnis;

                for (int v = 0; v < ergebnis.size() / 4 * 3; v++) {
                    last.remove(v);
                }

                CityCombination tourLast = min.sort(last);
                System.out.println(tourLast.getDistance());

                for(int q = 0; q< tourLast.getCities().size(); q++){
                    System.out.println(tourLast.getCities().get(q));
                }

                break;
            default:
                System.out.println("Wrong parameter");
        }
        //zum testen



          /*  for(int m = 0; m<280; m++)
            System.out.println(cities.get(m));
            System.out.println(distance);*/

    }
}
