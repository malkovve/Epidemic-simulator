package simulator.epidemic.service;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public record MeshService(GridPane gridPane) {

//    public void createMesh(int sizeX, int sizeY) {
//        removeMesh();
//        for (int i = 0; i < sizeX; i++) {
//            gridPane.getColumnConstraints().add(new ColumnConstraints(5));
//        }
//        for (int i = 0; i < sizeY; i++) {
//            gridPane.getRowConstraints().add(new RowConstraints(5));
//        }
//    }
//
//    public void removeMesh() {
//        gridPane.setGridLinesVisible(false);
//        gridPane.getColumnConstraints().clear();
//        gridPane.getRowConstraints().clear();
//        gridPane.getChildren().clear();
//        gridPane.setGridLinesVisible(true);
//    }
}
