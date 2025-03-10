package TARS.logic;

import TARS.command.*;

import TARS.task.Task;
import TARS.task.Todo;
import TARS.task.Deadline;
import TARS.task.Event;
import TARS.task.TaskType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

    public static Command parseCommand(String line)
            throws TARSParserTaskReadException,
            Validator.TARSInvalidCommandType,
            Validator.TARSInvalidCommandParam {
        Validator.validate(line);
        CommandType commandType = parseCommandType(line);

        switch (commandType) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case MARK,UNMARK:
            return new MarkCommand(parseIndex(line), commandType == CommandType.MARK);
        case TODO, DEADLINE, EVENT:
            Task newTask = parseTask(line, RegexConstants.COMMAND_PATTERN);
            return new AddCommand(newTask);
        case DELETE:
            return new DeleteCommand(parseIndex(line));
        case FIND:
            String searchTerm = parseSearchTerm(line);
            return new FindCommand(searchTerm);
        }
        return null;
    }

    protected static CommandType parseCommandType(String line) {
        Matcher matcher = RegexConstants.COMMAND_TYPE_PATTERN.matcher(line);
        CommandType commandType = null;
        if (matcher.matches()) {
            String commandTypeString = matcher.group("commandType");
            commandType = CommandType.fromString(commandTypeString);
        }

        return commandType;
    }

    public static Task parseFileLine(String line) throws TARSParserTaskReadException {
        return parseTask(line, RegexConstants.FILE_LINE_PATTERN);
    }

    private static int parseIndex(String line) {
        int index = -1;
        Matcher matcher = RegexConstants.COMMAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            index = Integer.parseInt(matcher.group("index"));
        }
        return index;
    }

    private static String parseSearchTerm(String line) {
        String searchTerm = "";
        Matcher matcher = RegexConstants.COMMAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            searchTerm = matcher.group("description");
        }
        return searchTerm;
    }

    private static Task parseTask(String line, Pattern pattern) throws TARSParserTaskReadException {
        TaskType taskType;
        boolean isDone;
        String description, by, to, from;
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            taskType = TaskType.fromString(matcher.group("taskType"));
            description = matcher.group("description");
            by = matcher.group("by");
            from = matcher.group("from");
            to = matcher.group("to");

            if (pattern == RegexConstants.COMMAND_PATTERN) {
                isDone = false;
                by = DateTimeParser.parseParam(by);
                from = DateTimeParser.parseParam(from);
                to = DateTimeParser.parseParam(to);
            } else {
                isDone = matcher.group("status").equals("X");
            }


        } else {
            throw new TARSParserTaskReadException("File Load Error: Unable to parse task type.");
        }


        switch (taskType) {
        case TODO:
            return new Todo(taskType, description, isDone);
        case DEADLINE:
            return new Deadline(taskType, description, isDone, by);
        case EVENT:
            return new Event(taskType, description, isDone, from, to);
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