package simulator.epidemic.service;

import simulator.epidemic.objects.IterationResult;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.log.Logger;

import java.util.*;

public class PeopleService {

    private static final Logger logger = new Logger(PeopleService.class);

    private final IterationResult iterationResult;

    public PeopleService(IterationResult iterationResult) {
        this.iterationResult = iterationResult;
    }

//    public ListData prepare(int quantityIllPeople, int quantityHealthyPeople) {
//        long startTime = System.currentTimeMillis();
//        ArrayList<People> illPeopleMap = new ArrayList<>();
//        ArrayList<People> healthyPeopleMap = new ArrayList<>();
//        ArrayList<Coordinate> illCoordinate = new ArrayList<>();
//        HashMap<Coordinate, HashSet<Long>> add = new HashMap<>();
//        //todo Рассмотреть возмоность многопоточного ввода данных
////        int rand = ThreadLocalRandom.current().nextInt(1,7);
//        Random random = new Random();
//        for (int k = 0; k < quantityIllPeople; k++) {
//            int i = random.nextInt(iterationResult.getGridPane().getColumnCount());
//            int j = random.nextInt(iterationResult.getGridPane().getRowCount());
//            illPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.VERY_SICK));
//            illCoordinate.add(new Coordinate(i, j));
//        }
//        for (int k = 0; k < quantityHealthyPeople; k++) {
//            int i = random.nextInt(iterationResult.getGridPane().getColumnCount());
//            int j = random.nextInt(iterationResult.getGridPane().getRowCount());
//            healthyPeopleMap.add(new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.HEALTHY));
//        }
//        long endTime = System.currentTimeMillis();
//        logger.timerInfo("iter: ", (endTime - startTime));
//        return new ListData(illPeopleMap, healthyPeopleMap, illCoordinate);
//    }
}
