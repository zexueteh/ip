package TARS.logic;

import java.util.regex.Pattern;

import static TARS.command.CommandType.*;

public class RegexConstants {
    private static final String FILE_LINE_REGEX = "^\\[(?<taskType>[TDE])]"
            + "\\[\\s?(?<status>X?)]"
            + "\\s+(?<description>.+?)"
            + "(?:\\s*\\(by:\\s*(?<by>[^)]+)\\))?"
            + "(?:\\s\\(from:\\s*(?<from>.+?)\\s+to:\\s*(?<to>[^)]+)\\))?";
    static final Pattern FILE_LINE_PATTERN = Pattern.compile(FILE_LINE_REGEX);

    private static final String COMMAND_TYPE_REGEX = "^/(?<commandType>\\S+)(?:\\s+(?<arguments>.+))?$";
    protected static final Pattern COMMAND_TYPE_PATTERN = Pattern.compile(COMMAND_TYPE_REGEX);

    private static final String INDEX_COMMAND_REGEX = "^(?i)/(?<taskType>\\S+)" + "(?:\\s+(?<index>.+?)$)?";
    protected static final Pattern INDEX_COMMAND_PATTERN = Pattern.compile(INDEX_COMMAND_REGEX);

    private static final String COMMAND_REGEX = "^(?i)/(?<taskType>\\S+)"
           // + "(?:\\s+(?<index>.+)$)?"
            + "(?:\\s+(?<description>.+?))?"
            + "(?i)(?:\\s+/by\\s+(?<by>.+?))?"
            + "(?i)(?:\\s+/from\\s+(?<from>.+?))?"
            + "(?i)(?:\\s+/to\\s+(?<to>.+?))?";
    protected static final Pattern COMMAND_PATTERN = Pattern.compile(COMMAND_REGEX);

    static final String VALIDATE_BYE_LIST_HELP_REGEX = String.format(
            "(?i)^/(?<commandType>%s|%s|%s)$",
            BYE, LIST, HELP);
    private static final String VALIDATE_MARK_UNMARK_DELETE_REGEX = String.format(
            "(?i)^/(?<commandType>%s|%s|%s)(?:\\s+(?<index>.+))?$",
            MARK, UNMARK, DELETE);
    static final Pattern VALIDATE_MARK_UNMARK_DELETE_PATTERN = Pattern.compile(VALIDATE_MARK_UNMARK_DELETE_REGEX);

    private static final String DESCRIPTION_REGEX = "(?i)^/%s(?:\\s+(?<description>.+?))?";
    private static final String VALIDATE_EVENT_REGEX = String.format(DESCRIPTION_REGEX
            + "\\s+/from(?:\\s+(?<from>.+?))?"
            + "\\s+/to(?:\\s+(?<to>.+?))?", EVENT);
    static final Pattern VALIDATE_EVENT_PATTERN = Pattern.compile(VALIDATE_EVENT_REGEX);

    private static final String VALIDATE_DEADLINE_REGEX = String.format(DESCRIPTION_REGEX
            + "\\s+/by(?:\\s+(?<deadline>.+?))?$", DEADLINE);
    static final Pattern VALIDATE_DEADLINE_PATTERN = Pattern.compile(VALIDATE_DEADLINE_REGEX);

    private static final String VALIDATE_TODO_REGEX = String.format(DESCRIPTION_REGEX, TODO);
    static final Pattern VALIDATE_TODO_PATTERN = Pattern.compile(VALIDATE_TODO_REGEX);

    private static final String VALIDATE_FIND_REGEX = String.format(DESCRIPTION_REGEX, FIND).replace("description", "searchTerm");
    static final Pattern VALIDATE_FIND_PATTERN = Pattern.compile(VALIDATE_FIND_REGEX);

    private static final String DATE_REGEX = "\\b\\d{4}-\\d{2}-\\d{2}\\b";
    static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);

    private static final String TIME_REGEX = "\\b\\d{2}:\\d{2}\\b";
    static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);

}
