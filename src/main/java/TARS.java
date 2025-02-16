import java.util.Scanner;

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
    public static final String EMPTY_LINE_MESSAGE = "\tNegative Captain, your command is empty. Awaiting your command... ";


    private static int nTasks = 0;
    private static final int MAX_TASK = 100;
    private static Task[] taskList = new Task[MAX_TASK];

    /**
     * The main entry point into TARS Chatbot Application
     *
     * This method initializes the chatbot, displays a welcome message
     * and maintains a handler loop for user input until "bye" command is entered.
     * Users can add tasks, list tasks, mark tasks as done, unmark tasks
     *
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
                System.out.println("ERROR");
            }

            // to refactor into command handler function once input validation impelemented


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
     * Validation throws exception for empty strings, returns CommandType.INVALID for unknown commands
     * @param line user input String as a command
     * @return first word in line as commandType
     */
    private static CommandType parseCommandType(String line) throws IndexOutOfBoundsException, IllegalArgumentException {
        // Check for empty line
        line = line.trim();
        if (line.isEmpty()) {
            throw new IndexOutOfBoundsException(EMPTY_LINE_MESSAGE);
        }

        // Parse first word for CommandType
        int commandEndIndex = line.indexOf(' ');
        if (commandEndIndex == -1) {
            commandEndIndex = line.length(); // case of single word commands e.g. list with no spaces
        }

        String commandString = line.substring(0, commandEndIndex);
        return CommandType.fromString(commandString);
    }

    private static String parseCommandBody(String line, CommandType commandType)  throws TARSInvalidCommandBodyException {
        String commandBody;
        line = line.trim();

        int commandStartIndex = line.indexOf(' ');
        if (commandStartIndex == -1) {
            commandStartIndex = line.length(); // single word command e.g. LIST
        } else {
            commandStartIndex = commandStartIndex + 1;
        }
        commandBody = line.substring(commandStartIndex);


        switch (commandType) {
        case LIST:
            if (!commandBody.isEmpty()) {
                throw new TARSInvalidCommandBodyException();
            }
            break;

        case MARK:
        case UNMARK:
            int taskNumber;
            try {
                taskNumber = Integer.parseInt(commandBody);
            } catch (NumberFormatException e) {
                throw new TARSInvalidCommandBodyException();
            }
            if (taskNumber > nTasks || taskNumber < 1) {
                throw new TARSInvalidCommandBodyException();
            }
            break;

        case TODO:
            if (commandBody.isEmpty()
                || commandBody.contains("/by")
                || commandBody.contains("/from")
                || commandBody.contains("/to")
            ) {
                throw new TARSInvalidCommandBodyException();
            }
            break;

        case DEADLINE:
            if (commandBody.contains("/from")
            || commandBody.contains("/to")
            ) {
                throw new TARSInvalidCommandBodyException();
            }

            if (!commandBody.contains("/by")) {
                throw new TARSInvalidCommandBodyException();
            }

            int byIndex = commandBody.indexOf("/by");
            String byString = commandBody.substring(byIndex + 3);
            if (byString.isEmpty()) {
                throw new TARSInvalidCommandBodyException();
            }
            break;

        case EVENT:
            // To be implemented
            if (commandBody.contains("/by")) {
                throw new TARSInvalidCommandBodyException();
            }

            if (!commandBody.contains("/from")
                || !commandBody.contains("/to")
            ) {
                throw new TARSInvalidCommandBodyException();
            }

            int fromIndex = commandBody.indexOf("/from");
            int toIndex = commandBody.indexOf("/to");

            if (fromIndex > toIndex) {
                throw new TARSInvalidCommandBodyException();
            }

            String fromString = commandBody.substring(fromIndex + 5, toIndex);
            String toString = commandBody.substring(toIndex + 3);

            if (fromString.isEmpty() || toString.isEmpty()) {
                throw new TARSInvalidCommandBodyException();
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

        taskList[nTasks] = newTask;

        System.out.println(LINE_SEPERATOR);
        System.out.println("\tYes Captain. I've added this task.");
        System.out.println("\t" + taskList[nTasks]);

        nTasks++;
        System.out.println("\tThere are " + nTasks + " tasks in your list.");
        System.out.println(LINE_SEPERATOR);

    }

    /**
     * Formats and prints Tasks objects in taskList
     */
    private static void printTaskList() {
        System.out.println(LINE_SEPERATOR);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < nTasks; i++) {
            System.out.println("    " + (i+1) + "." +taskList[i]);
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
     * @param commandType enum CommandType MARK, UNMARK
     */
    private static void markHandler(int index, CommandType commandType) {
        boolean isMarking = commandType == CommandType.MARK;
        taskList[index].setIsDone(isMarking);

        System.out.println(LINE_SEPERATOR);
        System.out.println(
            isMarking
                ? "\tTask Done. Good job Captain!"
                : "\tOk, your task is marked as not done yet."
        );
        System.out.println("\t" + taskList[index]);
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * takes user input line and parses out Task attributes to return Task Object
     * parsing logic depends on command type
     * WARNING: only basic input validation implemented
     * @param commandBody user input string
     * @param commandType enum CommandType TODO, DEADLINE, EVENT
     * @return Todo, Deadline or Event Object
     */
    private static Task getTask(String commandBody, CommandType commandType) {
        Task newTask = null;
        String description = commandBody;
        String by  = null;
        String from = null;
        String to = null;

        // Eg. event E /from a /to b
        //     commandType = CommandType.EVENT;  commandBody = "E /from a /to b";

        switch (commandType) {
        case DEADLINE: // Handle Deadline command containing /by
            int byIndex = commandBody.indexOf("/by");
            by = commandBody.substring(byIndex + 3).trim();
            description = commandBody.substring(0, byIndex).trim();
            break;

        case EVENT: // Handle Event command containing /from /to
            int fromIndex = commandBody.indexOf("/from");
            int toIndex = commandBody.indexOf("/to");

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

