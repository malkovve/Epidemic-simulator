package simulator.epidemic.utils;

import simulator.epidemic.animation.Animation;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.epidemic.objects.animation.InputData;
import simulator.log.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private static final Logger log = new Logger(DataGenerator.class);

    public static List<People> generatePeople(InputData inputData) {
        long startTime = System.currentTimeMillis();
        ArrayList<People> peopleList = new ArrayList<>();
        for (int k = 0; k < inputData.getIllPeople(); k++) {
            int i = ThreadLocalRandom.current().nextInt(inputData.getMeshSizeX());
            int j = ThreadLocalRandom.current().nextInt(inputData.getMeshSizeY());
            People people = new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.VERY_SICK);
            people.setImageDisplay(Animation.redPoint);
            peopleList.add(people);
        }

        for (int k = 0; k < inputData.getHealthyPeople(); k++) {
            int i = ThreadLocalRandom.current().nextInt(inputData.getMeshSizeX());
            int j = ThreadLocalRandom.current().nextInt(inputData.getMeshSizeY());
            People people = new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.HEALTHY);
            people.setImageDisplay(Animation.greenPoint);
            peopleList.add(people);
        }
        long endTime = System.currentTimeMillis();
        log.timerInfo("generatePeople: ", (endTime - startTime));
        log.info("DataBase create successful");
        return peopleList;
    }
}
