import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class IDEMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(IDEMain.class.getResource("simulator.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SimulatorEpidemic");
        primaryStage.getIcons().add(new Image("images/epidemic.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
