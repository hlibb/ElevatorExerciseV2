package building;

public class BuildingProperties {
    public final int MAX_FLOOR;
    public final int ELEVATOR_CAPACITY;

    public BuildingProperties() {
        MAX_FLOOR = (int) (Math.random() * 15 + 5);
        ELEVATOR_CAPACITY = 5;
    }
}
