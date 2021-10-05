package simulator.epidemic.objects;


public class People {

    private String id;
    private Coordinate coordinate;
    private PeopleState state;

    public People(String id, Coordinate coordinate, PeopleState state) {
        this.id = id;
        this.coordinate = coordinate;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getState() {
        return state.getState();
    }

    public void setState(PeopleState state) {
        this.state = state;
    }
}
