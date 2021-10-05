package simulator.epidemic.service;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MeshService {

    private final GridPane gridPane;

    public MeshService(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void createMesh(int sizeX, int sizeY) {
        removeMesh();
        for (int i = 0; i < sizeX; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5));
        }
        for (int i = 0; i < sizeY; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(5));
        }
    }

    private void removeMesh() {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
    }
}
