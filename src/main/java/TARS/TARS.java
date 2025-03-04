package TARS;
import TARS.logic.Command;
import TARS.logic.Parser;
import TARS.task.Task;
import TARS.task.Todo;
import TARS.task.Deadline;
import TARS.task.Event;
import TARS.logic.CommandType;
import TARS.command.TARSInvalidCommandBodyException;

import TARS.util.Ui;
import TARS.util.Storage;

import TARS.task.TaskList;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class TARS {
    private static final String LINE_SEPERATOR = "\t____________________________________________________________";

    private static final ArrayList<Task> taskList = new ArrayList<Task>();

    private static final String dataFolderPath = System.getProperty("user.dir") + "/data";
    private static final String dataFilePath = dataFolderPath + "/tasks.txt";

    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    public TARS(String dataFolderPath, String dataFilePath) {
        ui = Ui.getInstance();

        try {
            storage = Storage.getInstance(dataFolderPath, dataFilePath);
            tasks = new TaskList(storage.load());

        } catch (Storage.TARSStorageFilePathException|
                 Storage.TARSStorageOperationException|
                Parser.TARSParserTaskReadException e) {
            ui.printResponseMessage(e.getMessage());
        }

    }


    public void run() {
        ui.printHelloMessage();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readScannerInput();
                //ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parseCommand(fullCommand);
                //c.execute(tasks, ui, storage);
                //isExit = c.isExit();
            } catch (Exception e) {
                //ui.showError(e.getMessage());
            } finally {
                //ui.showLine();
            }
        }
    }



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
        new TARS(dataFolderPath, dataFilePath).run();
