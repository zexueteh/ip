import java.util.Scanner;

public class TARS {
    private static final String LINE_SEPERATOR = "    ____________________________________________________________";
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
            if (line.equals("bye")) {
                break;
            } else if (line.equals("list")) {
                printTaskList();
            } else if (line.startsWith("mark") || line.startsWith("unmark")) {
                int index = parseMarkCommand(line);
                boolean isMarking = line.startsWith("mark");
                markHandler(index, isMarking);

            } else {
                Task newTask = taskParser(line);
                addTask(newTask);

            }
        }

        printGoodbyeMessage();
    }

    /**
     * Prints the hello message to welcome the user.
     */
    private static void printHelloMessage() {
        System.out.println(LINE_SEPERATOR);
        System.out.println("      _____  _    ____  ____  ");
        System.out.println("     |_   _|/ \\  |  _ \\/ ___| ");
        System.out.println("       | | / _ \\ | |_) \\___ \\ ");
        System.out.println("       | |/ ___ \\|  _ < ___) |");
        System.out.println("       |_/_/   \\_\\_| \\_\\____/ ");
        System.out.println("                              ");
        System.out.println("    Hello, I'm TARS, your friendly chatbot assistant. ");
        System.out.println("    How can I help you today?");
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * Prints the goodbye message before exiting.
     */
    private static void printGoodbyeMessage() {
        System.out.println(LINE_SEPERATOR);
        System.out.println("    Goodbye and Goodnight! ");
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * Appends new Task object to taskList array
     * @param newTask Task Object
     */
    private static void addTask(Task newTask) {

        taskList[nTasks] = newTask;

        System.out.println(LINE_SEPERATOR);
        System.out.println("    Yes Captain. I've added this task.");
        System.out.println("      " + taskList[nTasks]);

        nTasks++;
        System.out.println("    There are " + nTasks + " tasks in your list.");
        System.out.println(LINE_SEPERATOR);

    }

    /**
     * Formats and prints Tasks objects in taskList
     */
    private static void printTaskList() {
        System.out.println(LINE_SEPERATOR);
        System.out.println("    Here are the tasks in your list:");
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
                ? "    Task Done. Good job Captain!"
                : "    Ok, your task is marked as not done yet."
        );
        System.out.println("      " + taskList[index]);
        System.out.println(LINE_SEPERATOR);
    }

    /**
     * takes user input line and parses out Task attributes to return Task Object
     * parsing logic depends on command type
     * WARNING: only basic input validation implemented
     * @param line user input String
     * @return Todo, Deadline or Event Object
     */
    private static Task taskParser(String line) {
        Task newTask = null;
        String description = null;
        String by  = null;
        String from = null;
        String to = null;

        // Eg. event E /from a /to b
        //     commandType = "event";  commandBody = "E /from a /to b";
        String commandType = line.substring(0, line.indexOf(" "));
        String commandBody = line.substring(line.indexOf(" ") + 1);


        if (!commandType.isEmpty() && (commandType.equals("todo") || commandType.equals("deadline") || commandType.equals("event"))) {

            description = commandBody;
            // Handle /by
            int byIndex = commandBody.indexOf("/by");
            if (byIndex != -1) {
                by = commandBody.substring(byIndex + 3).trim();
                description = commandBody.substring(0, byIndex).trim();
            }

            int fromIndex = commandBody.indexOf("/from");
            int toIndex = commandBody.indexOf("/to");

            if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex) {
                from = description.substring(fromIndex + 5, toIndex).trim();
                to = description.substring(toIndex + 3).trim();
                description = description.substring(0, fromIndex).trim();
            }

            switch (commandType) {
            case "todo":
                newTask = new Todo(description);
                break;
            case "deadline":
                newTask = new Deadline(description, by);
                break;
            case "event":
                newTask = new Event(description, to, from);
                break;
            }
        }
        return newTask;
    }

}
