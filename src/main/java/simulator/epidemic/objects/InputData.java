package simulator.epidemic.objects;

public class InputData {

    private final int meshSizeX;

    private final int meshSizeY;

    private final int quantityAllPeople;

    private final int quantityIllPeople;

    private final int quantityHealthyPeople;

    public InputData(int meshSizeX, int meshSizeY, int quantityAllPeople, int quantityIllPeople, int quantityHealthyPeople) {
        this.meshSizeX = meshSizeX;
        this.meshSizeY = meshSizeY;
        this.quantityAllPeople = quantityAllPeople;
        this.quantityIllPeople = quantityIllPeople;
        this.quantityHealthyPeople = quantityHealthyPeople;
    }

    public int getMeshSizeX() {
        return meshSizeX;
    }

    public int getMeshSizeY() {
        return meshSizeY;
    }

    public int getQuantityAllPeople() {
        return quantityAllPeople;
    }

    public int getQuantityIllPeople() {
        return quantityIllPeople;
    }

    public int getQuantityHealthyPeople() {
        return quantityHealthyPeople;
    }
}
