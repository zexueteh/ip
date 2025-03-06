package TARS.logic;

import TARS.command.*;
import TARS.task.*;
import static TARS.command.CommandType.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    protected static CommandType parseCommandType(String line) {
        Matcher matcher = commandTypePattern.matcher(line);
        CommandType commandType = null;
        if (matcher.matches()) {
            String commandTypeString = matcher.group("commandType");
            commandType = fromString(commandTypeString);
        }

        return commandType;
    }

    public static Task parseFileLine(String line) throws TARSParserTaskReadException {
        return parseTask(line, fileLinePattern);
    }

    private static int parseIndex(String line, Pattern pattern) {
        int index = -1;
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            index = Integer.parseInt(matcher.group("index"));
        }
        return index;
    }

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
