package simulator.epidemic;

import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import simulator.epidemic.animation.Animation;
import simulator.epidemic.exception.SimulatorException;
import simulator.epidemic.objects.ApplicationSettings;
import simulator.epidemic.objects.IterationResult;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.animation.Mesh;
import simulator.epidemic.utils.DataGenerator;
import simulator.epidemic.utils.ObjectValidator;
import simulator.log.Logger;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private TextField iterAll; // итерационные значения больных
    @FXML
    private TextField iterHealthy; // итерационные значения здоровых
    @FXML
    private TextField iterIll; // итерационные значения общего числа популяции
    @FXML
    private TextField iteration; // итерационные значения
    @FXML
    private TextField probabilityInfection; // вероятность заражения
    @FXML
    private TextField displayIter; //
    @FXML
    private GridPane gridPane; // сетка
    @FXML
    private ComboBox<String> speed; // указатель на скорость прокрутки кадров
    @FXML
    private ComboBox<String> calendarDate; //
    @FXML
    private CheckBox extraSettings; // флаг дополнительных настроек
    @FXML
    private CheckBox cellularAutomaton; // флаг клеточного автомата
    @FXML
    private Tab settings; // окно дополнительных настроек
    @FXML
    private Tab chart; // окно графика
    @FXML
    private Tab tabAutomaton; // окно клеточного автомата
    @FXML
    private LineChart<String, Float> lineChart;

    public static ApplicationSettings applicationSettings;
    private volatile boolean isAcceptData = false;
    private Animation animation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series<String, Float> stringFloatSeries = new XYChart.Series<>();
        lineChart.getData().add(stringFloatSeries);
        applicationSettings = new ApplicationSettings(
                new Mesh(gridPane),
                new IterationResult(iterAll, iterIll, iterHealthy, iteration),
                stringFloatSeries
        );

//----------------------------------------------------------------------------------------------------------------------
        ObservableList<String> list = FXCollections.observableArrayList("0.25", "0.5", "1", "1.5", "2", "3", "4", "5");
        speed.setItems(list); // устанавливаем выбранный элемент по умолчанию
        speed.getSelectionModel().select(4); // устанавливаем по умолчанию значение 2 секунды

//----------------------------------------------------------------------------------------------------------------------
        ObservableList<String> listDate = FXCollections.observableArrayList("сек", "мин", "час", "день", "неделя", "месяц");
        calendarDate.setItems(listDate); // устанавливаем выбранный элемент по умолчанию
        calendarDate.getSelectionModel().select(1); // устанавливаем по умолчанию значение 2 секунды

//----------------------------------------------------------------------------------------------------------------------
        settings.setDisable(true); // устанавливаем значения по умолчанию
//        привязываем лямбда-функцию к реакции на изменение флага дополнительных настроек
        extraSettings.selectedProperty().addListener((observable, oldValue, newValue) -> {
            settings.setDisable(oldValue);
        });

//----------------------------------------------------------------------------------------------------------------------
        cellularAutomaton.setSelected(true); // установка значения по умолчанию
//        привязываем лямбда-функцию к реакции на вкл/выкл вкладки клеточного автомата
        cellularAutomaton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tabAutomaton.setDisable(oldValue);
        });

//----------------------------------------------------------------------------------------------------------------------
        applicationSettings.setApplicationSpeed(1000L); // установка значения по умолчанию
//        привязываем лямбда-функцию к реакции на изменение скорости прокрутки анимации
        speed.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Animation.isAnimation) {
                // fixme: изменить подход
                switch (newValue) {
                    case ("0.25") -> applicationSettings.setApplicationSpeed(250L);
                    case ("0.5") -> applicationSettings.setApplicationSpeed(500L);
                    case ("1") -> applicationSettings.setApplicationSpeed(1000L);
                    case ("1.5") -> applicationSettings.setApplicationSpeed(1500L);
                    case ("2") -> applicationSettings.setApplicationSpeed(2000L);
                    case ("3") -> applicationSettings.setApplicationSpeed(3000L);
                    case ("4") -> applicationSettings.setApplicationSpeed(4000L);
                    case ("5") -> applicationSettings.setApplicationSpeed(5000L);
                }
            }
        });
        log.info("Application Start");
    }

    @FXML
    private void acceptData(ActionEvent event) throws SimulatorException {
        try {
            if (!isAcceptData) {
//            Проверка валидности данных
                ObjectValidator.validateInputData(meshSize.getText(), quantityPeople.getText(), illPeople.getText());
                applicationSettings.setProbabilityInfection(Float.parseFloat(probabilityInfection.getText()));
//            Запускаем генерацию данных
                List<People> peopleList = DataGenerator.generatePeople(applicationSettings);
                animation = new Animation(peopleList);
//            Подготавливаем сетку
                animation.prepareMesh(applicationSettings.getMesh().getSizeX(), applicationSettings.getMesh().getSizeY());
//            Подготавливаем людей
                animation.preparePeople();

                meshSize.setEditable(false);
                quantityPeople.setEditable(false);
                illPeople.setEditable(false);
                probabilityInfection.setEditable(false);
                speed.setEditable(false);
                isAcceptData = true;
            }
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
        probabilityInfection.setEditable(true);
        speed.setEditable(true);
        isAcceptData = false;
    }

    @FXML
    private void start(ActionEvent event) {
        if (!Animation.isAnimation && isAcceptData) {
            Animation.isAnimation = true;
            animation.start();
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        animation.stop();
        Animation.isAnimation = false;
    }

    @FXML
    public void onStageClose() {
        if (Animation.isAnimation) {
            animation.stop();
        }
        log.info("The program has been successfully closed.");
    }
}

