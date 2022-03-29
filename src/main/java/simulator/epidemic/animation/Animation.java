package simulator.epidemic.animation;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import simulator.epidemic.objects.IterationResult;
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

    public static final Image greenPoint = new Image("images/green.png");
    public static final Image redPoint = new Image("images/red.png");

    private final GridPane gridPane;
    private final IterationResult iterationResult;
    private List<People> peopleList;
    private int iter;
    private float count;

    private static final AtomicReference<CompletableFuture<Void>> FUTURE_REFERENCE = new AtomicReference<>(null);

    private ScheduledExecutorService scheduledExecutorService;

    public Animation(GridPane gridPane, IterationResult iterationResult, List<People> peopleList) {
        this.gridPane = gridPane;
        this.peopleList = peopleList;
        this.iterationResult = iterationResult;
        iter = 0;
    }

    public void prepareMesh(int sizeX, int sizeY) {
        createMesh(sizeX, sizeY);
    }

    public void preparePeople() {
        displayPeople(peopleList);
    }

    public void start() {

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = () -> {
            FUTURE_REFERENCE.set(new CompletableFuture<>());
            FUTURE_REFERENCE.get().whenComplete((aVoid, throwable) -> {
                Platform.runLater(() -> {
                    if (throwable == null) {
                        XYChart.Series<String, Float> series = new XYChart.Series<>();
                        series.getData().add(new XYChart.Data<>(String.valueOf(iter), count));
                        iterationResult.getLineChart().getData().add(series);

                        gridPane.setGridLinesVisible(false);
                        gridPane.getChildren().clear();
                        displayPeople(peopleList);
                        gridPane.setGridLinesVisible(true);
                    } else {
                        // обработчик ошибок
                    }
                });
            });
            try {
                iter++;
                Map<Coordinate, List<People>> groupingPeople = peopleList.stream().collect(Collectors.groupingBy(People::getCoordinate));
                long l = System.currentTimeMillis();
                CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm(groupingPeople);
                ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
                peopleList = forkJoinPool.invoke(calculationAlgorithm);
                long l1 = System.currentTimeMillis();
                System.out.println("ForkJoin " + (l1 - l));
                FUTURE_REFERENCE.getAndSet(null).complete(null);
                iterationResult.getIterAll().setText(String.valueOf(peopleList.size()));
                count = peopleList.stream().filter(people -> people.getState().equals(PeopleState.VERY_SICK.getState())).count();
                iterationResult.getIterIll().setText(String.valueOf((int) count));



            } catch (Throwable e) {
                FUTURE_REFERENCE.getAndSet(null).completeExceptionally(e);
                stop();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 2000L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    //  Children: duplicate children added: parent = Grid hgap=0.0, vgap=0.0, alignment=TOP_LEFT
    private void displayPeople(List<People> peopleList) {
        for (People people : peopleList) {
            gridPane.add(people.getImageDisplay(), people.getCoordinate().getCoordinateX(), people.getCoordinate().getCoordinateY());
        }
    }

    private void createMesh(int sizeX, int sizeY) {
        removeMesh();
        for (int i = 0; i < sizeX; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5));
        }
        for (int i = 0; i < sizeY; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(5));
        }
    }

    private void removeMesh() {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
    }
}
