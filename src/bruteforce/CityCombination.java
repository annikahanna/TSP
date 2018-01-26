package bruteforce;

import java.util.ArrayList;

//Kombination von der Tour mit der gesamten Distanz
public class CityCombination {
    private ArrayList<Integer> cities;
    private double distance;

    public CityCombination(ArrayList<Integer> cities, double distance) {
        this.cities = cities;
        this.distance = distance;
    }

    public ArrayList<Integer> getCities() {
        return cities;
    }

    public double getDistance() {
        return distance;
    }

}
