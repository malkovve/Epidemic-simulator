package simulator.epidemic.exception;

public class SimulatorEpidemicExceptionBuilder {

    private static final ExceptionFactory EXCEPTION_FACTORY = new SimulatorExceptionFactory();

    private SimulatorEpidemicExceptionBuilder() {}

    public static final class ErrorCode {
        public static final String ATTRIBUTE_INCOMPATIBLE_TYPE = "attribute_incompatible_type";
    }

    public static SimulatorException buildIncorrectlyPopulation(String comment) {
        return EXCEPTION_FACTORY.build("incorrectly_size_population", comment);
    }

    public static SimulatorException buildIncorrectlyInputData(String comment) {
        return EXCEPTION_FACTORY.build("incorrectly_input_data", comment);

    }

    public static SimulatorException buildIncorrectlyGrid(String comment) {
        return EXCEPTION_FACTORY.build("incorrectly_grid_size", comment);
    }

    public static SimulatorException buildAttributeIncompatibleType(Exception e) {
        return EXCEPTION_FACTORY.build(ErrorCode.ATTRIBUTE_INCOMPATIBLE_TYPE, e);
    }

    public static SimulatorException notFoundPeopleState(String comment) {
        return EXCEPTION_FACTORY.build("not_found_people_state", comment);
    }
}