//        ArrayList<String> fileData = loadFile();
//        initializeTaskList(fileData);
//
//        String line;
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            line = sc.nextLine().strip();
//
//            try {
//                CommandType commandType = parseCommandType(line);
//
//                if (commandType == CommandType.BYE) {
//                    break;
//                }
//
//                String commandBody = parseCommandBody(line, commandType);
//                commandHandler(commandType, commandBody);
//                writeFile();
//
//            } catch (Exception e) {
//                Ui.printResponseMessage(e.getMessage());
//            }
//
//        }
//
//        printGoodbyeMessage();
//        sc.close();
    }



    /**
     * Parses user input line for commandType
     * Validation throws exception for empty strings, returns TARS.logic.CommandType.INVALID for unknown commands
     * @param line user input String as a command
     * @return first word in line as commandType
     */
    private static CommandType parseCommandType(String line) throws IndexOutOfBoundsException, IllegalArgumentException {
        // Check for empty line
        line = line.trim();
        if (line.isEmpty()) {
            throw new IndexOutOfBoundsException("Invalid Command: . Command cannot be empty. Use 'help' for valid commands.");
        }

        // Parse first word for TARS.logic.CommandType
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
     * @param commandType enum TARS.logic.CommandType extracted from user input String
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

        boolean hasBy = commandBody.toLowerCase().contains("/by");
        boolean hasFrom = commandBody.toLowerCase().contains("/from");
        boolean hasTo = commandBody.toLowerCase().contains("/to");

        switch (commandType) {
        case LIST:
            if (!commandBody.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType LIST has no arguments.");
            }
            break;

        case MARK:
        case UNMARK:
        case DELETE:
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
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType TODO has empty description.");
            }

            if (hasBy || hasFrom || hasTo
            ) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: " +
                        (hasBy ? "/by ": "") +
                        (hasFrom ? "/from ": "") +
                        (hasTo ? "/to ": "") +
                ". TARS.logic.CommandType TODO has no flags."
                );
            }
            break;

        case DEADLINE:
            if (!hasBy) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: TARS.logic.CommandType DEADLINE must contain /by flag.");
            }
            if (hasFrom || hasTo ) {
                throw new TARSInvalidCommandBodyException("Invalid Flags: " +
                        (hasFrom ? "/from ": "") +
                        (hasTo ? "/to ": "") +
                        ". TARS.logic.CommandType DEADLINE only has /by flag.");
            }

            int byIndex = commandBody.toLowerCase().indexOf("/by");
            description = commandBody.substring(0, byIndex).trim();
            String byString = commandBody.substring(byIndex + 3).trim();

            if (description.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType DEADLINE has empty description.");
            }
            if (byString.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType DEADLINE has empty deadline.");
            }
            break;

        case EVENT:
            if (!hasFrom || !hasTo) {
                throw new TARSInvalidCommandBodyException(
                        "Invalid Flags: TARS.logic.CommandType EVENT must contain /from and /to flags."
                );
            }

            if (hasBy) {
                throw new TARSInvalidCommandBodyException(
                        "Invalid Flags: /by . TARS.logic.CommandType EVENT only has /from and /to flags.");
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
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType EVENT has empty description.");
            }

            if (fromString.isEmpty() || toString.isEmpty()) {
                throw new TARSInvalidCommandBodyException("Invalid Argument: TARS.logic.CommandType EVENT has empty start/end time.");
            }

            break;
        default:
            break;
        }
        return commandBody;
    }



    /**
     * Reads task data from hardcoded file path, processes each line and stores in array
     *
     * Exception handling: FileNotFoundException if data file is missing/corrupted
     * @return array of raw task data strings
     */

    private static ArrayList<String> loadFile() {
        ArrayList<String> rawTaskData = new ArrayList<String>();
        File dataFile = new File(dataFilePath);

        String nextLine;

        try {
            Scanner fileScanner = new Scanner(dataFile);

            while (fileScanner.hasNextLine()) {
                nextLine = fileScanner.nextLine().trim();
                if (nextLine.endsWith(")")) {
                    nextLine = nextLine.substring(0, nextLine.length() - 1);
                }

                rawTaskData.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            // file does not exist at the start
            System.out.println("FileNotFoundException: " + e.getMessage());
        }
        return rawTaskData;
    }

    /**
     * saves current taskList contents to a file
     * writes the string representation of each Task object to a new line
     *
     * Error Handling: If IOException occurs, prints an error message
     */
    private static void writeFile() {
        StringBuilder writeBuffer = new StringBuilder();


        for (int i = 0; i < taskList.size(); i++) {
            writeBuffer.append(taskList.get(i).toString()).append("\n");
        }
        try {
            FileWriter dataWriter = new FileWriter(dataFilePath);
            dataWriter.write(writeBuffer.toString());
            dataWriter.close();
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());

        }

    }

    /**
     * Initializes task list from previously stored data
     * @param args Array of raw task data strings from storage file
     */

