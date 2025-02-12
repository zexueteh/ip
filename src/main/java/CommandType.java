public enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT;
    private static final String UNKNOWN_COMMAND_MESSAGE = " is an unknown command. I am unable to comply, Captain.";

    public static CommandType fromString(String command) throws IllegalArgumentException {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Modifying exception message
            throw new IllegalArgumentException(command + UNKNOWN_COMMAND_MESSAGE);
        }
    }
}
