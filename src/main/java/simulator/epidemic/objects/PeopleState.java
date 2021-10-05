package simulator.epidemic.objects;

public enum PeopleState {
    HEALTHY("healthy"),
    VERY_SICK("very_sick"),
    SICK("sick");

    private String state;

    PeopleState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
