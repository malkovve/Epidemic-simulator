package simulator.epidemic.exception;

import java.util.Map;

public class SimulatorExceptionFactory extends ExceptionFactory {

    public SimulatorExceptionFactory() {

    }

    @Override
    public SimulatorException build(String code, String comment, Map<String, Object> parameters, Throwable cause) {
        if (cause != null) {
            return new SimulatorException(code, comment, parameters, cause);
        }
        return new SimulatorException(code, comment, parameters);
    }
}
