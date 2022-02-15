package simulator.epidemic.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulator.epidemic.objects.DataTransaction;
import simulator.epidemic.objects.ElementsGUI;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.epidemic.objects.animation.InputData;
import simulator.epidemic.service.MeshService;
import simulator.epidemic.service.PeopleService;
import simulator.log.Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Animation {

    private static final Logger logger = new Logger(Animation.class);

    public final Image greenPoint = new Image("images/greenPoint.png");
    public final Image redPoint = new Image("images/redPoint.png");

    private final ElementsGUI elementsGUI;

    private DataTransaction fullDataPeople;
    private PeopleService peopleService;

    public Animation(ElementsGUI elementsGUI, InputData inputData) {
        this.elementsGUI = elementsGUI;

//        создаем сетку указанной размерности
//        long startTime = System.currentTimeMillis();
        MeshService.createMesh(elementsGUI.getGridPane(), inputData.getMeshSizeX(), inputData.getMeshSizeY());
//        long endTime = System.currentTimeMillis();
//        logger.timerInfo("Create mesh: ", (endTime - startTime));

//        генерируем базу данных популяции
//        long startTime2 = System.currentTimeMillis();
        peopleService = new PeopleService(elementsGUI); // инициализируем сервис
        fullDataPeople = peopleService.prepare(inputData.getIllPeople(), inputData.getHealthyPeople());
//        long endTime2 = System.currentTimeMillis();
//        logger.timerInfo("Create data base people: ", (endTime2 - startTime2));

//        добавление элементов на сетку
        long startTime3 = System.currentTimeMillis();
        for (People healthyPeople : fullDataPeople.healthyPeopleMap) {
            addPeopleInMesh(greenPoint, healthyPeople);
        }
        for (People healthyPeople : fullDataPeople.illPeopleMap) {
            addPeopleInMesh(redPoint, healthyPeople);
        }
        long endTime3 = System.currentTimeMillis();
        logger.timerInfo("Add people in mesh: ", (endTime3 - startTime3));

//        изменение значений итерации
        elementsGUI.getIterAll().setText(String.valueOf(fullDataPeople.healthyPeopleMap.size() + fullDataPeople.illPeopleMap.size()));
        elementsGUI.getIterIll().setText(String.valueOf(fullDataPeople.illPeopleMap.size()));
        elementsGUI.getIterHealthy().setText(String.valueOf(fullDataPeople.healthyPeopleMap.size()));
    }

    public void start(CompletableFuture<Void> future) {
        elementsGUI.getGridPane().setGridLinesVisible(false);
        elementsGUI.getGridPane().getChildren().clear();
        elementsGUI.getGridPane().setGridLinesVisible(true);

        long startTime = System.currentTimeMillis();
        animationHealthyPeople();
        animationIllPeople();
        long endTime = System.currentTimeMillis();
        logger.timerInfo("alg time: ", (endTime - startTime));

        long startTime2 = System.currentTimeMillis();
        for (People people : fullDataPeople.illPeopleMap) {
            addPeopleInMesh(redPoint, people);
        }
        for (People people : fullDataPeople.healthyPeopleMap) {
            addPeopleInMesh(greenPoint, people);
        }
        long endTime2 = System.currentTimeMillis();
        logger.timerInfo("add point in mesh: ", (endTime2 - startTime2));

//        изменение значений итерации
        elementsGUI.getIterAll().setText(String.valueOf(fullDataPeople.healthyPeopleMap.size() + fullDataPeople.illPeopleMap.size()));
        elementsGUI.getIterIll().setText(String.valueOf(fullDataPeople.illPeopleMap.size()));
        elementsGUI.getIterHealthy().setText(String.valueOf(fullDataPeople.healthyPeopleMap.size()));
        future.complete(null);
    }

    private void animationIllPeople() {
        fullDataPeople.illCoordinate.clear();
        ArrayList<People> newIllPeopleMap = new ArrayList<>();
        int n = fullDataPeople.illPeopleMap.size();
        for (int i = 0; i < n; i++) {
            People people = fullDataPeople.illPeopleMap.get(i);
            Coordinate newCoordinate = moving(people.getCoordinate());
            fullDataPeople.illCoordinate.add(newCoordinate);
            newIllPeopleMap.add(new People(people.getId(), newCoordinate, PeopleState.VERY_SICK));
        }
        fullDataPeople.illPeopleMap.clear();
        fullDataPeople.illPeopleMap = newIllPeopleMap;
    }

    private void animationHealthyPeople() {
        int n = fullDataPeople.healthyPeopleMap.size();
        ArrayList<People> newHealthyPeopleMap = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            People people = fullDataPeople.healthyPeopleMap.get(i);
            Coordinate newCoordinate = moving(people.getCoordinate());
            try {
                if (!fullDataPeople.illCoordinate.contains(newCoordinate)) {
                    newHealthyPeopleMap.add(new People(people.getId(), newCoordinate, PeopleState.HEALTHY));
                } else {
                    fullDataPeople.illPeopleMap.add(new People(people.getId(), newCoordinate, PeopleState.VERY_SICK));
                }
            } catch (Throwable e) {
                logger.error("Animation error", e.getMessage());
                throw e;
            }
        }
        fullDataPeople.healthyPeopleMap.clear();
        fullDataPeople.healthyPeopleMap = newHealthyPeopleMap;
    }

    public Coordinate moving(Coordinate position) {
        Coordinate newCoordinate = null;
        Random random = new Random();
        int i = random.nextInt(9) + 1;
        int x = position.getCoordinateX();
        int y = position.getCoordinateY();

        switch (i) {
            case (1) -> newCoordinate = new Coordinate(Math.abs(x - 1), y + 1);
            case (2) -> newCoordinate = new Coordinate(x, y + 1);
            case (3) -> newCoordinate = new Coordinate(x + 1, y + 1);
            case (4) -> newCoordinate = new Coordinate(Math.abs(x - 1), y);
            case (5) -> newCoordinate = new Coordinate(x, y);
            case (6) -> newCoordinate = new Coordinate(x + 1, y);
            case (7) -> newCoordinate = new Coordinate(Math.abs(x - 1), Math.abs(y - 1));
            case (8) -> newCoordinate = new Coordinate(x, Math.abs(y - 1));
            case (9) -> newCoordinate = new Coordinate(x + 1, Math.abs(y - 1));
        }
        if (newCoordinate.getCoordinateX() > elementsGUI.getGridPane().getColumnCount() - 1) {
            newCoordinate.setCoordinateX(newCoordinate.getCoordinateX() - 1);
        }
        if (newCoordinate.getCoordinateY() > elementsGUI.getGridPane().getRowCount() - 1) {
            newCoordinate.setCoordinateY(newCoordinate.getCoordinateY() - 1);
        }
        return newCoordinate;
    }

    public void addPeopleInMesh(Image image, People people) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        elementsGUI.getGridPane().add(imageView, people.getCoordinate().getCoordinateX(), people.getCoordinate().getCoordinateY());
    }

    public void withProbability(Supplier positiveCase, Supplier negativeCase, int probability) {
        SplittableRandom random = new SplittableRandom();
        boolean whoKnows = random.nextInt(1, 101) <= 50;
    }
}
