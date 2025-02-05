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
                addTask(line);
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
     * Appends user input to taskList array
     * @param description Task description
     */
    private static void addTask(String description) {
        System.out.println(LINE_SEPERATOR);
        System.out.println("    added: " + description);
        System.out.println(LINE_SEPERATOR);

        taskList[nTasks++] = new Task(description);
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
                ? "    Your task has been marked as done."
                : "    Ok, your task is marked as not done yet."
        );
        System.out.println("      " + taskList[index]);
        System.out.println(LINE_SEPERATOR);
    }

}
