package TARS.logic;

import TARS.command.CommandType;
import java.util.regex.Matcher;

/**
 * Validates user input commands before parsing
 */
public class Validator extends Parser {
    /**
     * Validates the user input command before parsing.
     *
     * @param input The user command.
     * @throws TARSInvalidCommandType If the command type is invalid.
     * @throws TARSInvalidCommandParam If command parameters are incorrect.
     */
    public static void validate(String input) throws
            TARSInvalidCommandType, TARSInvalidCommandParam {
        validateCommandType(input);
        CommandType type = parseCommandType(input);
        validateCommandParams(input, type);
    }

    private static void validateCommandType(String input) throws TARSInvalidCommandType {
        Matcher matcher = RegexConstants.COMMAND_TYPE_PATTERN.matcher(input);

        if (matcher.matches()) {
            String typeString = matcher.group("commandType").trim();
            CommandType type = CommandType.fromString(typeString);
            if (type == CommandType.INVALID) {
                throw new TARSInvalidCommandType("\"" + typeString + "\" is an invalid command.");
            }
        } else {
            throw new TARSInvalidCommandType("no commandType found");
        }
    }

    private static void validateCommandParams(String input, CommandType type) throws TARSInvalidCommandParam {
        switch (type) {
        case BYE, LIST, HELP:
            validateByeListHelpCommand(input, type);
            break;
        case MARK, UNMARK, DELETE:
            validateMarkUnmarkDeleteCommand(input, type);
            break;
        case TODO:
            validateTodoCommand(input, type);
            break;
        case DEADLINE:
            validateDeadlineCommand(input, type);
            break;
        case EVENT:
            validateEventCommand(input, type);
            break;
        case FIND:
            validateFindCommand(input, type);
            break;
        }
    }

    private static void validateByeListHelpCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        if (!input.matches(RegexConstants.VALIDATE_BYE_LIST_HELP_REGEX)) {
            throw new TARSInvalidCommandParam("/" + type + " command has no parameters.");
        }
    }

    private static void validateMarkUnmarkDeleteCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        Matcher matcher = RegexConstants.VALIDATE_MARK_UNMARK_DELETE_PATTERN.matcher(input);
        if (matcher.matches()) {
            String index = matcher.group("index");
            if (index == null) {
                throw new TARSInvalidCommandParam("/" + type + " command requires a task index.");
            } else {
                try {
                    Integer.parseInt(index);
                } catch (NumberFormatException e) {
                    throw new TARSInvalidCommandParam("Index: \"" + index + "\" is invalid. /"
                            + type + " command requires an integer index.");
                }
            }
        }

    }

    private static void validateTodoCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        Matcher matcher = RegexConstants.VALIDATE_TODO_PATTERN.matcher(input);
        String[] params = {"description"};
        validateObjectParams(type, params, matcher);
    }

    private static void validateDeadlineCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        Matcher matcher = RegexConstants.VALIDATE_DEADLINE_PATTERN.matcher(input);
        String[] params = {"description","deadline"};
        validateObjectParams(type, params, matcher);
    }
    private static void validateEventCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        Matcher matcher = RegexConstants.VALIDATE_EVENT_PATTERN.matcher(input);
        String[] params = {"description","from","to"};
        validateObjectParams(type, params, matcher);
    }

    private static void validateFindCommand(String input, CommandType type)
            throws TARSInvalidCommandParam {
        Matcher matcher = RegexConstants.VALIDATE_FIND_PATTERN.matcher(input);
        String[] params = {"searchTerm"};
        validateObjectParams(type, params, matcher);
    }

    private static void validateObjectParams(CommandType type, String[] params, Matcher matcher)
            throws TARSInvalidCommandParam {
        if (matcher.matches()) {
            for (String param : params) {
                String arg = matcher.group(param);

                if (arg == null || arg.trim().isEmpty()) {
                    throw new TARSInvalidCommandParam("/" + type + " command requires a " + param + " parameter.");
                } else if (arg.contains("/")) {
                    throw new TARSInvalidCommandParam(param + " cannot contain other flags.");
                }
            }
        } else {
            throw new TARSInvalidCommandParam("/" + type + " command requires "
                    + String.join(", ", params) + " parameters.");
        }
    }

    /**
     * Validates that the index parameter is within a valid range.
     *
     * @param numberTasks The total number of tasks.
     * @param index The index to validate.
     * @throws TARSInvalidCommandParam If the index is out of range.
     */
    public static void validateIndexParam(int numberTasks, int index) throws TARSInvalidCommandParam {
        if (numberTasks <= 0) {
            throw new TARSInvalidCommandParam("Index: " + index + " is invalid. Task List is empty.");
        } else if (index <= 0) {
            throw new TARSInvalidCommandParam("Index: " + index + " has to be greater than 0.");
        } else if (index > numberTasks) {
            throw new TARSInvalidCommandParam("Index: " + index
                    + " has to be less than or equal to " + numberTasks + ".");
        }
    }

    public static class TARSInvalidCommandType extends Exception {
        public TARSInvalidCommandType(String message) {
            super("Invalid Command Type: " + message);
        }
    }

    public static class TARSInvalidCommandParam extends Exception {
        public TARSInvalidCommandParam(String message) {
            super("Invalid Command Parameters: " + message);
        }
    }
}
