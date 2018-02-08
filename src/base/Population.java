package base;

import java.util.ArrayList;

public class Population {
    private ArrayList<Tour> tours;

    public ArrayList<Tour> getTours() {
        return tours;
    }

    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }

    public Population() {
        this.tours = new ArrayList<Tour>();
    }

    public void addTourToPopulation(Tour t) {
        this.tours.add(t);
    }

    public int getSize() {
        return this.tours.size();
    }

    public Tour getSingleTour(int index) {
        return this.tours.get(index);
    }

    public void setSingleTour(int index, Tour tour){
        this.tours.set(index, tour);
    }

    public void removeSingleTour(int index) {
        this.tours.remove(index);
    }

    public void trimSingleTour() {
        this.tours.trimToSize();
    }

    public ArrayList<Tour> getPopulation() {
        return this.tours;
    }

    public double getPopulationScore() {
        double sum = 0;
        for (Tour tour : tours) {
            sum += tour.getFitness();
        }

        return sum / tours.size();
    }
}