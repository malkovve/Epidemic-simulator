package simulator.epidemic.utils;

import simulator.epidemic.SimulatorController;
import simulator.epidemic.animation.Animation;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CalculationAlgorithm extends RecursiveTask<List<People>> {

    private final Map<Coordinate, List<People>> groupingPeople;

    public CalculationAlgorithm(Map<Coordinate, List<People>> groupingPeople) {
        this.groupingPeople = groupingPeople;
    }

    @Override
    protected List<People> compute() {
        if (groupingPeople.size() <= 10000) {
            return peopleAlg(groupingPeople);
        }
        Map<Coordinate, List<People>> firstCoordinateListHashMap = new HashMap<>();
        Map<Coordinate, List<People>> secondCoordinateListHashMap = new HashMap<>();
        boolean b = false;
        for (Map.Entry<Coordinate, List<People>> e: groupingPeople.entrySet()) {
            if (b)
                firstCoordinateListHashMap.put(e.getKey(), e.getValue());
            else
                secondCoordinateListHashMap.put(e.getKey(), e.getValue());
            b = !b;
        }
        List<People> peopleList = new ArrayList<>();
        CalculationAlgorithm firstHalfArrayValueSumCounter = new CalculationAlgorithm(firstCoordinateListHashMap);
        CalculationAlgorithm secondHalfArrayValueSumCounter = new CalculationAlgorithm(secondCoordinateListHashMap);
        invokeAll(
                firstHalfArrayValueSumCounter,
                secondHalfArrayValueSumCounter
        );
        peopleList.addAll(firstHalfArrayValueSumCounter.join());
        peopleList.addAll(secondHalfArrayValueSumCounter.join());
        return peopleList;
    }

    private List<People> peopleAlg(Map<Coordinate, List<People>> listPeople) {
//        Map<Coordinate, List<People>> newCoordinateListPeopleMap = new HashMap<>();
        List<People> newPeopleList = new ArrayList<>();
        for (Map.Entry<Coordinate, List<People>> e: listPeople.entrySet()) {
            Coordinate coordinate = e.getKey();
            List<People> peopleList = e.getValue();
            Map<String, List<People>> collect = peopleList.stream().collect(Collectors.groupingBy(People::getState));
            for (Map.Entry<String, List<People>> stateGrouping: collect.entrySet()) {
                if (collect.size() == 1) {
                    for(People people: stateGrouping.getValue()) {
                        people.setCoordinate(moving(coordinate));
                        newPeopleList.add(people);
                    }
                } else if (stateGrouping.getKey().equals(PeopleState.HEALTHY.getState())) {
                    for(People people: stateGrouping.getValue()) {
                        boolean isIll = ThreadLocalRandom.current().nextInt(100) > 50;
                        if (isIll) {
                            people.setState(PeopleState.VERY_SICK);
                            people.setImageDisplay(Animation.redPoint);
                        }
                        people.setCoordinate(moving(coordinate));
                        newPeopleList.add(people);
                    }
                } else if (stateGrouping.getKey().equals(PeopleState.VERY_SICK.getState())) {
                    for(People people: stateGrouping.getValue()) {
                        people.setCoordinate(moving(coordinate));
                        newPeopleList.add(people);
                    }
                }
            }
        }

        return newPeopleList;
    }

    public Coordinate moving(Coordinate coordinate) {
        Coordinate newCoordinate = null;
        Random random = new Random();
        int i = random.nextInt(9) + 1;
        int x = coordinate.getCoordinateX();
        int y = coordinate.getCoordinateY();

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
        if (newCoordinate.getCoordinateX() > SimulatorController.settingsAnimation.getMeshSizeY() - 1) {
            newCoordinate.setCoordinateX(newCoordinate.getCoordinateX() - 1);
        }
        if (newCoordinate.getCoordinateY() > SimulatorController.settingsAnimation.getMeshSizeY() - 1) {
            newCoordinate.setCoordinateY(newCoordinate.getCoordinateY() - 1);
        }
        return newCoordinate;
    }
}
