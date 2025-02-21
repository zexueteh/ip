package TARS.command;

public enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE;

    public static CommandType fromString(String command) throws IllegalArgumentException {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Modifying exception message
            throw new IllegalArgumentException("Invalid Command: " + command + ". Command not recognized. Use 'help' for valid commands.");
        }
    }
}
