package TARS.ui;
import TARS.task.Task;
import TARS.task.Todo;
import TARS.task.Deadline;
import TARS.task.Event;
import TARS.command.CommandType;
import TARS.command.TARSInvalidCommandBodyException;



import java.util.Scanner;
import java.util.ArrayList;

public class TARS {
    private static final String LINE_SEPERATOR = "\t____________________________________________________________";
    private static final String HELLO_MESSAGE = """
            \t  _____  _    ____  ____
            \t |_   _|/ \\  |  _ \\/ ___|
            \t   | | / _ \\ | |_) \\___ \\
            \t   | |/ ___ \\|  _ < ___) |
            \t   |_/_/   \\_\\_| \\_\\____/
            \tHello, I'm TARS, your friendly chatbot assistant.
            \tHow can I help you today?""";
    public static final String GOODBYE_MESSAGE = "\tGoodnight Captain. Sleep well.";


    private static ArrayList<Task> taskList = new ArrayList<Task>();

    /**
     * The main entry point into TARS Chatbot Application

     * This method initializes the chatbot, displays a welcome message
     * and maintains a handler loop for user input until "bye" command is entered.
     * Users can add tasks, list tasks, mark tasks as done, unmark tasks

     * Commands supported
     * - Add Task: Any text input added as task (Exceptions below)
     * - List Tasks: command "list"
     * - Marking Tasks as done: command "mark {task_number}"
     * - Marking Tasks as undone: command "unmark {task_number}"
     * - Exit: command "bye"
     *
     * @param args String buffer
     */
    public static void main(String[] args) {
        printHelloMessage();
        String line;
        Scanner sc = new Scanner(System.in);

        while (true) {
            line = sc.nextLine().strip();

            try {
                CommandType commandType = parseCommandType(line);

                if (commandType == CommandType.BYE) {
                    break;
                }

                String commandBody = parseCommandBody(line, commandType);
                commandHandler(commandType, commandBody);


            } catch (Exception e) {
                printResponseMessage(e.getMessage());
            }

        }

        printGoodbyeMessage();
        sc.close();
    }

