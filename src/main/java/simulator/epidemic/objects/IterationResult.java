package simulator.epidemic.objects;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class IterationResult {

    private final GridPane gridPane; // сетка

    private final TextField iterAll; // итерационные значения больных
    private final TextField iterIll; // итерационные значения общего числа популяции
    private final TextField iterHealthy; // итерационные значения здоровых

    public IterationResult(GridPane gridPane, TextField iterAll, TextField iterIll, TextField iterHealthy) {
        this.gridPane = gridPane;
        this.iterAll = iterAll;
        this.iterIll = iterIll;
        this.iterHealthy = iterHealthy;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public TextField getIterAll() {
        return iterAll;
    }

    public TextField getIterIll() {
        return iterIll;
    }

    public TextField getIterHealthy() {
        return iterHealthy;
    }

    public void setBasicIterValue() {
        iterAll.setText("0");
        iterIll.setText("0");
        iterHealthy.setText("0");
    }
}
