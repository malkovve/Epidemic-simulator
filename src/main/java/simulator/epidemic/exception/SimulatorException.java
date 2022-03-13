package simulator.epidemic.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SimulatorException extends Exception {

    private final String code;
    private final Map<String, Object> parameters;
    private final String comment;

    SimulatorException(String code, String comment, Map<String, Object> parameters) {
        this(code, comment, parameters, null);
    }

    SimulatorException(String code, String comment, Map<String, Object> parameters, Throwable cause) {
        super(
                buildMessage(code, parameters, comment),
                cause
        );

        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException();
        }

        this.code = code;
        this.parameters = parameters == null ? null : Collections.unmodifiableMap(parameters);
        this.comment = comment;
    }


    public String getCode() {
        return code;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public String getComment() {
        return comment;
    }

    private static String buildMessage(String code, Map<String, Object> parameters, String comment) {
        StringJoiner builder = new StringJoiner(", ");
        if (comment != null) builder.add(comment);
        builder.add("code=" + code);
        if (parameters != null) builder.add("parameters=" + parameters);
        return builder.toString();
    }

    public static boolean equals(SimulatorException e1, SimulatorException e2) {
        if (e1 == e2) {
            return true;
        } else if (e1 == null || e2 == null) {
            return false;
        }

        if (!e1.getCode().equals(e2.getCode())) {
            return false;
        }

        if (!Objects.equals(e1.getComment(), e2.getComment())) {
            return false;
        }

        if (!Objects.equals(e1.getParameters(), e2.getParameters())) {
            return false;
        }

        return Objects.equals(e1.getCause(), e2.getCause());
    }
}