    private static void printResponseMessage(String message) {
        System.out.println(LINE_SEPERATOR);
        System.out.println(message);
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * Prints the hello message to welcome the user.
     */
    private static void printHelloMessage() {
        printResponseMessage(HELLO_MESSAGE);
    }

    /**
     * Prints the goodbye message before exiting.
     */
    private static void printGoodbyeMessage() {
        printResponseMessage(GOODBYE_MESSAGE);
    }

    /**
     * Parses user input line for commandType
     * Validation throws exception for empty strings, returns TARS.command.CommandType.INVALID for unknown commands
     * @param line user input String as a command
     * @return first word in line as commandType
     */
    private static CommandType parseCommandType(String line) throws IndexOutOfBoundsException, IllegalArgumentException {
        // Check for empty line
        line = line.trim();
        if (line.isEmpty()) {
            throw new IndexOutOfBoundsException("Invalid Command: . Command cannot be empty. Use 'help' for valid commands.");
        }

        // Parse first word for TARS.command.CommandType
        int commandEndIndex = line.indexOf(' ');
        if (commandEndIndex == -1) {
            commandEndIndex = line.length(); // case of single word commands e.g. list with no spaces
        }

        String commandString = line.substring(0, commandEndIndex);
        return CommandType.fromString(commandString);
    }


    /**
     * parses commandBody for arguments from the user input after extracting command type
     * Validates the format of command body based on command type
     * Validation Rules
     * LIST: must not contain any arguments
     * MARK / UNMARK: must be a valid 1-indexed integer representing task index.
     * TODO: arguments must not contain any flags
     * DEADLINE: must only contain /by flag followed by due date
     * EVENT: must only contain /from and /to flags in order, each followed by start and end times
     * @param line full user input String
     * @param commandType enum TARS.command.CommandType extracted from user input String
     * @return validated commandBody String
     * @throws TARSInvalidCommandBodyException if the commandBody does not meet validation criteria
     */
    private static String parseCommandBody(String line, CommandType commandType)  throws TARSInvalidCommandBodyException {
        String commandBody;
        String description;

        line = line.trim();

        int commandStartIndex = line.indexOf(' ');
        if (commandStartIndex == -1) {
            commandStartIndex = line.length(); // single word command e.g. LIST
        } else {
            commandStartIndex = commandStartIndex + 1;
        }
        commandBody = line.substring(commandStartIndex);

        boolean hasBy = commandBody.toLowerCase().contains("by");
        boolean hasFrom = commandBody.toLowerCase().contains("from");
        boolean hasTo = commandBody.toLowerCase().contains("to");

        switch (commandType) {
        case LIST:
            if (!commandBody.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType LIST has no arguments.");
            }
            break;

        case MARK:
        case UNMARK:
            int taskNumber;
            try {
                taskNumber = Integer.parseInt(commandBody);
            } catch (NumberFormatException e) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: " + commandBody + ". Task number must be an integer.");
            }
            if (taskNumber > taskList.size() || taskNumber < 1) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: " + taskNumber + ". Task number is out of range");
            }
            break;

        case TODO:
            if (commandBody.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType TODO has empty description.");
            }

            if (hasBy || hasFrom || hasTo
            ) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: " +
                        (hasBy ? "/by ": "") +
                        (hasFrom ? "/from ": "") +
                        (hasTo ? "/to ": "") +
                ". TARS.command.CommandType TODO has no flags."
                );
            }
            break;

        case DEADLINE:
            if (!hasBy) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: TARS.command.CommandType DEADLINE must contain /by flag.");
            }
            if (hasFrom || hasTo ) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: " +
                        (hasFrom ? "/from ": "") +
                        (hasTo ? "/to ": "") +
                        ". TARS.command.CommandType DEADLINE only has /by flag.");
            }

            int byIndex = commandBody.toLowerCase().indexOf("/by");
            description = commandBody.substring(0, byIndex).trim();
            String byString = commandBody.substring(byIndex + 3).trim();

            if (description.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType DEADLINE has empty description.");
            }
            if (byString.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType DEADLINE has empty deadline.");
            }
            break;

        case EVENT:
            if (!hasFrom || !hasTo) {
                throw new TARSInvalidCommandBodyException(
                        "Invalid Flags: TARS.command.CommandType EVENT must contain /from and /to flags."
                );
            }

            if (hasBy) {
                throw new TARSInvalidCommandBodyException(
                        "Invalid Flags: /by . TARS.command.CommandType EVENT only has /from and /to flags.");
            }

            int fromIndex = commandBody.toLowerCase().indexOf("/from");
            int toIndex = commandBody.toLowerCase().indexOf("/to");

            if (fromIndex > toIndex) {
                throw new TARSInvalidCommandBodyException(
                        "Invalid Flags: flags /from and /to must be in that order."
                );
            }

            description = commandBody.substring(0, fromIndex).trim();
            String fromString = commandBody.substring(fromIndex + 5, toIndex).trim();
            String toString = commandBody.substring(toIndex + 3).trim();

            if (description.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType EVENT has empty description.");
            }

            if (fromString.isEmpty() || toString.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.command.CommandType EVENT has empty start/end time.");
            }

            break;
        default:
            break;
        }
        return commandBody;
    }


    /**
     * Appends new Task object to taskList array
     * @param newTask Task Object
     */
    private static void addTask(Task newTask) {

        taskList.add(newTask);

        System.out.println(LINE_SEPERATOR);
        System.out.println("\tYes Captain. I've added this task.");
        System.out.println("\t" + taskList.get(taskList.size() - 1));

        System.out.println("\tThere are " + taskList.size() + " tasks in your list.");
        System.out.println(LINE_SEPERATOR);

    }

    /**
     * Formats and prints Tasks objects in taskList
     */
    private static void printTaskList() {
        System.out.println(LINE_SEPERATOR);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("    " + (i+1) + "." + taskList.get(i));
        }
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * parses line in the case of mark/unmark command to extract Task index
     * @param line command read by scanner
     * @return 0-indexed integer corresponding to Task in taskList
     */
    private static int parseMarkCommand(String line) {
        int seperatorIndex = line.indexOf(" ") + 1;
        String indexString = line.substring(seperatorIndex);

        return Integer.parseInt(indexString) - 1;
    }

    /**
     * Marks/Unmarks task in taskList
     * @param index index of Task in taskList
     * @param commandType enum TARS.command.CommandType MARK, UNMARK
     */
    private static void markHandler(int index, CommandType commandType) {
        boolean isMarking = commandType == CommandType.MARK;
        taskList.get(index).setIsDone(isMarking);

        System.out.println(LINE_SEPERATOR);
        System.out.println(
            isMarking
                ? "\tTask Done. Good job Captain!"
                : "\tOk, your task is marked as not done yet."
        );
        System.out.println("\t" + taskList.get(index));
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * takes user input line and parses out Task attributes to return Task Object
     * parsing logic depends on command type
     * WARNING: only basic input validation implemented
     * @param commandBody user input String
     * @param commandType enum TARS.command.CommandType TODO, DEADLINE, EVENT
     * @return TARS.task.Todo, TARS.task.Deadline or TARS.task.Event Object
     */
    private static Task getTask(String commandBody, CommandType commandType) {
        Task newTask = null;
        String description = commandBody;
        String by  = null;
        String from = null;
        String to = null;

        // E.g. event E /from a /to b
        //     commandType = TARS.command.CommandType.EVENT;  commandBody = "E /from a /to b";

        switch (commandType) {
        case DEADLINE: // Handle TARS.task.Deadline command containing /by
            int byIndex = commandBody.toLowerCase().indexOf("/by");
            by = commandBody.substring(byIndex + 3).trim();
            description = commandBody.substring(0, byIndex).trim();
            break;

        case EVENT: // Handle TARS.task.Event command containing /from /to
            int fromIndex = commandBody.toLowerCase().indexOf("/from");
            int toIndex = commandBody.toLowerCase().indexOf("/to");

            from = description.substring(fromIndex + 5, toIndex).trim();
            to = description.substring(toIndex + 3).trim();
            description = description.substring(0, fromIndex).trim();
        }

        // Return Task object based on commandType
        switch (commandType) {
        case TODO:
            newTask = new Todo(description);
            break;
        case DEADLINE:
            newTask = new Deadline(description, by);
            break;
        case EVENT:
            newTask = new Event(description, from, to);
            break;
        }
        return newTask;
    }

    /**
     * Executes command based on commandType and commandBody. Execution logic is extracted to sub-methods
     * @param commandType enum TARS.command.CommandType
     * @param commandBody user input String
     */
    private static void commandHandler (CommandType commandType, String commandBody) {
        switch (commandType) {
        case LIST:
            printTaskList();
            break;

        case MARK:
        case UNMARK:
            int index = parseMarkCommand(commandBody);
            markHandler(index, commandType);
            break;

        case TODO:
        case EVENT:
        case DEADLINE:
            Task newTask = getTask(commandBody, commandType);
            addTask(newTask);
            break;

        default:
            break;

        }
    }
}