//    private static void initializeTaskList(ArrayList<String> args) {
//
//        for (int i = 0; i < args.size(); i++) {
//            String lineData = args.get(i);
//            CommandType commandType = parseLineTaskType(lineData);
//            Task newTask = getTask(lineData.substring(7), commandType, "(by:", "(from:", "to:");
//            boolean isDone = parseLineIsDone(lineData);
//            newTask.setIsDone(isDone);
//
//            taskList.add(newTask);
//
//        }
//    }

    /**
     * parses line data from data.txt file to determine CommandType
     * @param lineData raw task data string
     * @return CommandType enum Todo, DeadLine, Event
     */
    private static CommandType parseLineTaskType(String lineData) {
        char lineSymbol = lineData.charAt(1);
        CommandType commandType = null;
        switch (lineSymbol) {
        case 'T':
            commandType = CommandType.TODO;
            break;
        case 'D':
            commandType = CommandType.DEADLINE;
            break;
        case 'E':
            commandType = CommandType.EVENT;
            break;
        }
        return commandType;
    }

    /**
     * parses line data from data.txt file to determine if task is done
     * @param lineData raw task data string
     * @return whether task is done
     */
    private static boolean parseLineIsDone(String lineData) {
        char doneIndicator = lineData.charAt(4);
        return doneIndicator == 'X';
    }

    /**
     * Appends new Task object to taskList ArrayList
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
     * Deletes Task at index from ArrayList
     * @param index index of object to be deleted
     */
    private static void deleteHandler(int index) {
        System.out.println(LINE_SEPERATOR);
        System.out.println("\tRoger Captain. I've deleted this task.");
        System.out.println("\t" + taskList.get(index));

        System.out.println("\tThere are " + (taskList.size()-1) + " tasks in your list.");
        System.out.println(LINE_SEPERATOR);

        taskList.remove(index);
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
    private static int parseCommandIndex(String line) {
        int seperatorIndex = line.indexOf(" ") + 1;
        String indexString = line.substring(seperatorIndex);

        return Integer.parseInt(indexString) - 1;
    }

    /**
     * Marks/Unmarks task in taskList
     * @param index index of Task in taskList
     * @param commandType enum TARS.logic.CommandType MARK, UNMARK
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
//
//    /**
//     * takes user input line and parses out Task attributes to return Task Object
//     * parsing logic depends on command type
//     * WARNING: only basic input validation implemented
//     * @param commandBody user input String
//     * @param commandType enum TARS.logic.CommandType TODO, DEADLINE, EVENT
//     * @param byDelimiter String flag for by argument in command
//     * @param fromDelimiter String flag for from argument in command
//     * @param toDelimiter String flag for to argument in command
//     * @return TARS.task.Todo, TARS.task.Deadline or TARS.task.Event Object
//     */
//    private static Task getTask(String commandBody, CommandType commandType, String byDelimiter, String fromDelimiter, String toDelimiter) {
//        Task newTask = null;
//        String description = commandBody;
//        String by  = null;
//        String from = null;
//        String to = null;
//
//        // E.g. event E /from a /to b
//        //     commandType = TARS.logic.CommandType.EVENT;  commandBody = "E /from a /to b";
//
//        switch (commandType) {
//        case DEADLINE: // Handle TARS.task.Deadline command containing /by
//            int byIndex = commandBody.toLowerCase().indexOf(byDelimiter);
//            by = commandBody.substring(byIndex + byDelimiter.length()).trim();
//            description = commandBody.substring(0, byIndex).trim();
//            break;
//
//        case EVENT: // Handle TARS.task.Event command containing /from /to
//            int fromIndex = commandBody.toLowerCase().indexOf(fromDelimiter);
//            int toIndex = commandBody.toLowerCase().indexOf(toDelimiter);
//
//            from = description.substring(fromIndex + fromDelimiter.length(), toIndex).trim();
//            to = description.substring(toIndex + toDelimiter.length()).trim();
//            description = description.substring(0, fromIndex).trim();
//        }
//
//        // Return Task object based on commandType
//        switch (commandType) {
//        case TODO:
//            newTask = new Todo(description);
//            break;
//        case DEADLINE:
//            newTask = new Deadline(description, by);
//            break;
//        case EVENT:
//            newTask = new Event(description, from, to);
//            break;
//        }
//        return newTask;
//    }

    /**
     * Executes command based on commandType and commandBody. Execution logic is extracted to sub-methods
     * @param commandType enum TARS.logic.CommandType
     * @param commandBody user input String
     */
    private static void commandHandler (CommandType commandType, String commandBody) {
        switch (commandType) {
        case LIST:
            printTaskList();
            break;

        case MARK:
        case UNMARK:

            markHandler(parseCommandIndex(commandBody), commandType);
            break;

        case DELETE:
            deleteHandler(parseCommandIndex(commandBody));
            break;

        case TODO:
        case EVENT:
        case DEADLINE:
//            Task newTask = getTask(commandBody, commandType, "/by", "/from", "/to");
//            addTask(newTask);
            break;

        default:
            break;

        }
    }
}

