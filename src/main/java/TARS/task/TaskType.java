package TARS.task;

import java.util.Locale;

public enum TaskType {
    TODO, DEADLINE, EVENT, INVALID;

    public static TaskType fromString(String command) throws IllegalArgumentException {
        switch (command.toUpperCase()) {
        case "T", "TODO":
            return TaskType.TODO;
        case "D", "DEADLINE":
            return TaskType.DEADLINE;
        case "E", "EVENT":
            return TaskType.EVENT;
        default:
            return TaskType.INVALID;
        }
    }

    @Override
    public String toString() {
        switch (this) {
        case TODO:
            return "T";
        case DEADLINE:
            return "D";
        case EVENT:
            return "E";
        default:
            return "INVALID";
        }
    }

}
