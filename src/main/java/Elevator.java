import building.Building;
import entity.Passenger;
import lombok.Data;
import building.Direction;
import building.Floor;

import java.util.ArrayList;

@Data
public class Elevator {
    public ArrayList<Passenger> riders = new ArrayList<>();
    public int currentFloor = 0;
    Building building = new Building();
    Direction ridingDirection = Direction.UP;

    public Elevator() {
    }

    public void start() {
        currentSituationInTheBuilding(0);
        int moves = 0;
        while (moves < 999) {
            moving();
            currentSituationInTheBuilding(moves);
            moves++;
        }
    }

    public void moving() {
        loadPassengers();
        unloadPassengers();
        setRidingDirection();
        loadPassengers();
        move();
    }

    private void move() {
        if (ridingDirection.equals(Direction.UP)) currentFloor++;
        else if (ridingDirection.equals(Direction.DOWN)) currentFloor--;
    }

    public void setRidingDirection() {
        if (isAvailableDirection(getRidersDirection())) {
            ridingDirection = getRidersDirection();
            loadPassengers();
            unloadPassengers();
        } else if (isAvailableDirection(lookForPassengers())) {
            ridingDirection = lookForPassengers();
            loadPassengers();
            unloadPassengers();
        }
    }

    public void loadPassengers() {
        for (int i = getRidersNum(); i < getMaxElevatorCapacity(); i++) {
            if (detectPassengerWithSameDirection() != null) {
                Passenger lucky = detectPassengerWithSameDirection();
                riders.add(lucky);
                getCurrentWaitingLine().remove(lucky);
            }
        }
    }

    public void unloadPassengers() {
        for (int i = riders.size(); i > 0; i--) {
            ArrayList<Passenger> listOfPassengerToRemove = new ArrayList<>();
            riders.forEach(s -> {
                if (s.floorToGo == currentFloor) {
                    listOfPassengerToRemove.add(s);
                    addSatisfiedPassenger(s);
                }
            });
            riders.removeAll(listOfPassengerToRemove);
        }
    }

    public Passenger detectPassengerWithSameDirection() {
        for (Passenger p : getCurrentWaitingLine()) {
            if (ridingDirection.equals(getPassengerDirection(p))) return p;
        }
        return null;
    }

    public ArrayList<Passenger> getCurrentWaitingLine() {
        return building.getFloors().get(currentFloor).waitingPassengers;
    }

    public ArrayList<Passenger> getWaitingLine(int numberOfFloor) {
        return building.getFloors().get(numberOfFloor).waitingPassengers;
    }

    public Direction getPassengerDirection(Passenger passenger) {
        if (passenger.floorToGo > currentFloor) return Direction.UP;
        else if (passenger.floorToGo < currentFloor) return Direction.DOWN;
        else return Direction.STOP;
    }

    public int getRidersNum() {
        return riders.size();
    }

    public int getMaxElevatorCapacity() {
        return building.getBuildingProperties().ELEVATOR_CAPACITY;
    }

    public int getBuildingMaxFloor() {
        return building.getBuildingProperties().MAX_FLOOR;
    }

    public void addSatisfiedPassenger(Passenger p) {
        building.getFloors().get(currentFloor).satisfiedPeople.add(p);
    }

    public Floor getFloor(int numberOfFloor) {
        return building.getFloors().get(numberOfFloor - 1);
    }

    public Direction getRidersDirection() {
        if (riders.isEmpty()) return lookForPassengers();
        for (Passenger p : riders) {
            return getPassengerDirection(p);
        }
        return Direction.DOWN;
    }

    public boolean isAvailableDirection(Direction direction) {
        if (direction.equals(Direction.UP)) {
            return getBuildingMaxFloor() > currentFloor + 1;
        } else if (direction.equals(Direction.DOWN)) {
            return 0 <= currentFloor - 1;
        } else if (direction.equals(Direction.STOP)) {
            unloadPassengers();
            currentSituationInTheBuilding(10001);
            System.exit(80085);
            return true;
        } else throw new IllegalArgumentException(direction + ": should not be here");
    }

    public Direction lookForPassengers() {
        for (int i = 1; i < getBuildingMaxFloor() - currentFloor; i++) {
            if (!getWaitingLine(currentFloor + i).isEmpty()) return Direction.UP;
        }
        for (int i = currentFloor; i > 0; i--) {
            if (!getWaitingLine(i).isEmpty()) return Direction.DOWN;
        }
        return Direction.STOP;
    }

    public void currentSituationInTheBuilding(int moves) {
        System.out.printf("\t\t\t\t\t   < : : : : : Step %s : : : : : > \n", moves);
        for (int i = getBuildingMaxFloor(); i > 0; i--) {
            boolean bool = i == currentFloor + 1;
            System.out.printf("%2d. %-30s|%5s|%-30s\n",
                    i - 1,
                    getFloor(i).satisfiedPeopleToString(),
                    elevatorCabinToString(bool),
                    getFloor(i).waitingPassengersToString()
            );
        }
    }

    public String elevatorCabinToString(boolean toPrint) {
        if (!toPrint) return "     ";
        StringBuilder stringBuilder = new StringBuilder();
        for (Passenger p : riders) {
            stringBuilder.append(p.floorToGo);
        }
        if (riders.isEmpty()) return "<   >";
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Building.Elevator{" +
                "riders=" + riders +
                ", currentFloor=" + currentFloor +
                ", building=" + building +
                ", ridingDirection=" + ridingDirection +
                '}';
    }
}
