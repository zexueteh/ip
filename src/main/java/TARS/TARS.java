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

    /**
     * Initializes TARS chatbot from a flat file data.txt
     * <p>
     * Data is loaded from either an existing file, otherwise new task list is created if file is not found.
     *
     * @param dataFolderPath The path to the folder containing data.txt
     * @param dataFilePath The path to data.txt
     */
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

    /**
     * Runs the chatbot, displays Hello message and processes user commands
     * <p>
     * Chatbot continues running until "/bye" command is entered
     */
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
                ui.printResponseMessage(e.getMessage());
            } finally {
                ui.printLineDivider();
            }
        }
    }


    /**
     * The main entry point into TARS Chatbot Application
     * <p>
     * Supported Commands:
     * <ul>
     * <li>Add Task: commands "/todo {description}", "/deadline {description} /by {deadline]", "/event {description} /from {start} /to {end}" for Todo, Deadline and Event tasks.</li>
     * <li>List Tasks: command "/list"</li>
     * <li>Marking/Unmarking Tasks as done/undone: command "/mark {index}", "/unmark {index}"</li>
     * <li>Finding Tasks: "/find {searchTerm}" </li>
     * <li>Exit: command "bye"</li>
     *
     * </ul>
     * @param args String buffer
     */
    public static void main(String[] args) {
        new TARS(dataFolderPath, dataFilePath).run();
    }

}

