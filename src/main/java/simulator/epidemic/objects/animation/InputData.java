package simulator.epidemic.objects.animation;

public class InputData {

    private final int meshSizeX;

    private final int meshSizeY;

    private final int allPeople;

    private final int illPeople;

    public InputData(int meshSizeX, int meshSizeY, int allPeople, int illPeople) {
        this.meshSizeX = meshSizeX;
        this.meshSizeY = meshSizeY;
        this.allPeople = allPeople;
        this.illPeople = illPeople;
    }

    public int getMeshSizeX() {
        return meshSizeX;
    }

    public int getMeshSizeY() {
        return meshSizeY;
    }

    public int getQuantityAllPeople() {
        return allPeople;
    }

    public int getIllPeople() {
        return illPeople;
    }

    public int getHealthyPeople() {
        return allPeople - illPeople;
    }
}
