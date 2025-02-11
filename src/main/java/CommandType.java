public enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, INVALID;

    public static CommandType fromString(String command) {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }

    }
}
