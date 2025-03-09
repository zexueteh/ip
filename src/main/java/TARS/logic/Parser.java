package TARS.logic;

import TARS.command.*;
import TARS.task.*;
import static TARS.command.CommandType.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles parsing of user input and file data.
 */
public class Parser {
    private static final String fileLineRegex = "^\\[(?<taskType>T|D|E)\\]"
            + "\\[\\s?(?<status>X?)\\]"
            + "\\s+(?<description>.+?)"
            + "(?:\\s*\\(by:\\s*(?<by>[^)]+)\\))?"
            + "(?:\\s\\(from:\\s*(?<from>.+?)\\s+to:\\s*(?<to>[^)]+)\\))?";
    private static final Pattern fileLinePattern = Pattern.compile(fileLineRegex);

    private static final String commandTypeRegex = "^/(?<commandType>\\S+)(?:\\s+(?<arguments>.+))?$";
    protected static final Pattern commandTypePattern = Pattern.compile(commandTypeRegex);

    private static final String commandRegex = "^(?i)/(?<taskType>\\S+)"
            + "(?:\\s+(?<index>\\d+)$)?"
            + "(?:\\s+(?<description>.+?))?"
            + "(?i)(?:\\s+/by\\s+(?<by>.+?))?"
            + "(?i)(?:\\s+/from\\s+(?<from>.+?))?"
            + "(?i)(?:\\s+/to\\s+(?<to>.+?))?";

    protected static final Pattern commandPattern = Pattern.compile(commandRegex);

    /**
     * Parses a user command and returns the corresponding {@code Command} object.
     *
     * @param line The user input string.
     * @return A {@code Command} object representing the parsed command.
     * @throws TARSParserTaskReadException If parsing fails when reading from storage file.
     * @throws Validator.TARSInvalidCommandType If an invalid command type is provided by user input.
     * @throws Validator.TARSInvalidCommandParam If command parameters in user input are invalid.
     */
    public static Command parseCommand(String line)
            throws TARSParserTaskReadException,
            Validator.TARSInvalidCommandType,
            Validator.TARSInvalidCommandParam {
        Validator.validate(line);
        CommandType commandType = parseCommandType(line);

        Matcher commandMatcher = commandPattern.matcher(line);
        switch (commandType) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case MARK,UNMARK:
            return new MarkCommand(parseIndex(line,commandPattern), commandType == MARK);
        case TODO, DEADLINE, EVENT:
            Task newTask = parseTask(line, commandPattern);
            return new AddCommand(newTask);
        case DELETE:
            return new DeleteCommand(parseIndex(line,commandPattern));
        }
        return null;
    }

    /**
     * Parses the command type from a given user input string.
     *
     * @param line The input string.
     * @return The corresponding {@code CommandType}.
     */
    protected static CommandType parseCommandType(String line) {
        Matcher matcher = commandTypePattern.matcher(line);
        CommandType commandType = null;
        if (matcher.matches()) {
            String commandTypeString = matcher.group("commandType");
            commandType = fromString(commandTypeString);
        }
        return commandType;
    }

    /**
     * Parses a task from a file line.
     *
     * @param line A line from the stored file.
     * @return The parsed {@code Task} object.
     * @throws TARSParserTaskReadException If parsing fails.
     */
    public static Task parseFileLine(String line) throws TARSParserTaskReadException {
        return parseTask(line, fileLinePattern);
    }

    /**
     * Extracts the task index from a user input string.
     *
     * @param line The input string.
     * @param pattern The regex pattern used for extraction.
     * @return The extracted index.
     */
    private static int parseIndex(String line, Pattern pattern) {
        int index = -1;
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            index = Integer.parseInt(matcher.group("index"));
        }
        return index;
    }

    /**
     * Parses a task from a given input string.
     *
     * @param line The input string.
     * @param pattern The regex pattern used for parsing.
     * @return A {@code Task} object representing the parsed task.
     * @throws TARSParserTaskReadException If parsing fails.
     */
    private static Task parseTask(String line, Pattern pattern) throws TARSParserTaskReadException {
        TaskType taskType;
        boolean isDone;
        String description, by, to, from;
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            taskType = TaskType.fromString(matcher.group("taskType"));
            if (pattern.pattern().contains("?<status>")) {
                isDone = matcher.group("status").equals("X");
            } else {
                isDone = false;
            }
            description = matcher.group("description");
            by = matcher.group("by");
            from = matcher.group("from");
            to = matcher.group("to");

        } else {
            throw new TARSParserTaskReadException("File Load Error: Unable to parse task type.");
        }


        switch (taskType) {
        case TODO:
            return new Todo(taskType, description, isDone);
        case DEADLINE:
            return new Deadline(taskType, description, isDone, by);
        case EVENT:
            return new Event(taskType, description, isDone,  from, to);
        default:
            return null;
        }
    }

    public static class TARSParserTaskReadException extends Exception {
        public TARSParserTaskReadException(String message) {
            super(message);
        }
    }

}
