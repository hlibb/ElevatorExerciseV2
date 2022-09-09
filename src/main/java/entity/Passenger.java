package entity;

public class Passenger {
    public int floorToGo;

    public Passenger(int maxFloor) {
        floorToGo = (int) (Math.random() * maxFloor - 1);
    }
}
