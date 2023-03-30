import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import simulator.epidemic.SimulatorController;


public class IDEMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // сначала формируем загрузчик и привязываем его к файлу
        FXMLLoader fxmlLoader = new FXMLLoader(IDEMain.class.getResource("simulator.fxml"));
        // а затем уже непосредственно вызываем загрузку дерева разметки из файла
        Parent root = fxmlLoader.load();
        // тут мы вытаскиваем контроллер которые был создан при вызове метода load
        // и сохраняем ссылку на него в переменную
        SimulatorController controller = fxmlLoader.getController();
        // привязываем событие закрытия приложения к нашей функции onStageClose
        primaryStage.setOnHidden(event -> controller.onStageClose());
        // указываем заголовок программы
        primaryStage.setTitle("SimulatorEpidemic");
        // конка программы
        primaryStage.getIcons().add(new Image("images/epidemic.png"));
        // устанавливает scene внутрь stage. cодержимое scene является макет нашей программе котороый формировали в файле simulator.fxml
        primaryStage.setScene(new Scene(root));
        // делает видимым объект stage
        primaryStage.show();
    }
}
