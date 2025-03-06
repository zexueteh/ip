package TARS;

import TARS.command.Command;
import TARS.logic.Parser;
import TARS.task.TaskList;
import TARS.util.Ui;
import TARS.util.Storage;

public class TARS {
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

        } catch (Storage.TARSStorageFilePathException |
                 Storage.TARSStorageOperationException |
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
                ui.printLineDivider(); // show the divider line ("_______")
                Command c = Parser.parseCommand(fullCommand);
                assert c != null;
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                ui.printLineDivider();
            }
        }
    }


    /**
     * The main entry point into TARS Chatbot Application
     * <p>
     * This method initializes the chatbot, displays a welcome message
     * and maintains a handler loop for user input until "bye" command is entered.
     * Users can add tasks, list tasks, mark tasks as done, unmark tasks
     * <p>
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
    }

}

