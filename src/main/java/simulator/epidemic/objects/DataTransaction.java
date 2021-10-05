package simulator.epidemic.objects;

import java.util.ArrayList;

public class DataTransaction {
    public ArrayList<People> illPeopleMap;
    public ArrayList<People> healthyPeopleMap;
    public ArrayList<Coordinate> illCoordinate;

    public DataTransaction(ArrayList<People> illPeopleMap, ArrayList<People> healthyPeopleMap, ArrayList<Coordinate> illCoordinate) {
        this.illPeopleMap = illPeopleMap;
        this.healthyPeopleMap = healthyPeopleMap;
        this.illCoordinate = illCoordinate;
    }
}
