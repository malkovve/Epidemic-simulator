package simulator.epidemic.utils;

import simulator.epidemic.animation.Animation;
import simulator.epidemic.objects.ApplicationSettings;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.log.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private static final Logger log = new Logger(DataGenerator.class);

    public static List<People> generatePeople(ApplicationSettings applicationSettings) {
        long startTime = System.currentTimeMillis();
        ArrayList<People> peopleList = new ArrayList<>();
        for (int k = 0; k < applicationSettings.getIllPeople(); k++) {
            int i = ThreadLocalRandom.current().nextInt(applicationSettings.getMesh().getSizeX());
            int j = ThreadLocalRandom.current().nextInt(applicationSettings.getMesh().getSizeY());
            People people = new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.INFECTIOUS);
            people.setImageDisplay(Animation.redPoint);
            peopleList.add(people);
        }

        for (int k = 0; k < applicationSettings.getHealthyPeople(); k++) {
            int i = ThreadLocalRandom.current().nextInt(applicationSettings.getMesh().getSizeX());
            int j = ThreadLocalRandom.current().nextInt(applicationSettings.getMesh().getSizeY());
            People people = new People(UUID.randomUUID().toString(), new Coordinate(i, j), PeopleState.SUSCEPTIBLE);
            people.setImageDisplay(Animation.greenPoint);
            peopleList.add(people);
        }
        long endTime = System.currentTimeMillis();
        log.timerInfo("generatePeople: ", (endTime - startTime));
        log.info("DataBase create successful");
        return peopleList;
    }
}
