package TARS.task;

import TARS.logic.CommandType;

public enum TaskType {
    TODO, DEADLINE, EVENT, INVALID;

    public static TaskType fromString(String command) throws IllegalArgumentException {
        switch (command) {
        case "T":
            return TaskType.TODO;
        case "D":
            return TaskType.DEADLINE;
        case "E":
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
