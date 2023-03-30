package simulator.epidemic.animation;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import simulator.epidemic.SimulatorController;
import simulator.epidemic.objects.People;
import simulator.epidemic.objects.PeopleState;
import simulator.epidemic.objects.animation.Coordinate;
import simulator.epidemic.utils.CalculationAlgorithm;
import simulator.log.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Animation {

    private static final Logger log = new Logger(Animation.class);

    public static final Image greenPoint = new Image("images/greenPoint.png");
    public static final Image redPoint = new Image("images/red.png");

    private static final AtomicReference<CompletableFuture<Void>> FUTURE_REFERENCE = new AtomicReference<>(null);
    public static volatile boolean isAnimation = false;
    private ScheduledExecutorService scheduledExecutorService;

    private List<People> peopleList;
    private int iter;

    public Animation(List<People> peopleList) {
        this.peopleList = peopleList;
    }

    public void prepareMesh(int sizeX, int sizeY) {
        createMesh(sizeX, sizeY);
    }

    public void preparePeople() {
        iter = 0;
        SimulatorController.applicationSettings.getLineChart().getData().add(
                new XYChart.Data<>(String.valueOf(iter),
                        (float) SimulatorController.applicationSettings.getIllPeople())
        );
        displayPeople(peopleList);
    }

    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> {
            FUTURE_REFERENCE.set(new CompletableFuture<>());
            FUTURE_REFERENCE.get().whenComplete((aVoid, throwable) -> {
                Platform.runLater(() -> {
                    if (throwable == null) {
                        if (iter == 605) {
                            SimulatorController.applicationSettings.getLineChart().getData().add(
                                    new XYChart.Data<>(String.valueOf(iter),  Float.parseFloat(SimulatorController.applicationSettings.getIterationResult().getIterIll().getText()))
                            );
                            stop();
                        } else if ((iter % 10) == 0){
                            SimulatorController.applicationSettings.getLineChart().getData().add(
                                    new XYChart.Data<>(String.valueOf(iter),  Float.parseFloat(SimulatorController.applicationSettings.getIterationResult().getIterIll().getText()))
                            );
                            SimulatorController.applicationSettings.getMesh().getGridPane().setGridLinesVisible(false);
                            SimulatorController.applicationSettings.getMesh().getGridPane().getChildren().clear();
                            displayPeople(peopleList);
                            SimulatorController.applicationSettings.getMesh().getGridPane().setGridLinesVisible(true);
                        }
                    } else {
                        // обработчик ошибок
                    }
                });
            });
            try {
                iter++;
                Map<Coordinate, List<People>> groupingPeople = peopleList.stream().collect(Collectors.groupingBy(People::getCoordinate));
                CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm(groupingPeople);
                ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
                peopleList = forkJoinPool.invoke(calculationAlgorithm);
                FUTURE_REFERENCE.getAndSet(null).complete(null);
                SimulatorController.applicationSettings.getIterationResult().getIterAll().setText(String.valueOf(peopleList.size()));
                long count = peopleList.stream().filter(people -> people.getState().equals(PeopleState.INFECTIOUS.getState())).count();
                SimulatorController.applicationSettings.getIterationResult().getIterIll().setText(String.valueOf((int) count));
                SimulatorController.applicationSettings.getIterationResult().getIteration().setText(String.valueOf(iter));


            } catch (Throwable e) {
                FUTURE_REFERENCE.getAndSet(null).completeExceptionally(e);
                stop();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    //  Children: duplicate children added: parent = Grid hgap=0.0, vgap=0.0, alignment=TOP_LEFT
    private void displayPeople(List<People> peopleList) {
        for (People people : peopleList) {
            SimulatorController.applicationSettings.getMesh().getGridPane().add(people.getImageDisplay(), people.getCoordinate().getCoordinateX(), people.getCoordinate().getCoordinateY());
        }
    }

    private void createMesh(int sizeX, int sizeY) {
        removeMesh();
        SimulatorController.applicationSettings.getMesh().getGridPane().getStyleClass().add("game-grid");
        for (int i = 0; i < sizeX; i++) {
            SimulatorController.applicationSettings.getMesh().getGridPane().getColumnConstraints().add(new ColumnConstraints(5));
        }
        for (int i = 0; i < sizeY; i++) {
            SimulatorController.applicationSettings.getMesh().getGridPane().getRowConstraints().add(new RowConstraints(5));
        }
    }

    private void removeMesh() {
        SimulatorController.applicationSettings.getMesh().getGridPane().setGridLinesVisible(false);
        SimulatorController.applicationSettings.getMesh().getGridPane().getColumnConstraints().clear();
        SimulatorController.applicationSettings.getMesh().getGridPane().getRowConstraints().clear();
        SimulatorController.applicationSettings.getMesh().getGridPane().getChildren().clear();
        SimulatorController.applicationSettings.getMesh().getGridPane().setGridLinesVisible(true);
    }
}
