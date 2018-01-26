package base;

public class Pair {
    private City first;
    private City second;

    public City getFirst() {
        return first;
    }

    public City getSecond() {
        return second;
    }

    public void setFirst(City first) {
        this.first = first;
    }

    public void setSecond(City second) {
        this.second = second;
    }

    public Pair(City first, City second){
        this.first = first;
        this.second = second;
    }

}
