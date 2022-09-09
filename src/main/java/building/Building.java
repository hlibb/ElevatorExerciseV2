package building;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Building {
    private BuildingProperties buildingProperties;
    private ArrayList<Floor> floors = new ArrayList<>();

    public Building() {
        buildingProperties = new BuildingProperties();
        for (int i = 0; i < buildingProperties.MAX_FLOOR; i++) {
            floors.add(
                    new Floor(i, buildingProperties.MAX_FLOOR)
            );
        }
    }
}
