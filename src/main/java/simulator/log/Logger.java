package simulator.log;

import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class Logger {

    private final String nameClass;

    public Logger(Class clazz) {
        this.nameClass = clazz.getName();
    }

    private void writeFile(String text) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void error(String msg, String error) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Instant.now()).append(" ").append(nameClass).append(" ")
                .append("ERROR: ").append(msg).append(" ").append(error).append(System.lineSeparator());
        writeFile(stringBuilder.toString());

        exceptionDialog("ERROR", msg, error);
    }

    public void info(String msg) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Instant.now()).append(" ").append(nameClass).append(" ")
                .append("INFO: ").append(msg).append(System.lineSeparator());
        writeFile(stringBuilder.toString());
        System.out.println(msg);
    }

    public void timerInfo(String msg, long workTime) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Instant.now()).append(" ").append(nameClass).append(" ")
                .append("TimerINFO: ").append(msg).append(": ").append(workTime).append(System.lineSeparator());
        //writeFile(stringBuilder.toString());
        System.out.println(stringBuilder);
    }

    private void exceptionDialog(String title, String message, String exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(exception);
        alert.showAndWait();
    }
}
