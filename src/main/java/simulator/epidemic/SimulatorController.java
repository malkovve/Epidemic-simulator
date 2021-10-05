package simulator.epidemic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import simulator.log.Logger;

public class SimulatorController {

    private static final Logger logger = new Logger(SimulatorController.class);

    @FXML
    private TextField meshSize;
    @FXML
    private TextField quantityPeople;
    @FXML
    private TextField illPeople;
    @FXML
    private GridPane gridPane;

    @FXML
    private void acceptData(ActionEvent event) {

    }

    @FXML
    private void resetData(ActionEvent event) {

    }

    @FXML
    private void start(ActionEvent event) {

    }

    @FXML
    public void stop(ActionEvent event) {

    }

}

