package bruteforce;

import java.util.ArrayList;

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

    public void setCities(ArrayList<Integer> cities) {
        this.cities = cities;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
