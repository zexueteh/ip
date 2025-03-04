package TARS.logic;

import TARS.logic.Command;

import TARS.task.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
//    private static final String typeSeperatorRegex = "^(?i)(?:" +
//            "/(?<commandType>todo|deadline|event)" + "|" +
//            "\\[(?<taskType>T|D|E)\\]" + "\\[\\s?(?<status>X?)\\]" +
//            ")" + "\\s+(?<description>.+?)";
    private static final String fileLineRegex = "^\\[(?<taskType>T|D|E)\\]"
            + "\\[\\s?(?<status>X?)\\]"
            + "\\s+(?<description>.+?)"
            + "(?:\\s*\\(by:\\s*(?<by>[^)]+)\\))?"
            + "(?:\\s\\(from:\\s*(?<from>.+?)\\s+to:\\s*(?<to>[^)]+)\\))?";
    private static final Pattern fileLinePattern = Pattern.compile(fileLineRegex);

    private static final String commandRegex = "^(?i)/(?<commandType>todo|deadline|event)"
            + "\\s+(?<description>.+?)"
            + "(?i)(?:\\s+/by\\s+(?<by>.+?))?"
            + "(?i)(?:\\s+/from\\s+(?<from>.+?))?"
            + "(?i)(?:\\s+/to\\s+(?<to>.+?))?";
    private static final Pattern commandPattern = Pattern.compile(commandRegex);

    private final String commandDescriptionRegex = "";


    public static Command parseCommand(String line) throws TARSParserTaskReadException{
        Task newTask =  parseTask(line, commandPattern);
        return null;
    }

    public static Task parseFileLine(String line) throws TARSParserTaskReadException {
        Task newTask = parseTask(line, fileLinePattern);
        return newTask;
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
            return new Todo(taskType, description);
        case DEADLINE:
            return new Deadline(taskType, description, by);
        case EVENT:
            return new Event(taskType, description, from, to);
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
