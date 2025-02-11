import java.util.Scanner;

public class TARS {
    private static final String LINE_SEPERATOR = "\t____________________________________________________________";
    private static final String WELCOME_MESSAGE = """
            \t  _____  _    ____  ____
            \t |_   _|/ \\  |  _ \\/ ___|
            \t   | | / _ \\ | |_) \\___ \\
            \t   | |/ ___ \\|  _ < ___) |
            \t   |_/_/   \\_\\_| \\_\\____/
            \tHello, I'm TARS, your friendly chatbot assistant.
            \tHow can I help you today?""";
    public static final String GOODBYE_MESSAGE = "\tGoodnight Captain. Sleep well.";
    public static final String EMPTY_LINE_MESSAGE = "\tNegative Captain, your command is empty. Awaiting your command... ";
    public static final String UNKNOWN_COMMAND_MESSAGE = "\tCaptain, I cannot comply. Unknown command detected.";

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
            CommandType commandType = parseCommandType(line);


            if (commandType == CommandType.BYE) {
                break;
            }

            switch (commandType) {
            case LIST:
                printTaskList();
                break;
            case MARK:
            case UNMARK:
                int index = parseMarkCommand(line);
                boolean isMarking = line.startsWith("mark");
                markHandler(index, isMarking);
                break;

            case TODO:
            case EVENT:
            case DEADLINE:
                Task newTask = taskParser(line, commandType);
                if (newTask != null) {
                    addTask(newTask);
                }
                break;

            case INVALID:
            default:
                System.out.println(LINE_SEPERATOR);
                System.out.println(UNKNOWN_COMMAND_MESSAGE);
                System.out.println(LINE_SEPERATOR);
            }
        }
        printGoodbyeMessage();
        sc.close();
    }

    /**
     * Prints the hello message to welcome the user.
     */
    private static void printHelloMessage() {
        System.out.println(LINE_SEPERATOR);
        System.out.println(WELCOME_MESSAGE);
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * Prints the goodbye message before exiting.
     */
    private static void printGoodbyeMessage() {
        System.out.println(LINE_SEPERATOR);
        System.out.println(GOODBYE_MESSAGE);
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * Parses user input line for commandType
     * @param line user input String as a command
     * @return first word in line as commandType
     */
    private static CommandType parseCommandType(String line) {
        try {
            // Check for empty line
            line = line.trim();
            if (line.isEmpty()) {
                throw new IndexOutOfBoundsException();
            }

            int commandEndIndex = line.indexOf(' ');
            if (commandEndIndex == -1) {
                commandEndIndex = line.length(); // case of single word commands e.g. list with no spaces
            }

            String commandString = line.substring(0, commandEndIndex);
            return CommandType.fromString(commandString);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE_SEPERATOR);
            System.out.println(EMPTY_LINE_MESSAGE);
            System.out.println(LINE_SEPERATOR);
            return CommandType.INVALID;
        }

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
     * @param isMarking differentiates mark/unmark
     */
    private static void markHandler(int index, boolean isMarking) {
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
     * @param line user input String as a command
     * @param commandType first word in line representing type of command
     * @return Todo, Deadline or Event Object
     */
    private static Task taskParser(String line, CommandType commandType) {
        Task newTask = null;
        String description = null;
        String by  = null;
        String from = null;
        String to = null;

        // Eg. event E /from a /to b
        //     commandType = "event";  commandBody = "E /from a /to b";
        String commandBody = line.substring(line.indexOf(" ") + 1);

        // Parsing the line String
        description = commandBody;

        // Handle Deadline command containing /by
        int byIndex = commandBody.indexOf("/by");
        if (byIndex != -1) {
            by = commandBody.substring(byIndex + 3).trim();
            description = commandBody.substring(0, byIndex).trim();
        }

        // Handle Event command containing /from /to
        int fromIndex = commandBody.indexOf("/from");
        int toIndex = commandBody.indexOf("/to");

        if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex) {
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
            newTask = new Event(description, to, from);
            break;
        }
        return newTask;
    }

}
