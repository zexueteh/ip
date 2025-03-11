package TARS.command;

/**
 * Represents the different types of commands that can be executed by the chatbot.
 */
public enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, HELP, INVALID;

    /**
     * Converts a string command into its corresponding {@code CommandType}.
     *
     * @param command The command string.
     * @return The corresponding {@code CommandType}, or {@code INVALID} if not recognized.
     */
    public static CommandType fromString(String command) {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.INVALID;
        }
    }

    /**
     * Returns the string representation of the command type in lowercase.
     *
     * @return The command type as a lowercase string.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
