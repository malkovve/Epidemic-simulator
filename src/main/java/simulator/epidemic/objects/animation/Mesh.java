package simulator.epidemic.objects.animation;

import javafx.scene.layout.GridPane;

public class Mesh {

    private final GridPane gridPane;

    private int sizeX;

    private int sizeY;

    public Mesh(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}
