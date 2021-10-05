package simulator.epidemic.utils;

import javafx.scene.layout.GridPane;
import simulator.epidemic.objects.InputData;
import simulator.epidemic.objects.Mesh;
import simulator.epidemic.querypool.Query;
import simulator.epidemic.service.MeshService;
import simulator.log.Logger;

public class Animation extends Query<GridPane> {

    private static final Logger logger = new Logger(Animation.class);

    private final GridPane gridPane;

    public Animation(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @Override
    public void prepare(InputData inputData) {
        MeshService meshService = new MeshService(gridPane);
        if (inputData.getMeshSizeX() == 0 || inputData.getMeshSizeY() == 0) {
            throw new NumberFormatException("grid size cannot be 0x0");
        } else if (inputData.getQuantityAllPeople() <= inputData.getQuantityIllPeople()) {
            throw new NumberFormatException("the number of people is less than the number of patients");
        } else {
            Mesh mesh = new Mesh(inputData.getMeshSizeX(), inputData.getMeshSizeY());
            meshService.createMesh(mesh.getSizeX(), mesh.getSizeY());
        }
    }
}
