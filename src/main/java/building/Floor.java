package building;

import entity.Passenger;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Floor {
    public int floorNumber;
    public ArrayList<Passenger> waitingPassengers = new ArrayList<>();
    public List<Passenger> satisfiedPeople = new ArrayList<>();

    public Floor(int floorNumber, int maxFloor) {
        this.floorNumber = floorNumber;
        int lineLength = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < lineLength; i++) {
            Passenger passengerToAdd = new Passenger(maxFloor);
            if (passengerToAdd.floorToGo != floorNumber) waitingPassengers.add(passengerToAdd);
        }
    }

    public String waitingPassengersToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Passenger p : waitingPassengers) {
            stringBuilder.append("[").append(p.floorToGo).append("]");
        }
        return stringBuilder.toString();
    }

    public String satisfiedPeopleToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Passenger p : satisfiedPeople) {
            stringBuilder.append("[").append(p.floorToGo).append("]");
        }
        return stringBuilder.toString();
    }
}
