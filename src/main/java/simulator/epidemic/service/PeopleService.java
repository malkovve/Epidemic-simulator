package simulator.epidemic.service;

import simulator.epidemic.objects.DataTransaction;
import simulator.epidemic.objects.ElementsGUI;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.epidemic.utils.Animation;
import simulator.log.Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PeopleService {

    private static final Logger logger = new Logger(PeopleService.class);

    private final ElementsGUI elementsGUI;

    public PeopleService(ElementsGUI elementsGUI) {
        this.elementsGUI = elementsGUI;
    }

    public DataTransaction prepare(int quantityIllPeople, int quantityHealthyPeople) {
        long startTime = System.currentTimeMillis();
        ArrayList<People> illPeopleMap = new ArrayList<>();
        ArrayList<People> healthyPeopleMap = new ArrayList<>();
        ArrayList<Coordinate> illCoordinate = new ArrayList<>();
        //todo Рассмотреть возмоность многопоточного ввода данных
//        int rand = ThreadLocalRandom.current().nextInt(1,7);
        Random random = new Random();
        for (int k = 0; k < quantityIllPeople; k++) {
            int i = random.nextInt(elementsGUI.getGridPane().getColumnCount());
            int j = random.nextInt(elementsGUI.getGridPane().getRowCount());
            illPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.VERY_SICK));
            illCoordinate.add(new Coordinate(i, j));
        }
        for (int k = 0; k < quantityHealthyPeople; k++) {
            int i = random.nextInt(elementsGUI.getGridPane().getColumnCount());
            int j = random.nextInt(elementsGUI.getGridPane().getRowCount());
            healthyPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.HEALTHY));
        }
        long endTime = System.currentTimeMillis();
        logger.timerInfo("iter: ", (endTime - startTime));
        return new DataTransaction(illPeopleMap, healthyPeopleMap, illCoordinate);
    }
}
