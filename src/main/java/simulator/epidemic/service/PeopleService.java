package simulator.epidemic.service;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import simulator.epidemic.objects.Coordinate;
import simulator.epidemic.objects.DataTransaction;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PeopleService {

    public static final Image greenPoint = new Image("images/greenPoint.png");
    public static final Image redPoint = new Image("images/redPoint.jpg");
    private final GridPane gridPane;

    public PeopleService(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public DataTransaction prepare(int quantityIllPeople, int quantityHealthyPeople) {
        ArrayList<People> illPeopleMap = new ArrayList<>();
        ArrayList<People> healthyPeopleMap = new ArrayList<>();
        ArrayList<Coordinate> illCoordinate = new ArrayList<>();

        Random random = new Random();
        for (int k = 0; k < quantityIllPeople; k++) {
            int i = random.nextInt(gridPane.getColumnCount());
            int j = random.nextInt(gridPane.getRowCount());
            addPeopleInMesh(redPoint, i, j);
            illPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.VERY_SICK));
            illCoordinate.add(new Coordinate(i, j));
        }
        for (int k = 0; k < quantityHealthyPeople; k++) {
            int i = random.nextInt(gridPane.getColumnCount());
            int j = random.nextInt(gridPane.getRowCount());
            addPeopleInMesh(greenPoint, i, j);
            healthyPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.HEALTHY));
        }
        return new DataTransaction(illPeopleMap, healthyPeopleMap, illCoordinate);
    }

    /*public void createPeople(int quantityIllPeople, int quantityHealthyPeople) {
        Random random = new Random();
        for (int k = 1; k <= quantityIllPeople; k++) {
            int i = random.nextInt(gridPane.getColumnCount());
            int j = random.nextInt(gridPane.getRowCount());
            addPeopleInMesh(redPoint, i, j);
            illPeopleMap.put(k, new People(new Coordinate(i, j), PeopleState.VERY_SICK));
            illCoordinate.put(new Coordinate(i, j), true);
        }
        for (int k = 1; k <= quantityHealthyPeople; k++) {
            int i = random.nextInt(gridPane.getColumnCount());
            int j = random.nextInt(gridPane.getRowCount());
            addPeopleInMesh(greenPoint, i, j);
            healthyPeopleMap.put(k, new People(new Coordinate(i, j), PeopleState.HEALTHY));
        }
    }*/

    public void addPeopleInMesh(Image image, int coordinateX, int coordinateY) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        gridPane.add(imageView, coordinateX, coordinateY);
    }
}
