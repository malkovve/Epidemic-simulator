package simulator.epidemic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import simulator.epidemic.objects.InputData;
import simulator.epidemic.utils.Animation;
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

    private Animation animation;

    @FXML
    private void acceptData(ActionEvent event) {
        meshSize.setEditable(false);
        quantityPeople.setEditable(false);
        illPeople.setEditable(false);
        try {
            String[] strMesh = meshSize.getText().split("x");
            if (strMesh.length != 2 || illPeople.getText().length() == 0 || quantityPeople.getText().length() == 0) {
                throw new NumberFormatException("example mesh input: NxN");
            } else {
                int sizeX = strMesh[0].length() != 0 ? Integer.parseInt(strMesh[0]) : 0;
                int sizeY = strMesh[1].length() != 0 ? Integer.parseInt(strMesh[1]) : 0;
                int quantityAllPeople = Integer.parseInt(quantityPeople.getText());
                int quantityIllPeople = Integer.parseInt(illPeople.getText());
                int quantityHealthyPeople = quantityAllPeople - quantityIllPeople;

                InputData inputData = new InputData(sizeX, sizeY, quantityAllPeople, quantityIllPeople, quantityHealthyPeople);
                animation = new Animation(gridPane);
                animation.prepare(inputData);
            }


        } catch (Throwable e) {
            logger.error("incorrect input of initial data", e.getMessage());
        }
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

