package simulator.epidemic.utils;

import simulator.epidemic.SimulatorController;
import simulator.epidemic.exception.SimulatorEpidemicExceptionBuilder;
import simulator.epidemic.exception.SimulatorException;
import simulator.log.Logger;

public class ObjectValidator {

    private static final Logger log = new Logger(ObjectValidator.class);

    public static void validateInputData(
            String meshSize,
            String quantityPeople,
            String illPeople
    ) throws SimulatorException {
        String[] strMesh = meshSize.split("x");
        if (strMesh.length != 2) {
            throw SimulatorEpidemicExceptionBuilder.buildIncorrectlyGrid("example mesh input: NxN");
        }

        if (quantityPeople.length() == 0 || illPeople.length() == 0) {
            throw SimulatorEpidemicExceptionBuilder.buildIncorrectlyPopulation("population cannot be 0");
        }

        try {
            int meshSizeX = strMesh[0].length() != 0 ? Integer.parseInt(strMesh[0]) : 0;
            int meshSizeY = strMesh[1].length() != 0 ? Integer.parseInt(strMesh[1]) : 0;
            int quantityAllPeople = Integer.parseInt(quantityPeople);
            int quantityIllPeople = Integer.parseInt(illPeople);

            if (meshSizeX == 0 || meshSizeY == 0) {
                throw new NumberFormatException("grid size cannot be 0x0");
            }

            if (quantityAllPeople <= quantityIllPeople) {
                throw new NumberFormatException("the number of people is less than the number of patients");
            }

            SimulatorController.applicationSettings.getMesh().setSizeX(meshSizeX);
            SimulatorController.applicationSettings.getMesh().setSizeY(meshSizeY);
            SimulatorController.applicationSettings.setAllPeople(quantityAllPeople);
            SimulatorController.applicationSettings.setIllPeople(quantityIllPeople);
            log.info("Validate input data complete SUCCESSFUL");
        } catch (Exception e) {
            throw SimulatorEpidemicExceptionBuilder.buildAttributeIncompatibleType(e);
        }
    }
}
