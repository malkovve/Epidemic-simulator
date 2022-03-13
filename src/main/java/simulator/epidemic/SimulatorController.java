package simulator.epidemic;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import simulator.epidemic.animation.Animation;
import simulator.epidemic.exception.SimulatorException;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.epidemic.objects.animation.InputData;
import simulator.epidemic.utils.DataGenerator;
import simulator.epidemic.utils.ObjectValidator;
import simulator.log.Logger;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// Суть стенаний виртуальной машины заключается в том, что comboBox нулевой. А нулевой он потому что привязка полей
// помеченных декоратором @FXML происходит уже после вызова конструктора, где-то в терньях фреймворка JavaFX.
// Поэтому добавление элемента в конструкторе не прокатит.
// Для этого существует интерфейс Initializable, в котором реализован метод initialize.
// сначала вызывается конструктор, затем заносятся любые аннотированные поля @FXML, затем вызывается initialize().
// Поэтому конструктор НЕ имеет доступа к полям @FXML, относящимся к компонентам, определенным в файле *.fxml,
// тогда как initialize() имеет к ним доступ.
public class SimulatorController implements Initializable {

    private static final Logger log = new Logger(SimulatorController.class);

    @FXML
    private TextField meshSize; // поле размерности сетки
    @FXML
    private TextField quantityPeople; // поле количества людей
    @FXML
    private TextField illPeople; // поле числа заболевших
    @FXML
    private GridPane gridPane; // сетка
    @FXML
    private ComboBox<String> speed; // указатель на скорость прокрутки кадров
    @FXML
    private TextField iterAll; // итерационные значения больных
    @FXML
    private TextField iterHealthy; // итерационные значения здоровых
    @FXML
    private TextField iterIll; // итерационные значения общего числа популяции
    @FXML
    private TextField iteration; // итерационные значения
    @FXML
    private CheckBox extraSettings; // флаг дополнительных настроек
    @FXML
    private ComboBox<String> calendarDate; // флаг дополнительных настроек
    @FXML
    private Tab settings; // окно дополнительных настроек

    private volatile boolean isAnimation = false;
    private volatile boolean isAcceptData = false;
    public static InputData inputData;
    private Animation animation;
    private int iter = 0;
    private Long period;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings.setDisable(true); // устанавливаем значения по умолчанию
        period = 2000L;

        ObservableList<String> list = FXCollections.observableArrayList("0.25", "0.5", "1", "1.5", "2", "3", "4", "5");
        speed.setItems(list); // устанавливаем выбранный элемент по умолчанию
        speed.getSelectionModel().select(4); // устанавливаем по умолчанию значение 2 секунды

        ObservableList<String> listDate = FXCollections.observableArrayList("сек", "мин", "час", "день", "неделя", "месяц");
        calendarDate.setItems(listDate); // устанавливаем выбранный элемент по умолчанию
        calendarDate.getSelectionModel().select(1); // устанавливаем по умолчанию значение 2 секунды

//        привязываем лямбда-функцию к реакции на изменение флага дополнительных настроек
        extraSettings.selectedProperty().addListener((observable, oldValue, newValue) -> {
            settings.setDisable(oldValue);
        });
//        привязываем лямбда-функцию к реакции на изменение скорости прокрутки анимации
        speed.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!isAnimation) {
                // fixme: изменить подход
                switch (newValue) {
                    case ("0.25") -> period = 250L;
                    case ("0.5") -> period = 500L;
                    case ("1") -> period = 1000L;
                    case ("1.5") -> period = 1500L;
                    case ("2") -> period = 2000L;
                    case ("3") -> period = 3000L;
                    case ("4") -> period = 4000L;
                    case ("5") -> period = 5000L;
                }
            }
        });
        log.info("Application Start");
    }

    // добавить проверку isAcceptData
    @FXML
    private void acceptData(ActionEvent event) throws SimulatorException {
        try {
//            Проверка валидности данных
            inputData = ObjectValidator.validateInputData(meshSize.getText(), quantityPeople.getText(), illPeople.getText());
//            Запускаем генерацию данных
            List<People> peopleList = DataGenerator.generatePeople(inputData);
            long l = System.currentTimeMillis();
            long l1 = System.currentTimeMillis();
            System.out.println("Grouping: " + (l1 - l));
            animation = new Animation(gridPane, peopleList);
//            Подготавливаем сетку
            animation.prepareMesh(inputData.getMeshSizeX(), inputData.getMeshSizeY());
//            Подготавливаем людей
            animation.preparePeople();

            meshSize.setEditable(false);
            quantityPeople.setEditable(false);
            illPeople.setEditable(false);
            isAcceptData = true;
        } catch (Throwable e) {
            log.error("incorrect input of initial data", e.getMessage());
            throw e;
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
            animation.start();
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        animation.stop();
        isAnimation = false;
    }

    @FXML
    public void onStageClose() {
        animation.stop();
        log.info("The program has been successfully closed.");
    }
}

