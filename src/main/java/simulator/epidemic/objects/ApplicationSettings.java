package simulator.epidemic.objects;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import simulator.epidemic.objects.animation.Mesh;

public class ApplicationSettings {

    private final Mesh mesh;

    private final IterationResult iterationResult;

    private int allPeople;

    private int illPeople;

    private float probabilityInfection;

    private Long applicationSpeed;

    private final XYChart.Series<String, Float> lineChart;

    public ApplicationSettings(Mesh mesh, IterationResult iterationResult, XYChart.Series<String, Float> lineChart) {
        this.mesh = mesh;
        this.iterationResult = iterationResult;
        this.lineChart = lineChart;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public IterationResult getIterationResult() {
        return iterationResult;
    }

    public int getAllPeople() {
        return allPeople;
    }

    public int getIllPeople() {
        return illPeople;
    }

    public int getHealthyPeople() {
        return allPeople - illPeople;
    }

    public float getProbabilityInfection() {
        return probabilityInfection;
    }

    public Long getApplicationSpeed() {
        return applicationSpeed;
    }

    public XYChart.Series<String, Float> getLineChart() {
        return lineChart;
    }

    public void setAllPeople(int allPeople) {
        this.allPeople = allPeople;
    }

    public void setIllPeople(int illPeople) {
        this.illPeople = illPeople;
    }

    public void setProbabilityInfection(float probabilityInfection) {
        this.probabilityInfection = probabilityInfection;
    }

    public void setApplicationSpeed(long applicationSpeed) {
        this.applicationSpeed = applicationSpeed;
    }
}
