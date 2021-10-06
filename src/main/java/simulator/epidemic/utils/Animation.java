package simulator.epidemic.utils;

import javafx.scene.layout.GridPane;
import simulator.epidemic.objects.*;
import simulator.epidemic.querypool.Query;
import simulator.epidemic.service.MeshService;
import simulator.epidemic.service.PeopleService;
import simulator.log.Logger;

import java.util.ArrayList;
import java.util.Random;

public class Animation extends Query<GridPane> {

    private static final Logger logger = new Logger(Animation.class);

    private DataTransaction fullDataPeople;
    private final GridPane gridPane;
    private PeopleService peopleService;

    public Animation(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @Override
    public void prepare(InputData inputData) {
        MeshService meshService = new MeshService(gridPane);
        peopleService = new PeopleService(gridPane);
        if (inputData.getMeshSizeX() == 0 || inputData.getMeshSizeY() == 0) {
            throw new NumberFormatException("grid size cannot be 0x0");
        } else if (inputData.getQuantityAllPeople() <= inputData.getQuantityIllPeople()) {
            throw new NumberFormatException("the number of people is less than the number of patients");
        } else {
            Mesh mesh = new Mesh(inputData.getMeshSizeX(), inputData.getMeshSizeY());
            meshService.createMesh(mesh.getSizeX(), mesh.getSizeY());
            fullDataPeople = peopleService.prepare(inputData.getQuantityIllPeople(), inputData.getQuantityHealthyPeople());
        }
    }

    public void animationAlg() {
        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        animationHealthyPeople();
        animationIllPeople();
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
            peopleService.addPeopleInMesh(PeopleService.redPoint, newCoordinate.getCoordinateX(), newCoordinate.getCoordinateY());
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
                    peopleService.addPeopleInMesh(PeopleService.greenPoint, newCoordinate.getCoordinateX(), newCoordinate.getCoordinateY());
                } else {
                    fullDataPeople.illPeopleMap.add(new People(people.getId(), newCoordinate, PeopleState.VERY_SICK));
                    peopleService.addPeopleInMesh(PeopleService.redPoint, newCoordinate.getCoordinateX(), newCoordinate.getCoordinateY());
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
            case (1) -> newCoordinate = new Coordinate(Math.abs(x - 1), Math.abs(y - 1));
            case (2) -> newCoordinate = new Coordinate(x, Math.abs(y - 1));
            case (3) -> newCoordinate = new Coordinate(x + 1, Math.abs(y - 1));
            case (4) -> newCoordinate = new Coordinate(Math.abs(x - 1), y);
            case (5) -> newCoordinate = new Coordinate(x, y);
            case (6) -> newCoordinate = new Coordinate(x + 1, y);
            case (7) -> newCoordinate = new Coordinate(Math.abs(x - 1), y + 1);
            case (8) -> newCoordinate = new Coordinate(x, y + 1);
            case (9) -> newCoordinate = new Coordinate(x + 1, y + 1);
        }
        if (newCoordinate.getCoordinateX() > gridPane.getColumnCount() - 1 && newCoordinate.getCoordinateY() > gridPane.getRowCount() - 1) {
            newCoordinate = new Coordinate(Math.abs(x - 1), Math.abs(y - 1));
        } else if (newCoordinate.getCoordinateY() > gridPane.getRowCount() - 1) {
            newCoordinate = new Coordinate(Math.abs(x - 1), Math.abs(y - 1));
        } else if (newCoordinate.getCoordinateX() > gridPane.getColumnCount()) {
            newCoordinate = new Coordinate(Math.abs(x - 1), Math.abs(y - 1));
        }
        return newCoordinate;
    }
}
