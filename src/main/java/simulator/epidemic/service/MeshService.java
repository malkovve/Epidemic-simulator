package simulator.epidemic.service;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MeshService {

    public static void createMesh(GridPane gridPane, int sizeX, int sizeY) {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < sizeX; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5));
        }
        for (int i = 0; i < sizeY; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(5));
        }
    }
}
