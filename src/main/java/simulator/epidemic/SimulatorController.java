package simulator.epidemic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import simulator.epidemic.objects.InputData;
import simulator.epidemic.utils.Animation;
import simulator.log.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private volatile boolean isAnimation = false;
    private volatile boolean isAcceptData = false;
    private ScheduledExecutorService scheduledExecutorService;
    private Animation animation;
    // todo: Реализовать свой класс ошибок для корректного вывода ошибки.
    @FXML
    private void acceptData(ActionEvent event) {
        try {
            long startTime = System.currentTimeMillis();
            String[] strMesh = meshSize.getText().split("x");
            if (strMesh.length != 2) {
                throw new NumberFormatException("example mesh input: NxN");
            }
            if (quantityPeople.getText().length() == 0 || illPeople.getText().length() == 0) {
                throw new NumberFormatException(null);
            }
            int sizeX = strMesh[0].length() != 0 ? Integer.parseInt(strMesh[0]) : 0;
            int sizeY = strMesh[1].length() != 0 ? Integer.parseInt(strMesh[1]) : 0;
            int quantityAllPeople = Integer.parseInt(quantityPeople.getText());
            int quantityIllPeople = Integer.parseInt(illPeople.getText());
            int quantityHealthyPeople = quantityAllPeople - quantityIllPeople;

            InputData inputData = new InputData(sizeX, sizeY, quantityAllPeople, quantityIllPeople, quantityHealthyPeople);
            animation = new Animation(gridPane);
            animation.prepare(inputData);

//            long endTime = System.currentTimeMillis();
//            logger.timerInfo("Prepare Data time work: ", (endTime - startTime));
            meshSize.setEditable(false);
            quantityPeople.setEditable(false);
            illPeople.setEditable(false);
            isAcceptData = true;
        } catch (Throwable e) {
            logger.error("incorrect input of initial data", e.getMessage());
        }

    }

    @FXML
    private void resetData(ActionEvent event) {
        meshSize.setEditable(true);
        quantityPeople.setEditable(true);
        illPeople.setEditable(true);
    }

    @FXML
    private void start(ActionEvent event) {
        if (!isAnimation && isAcceptData) {
            isAnimation = true;
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            Runnable runnable = () -> Platform.runLater(() -> animation.animationAlg());
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
        isAnimation = false;
    }

}

