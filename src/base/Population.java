package base;

import java.util.ArrayList;

public class Population {
    private ArrayList<Tour> tours;

    public Population(){
        this.tours = new ArrayList<>();
    }

    public void addTourToPopulation(Tour t){
        this.tours.add(t);
    }

    public int getSize(){
        return this.tours.size();
    }

    public Tour getSingleTour(int index){
        return this.tours.get(index);
    }

    public void removeSingleTour(int index){
        this.tours.remove(index);
    }

    public void trimSingleTour(){
       this.tours.trimToSize();
    }

    public ArrayList<Tour> getPopulation(){
        return this.tours;
    }
}