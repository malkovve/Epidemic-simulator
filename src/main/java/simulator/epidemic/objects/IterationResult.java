package simulator.epidemic.objects;

import javafx.scene.control.TextField;

public class IterationResult {

    private final TextField iterAll; // итерационные значения больных
    private final TextField iterIll; // итерационные значения общего числа популяции
    private final TextField iterHealthy; // итерационные значения здоровых
    private final TextField iteration;

    public IterationResult(TextField iterAll, TextField iterIll, TextField iterHealthy, TextField iteration) {
        this.iterAll = iterAll;
        this.iterIll = iterIll;
        this.iterHealthy = iterHealthy;
        this.iteration = iteration;
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

    public TextField getIteration() {
        return iteration;
    }

    public void setBasicIterValue() {
        iterAll.setText("0");
        iterIll.setText("0");
        iterHealthy.setText("0");
    }


}
