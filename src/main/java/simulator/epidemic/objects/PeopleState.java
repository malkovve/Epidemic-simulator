package simulator.epidemic.objects;

public enum PeopleState {
    SUSCEPTIBLE("Susceptible"),
    EXPOSED("Exposed"),
    INFECTIOUS("Infected"),
    RECOVERED("Recovered");

    private String state;

    PeopleState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
