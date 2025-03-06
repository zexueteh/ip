package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class MarkCommand extends Command {
    boolean isMark;
    public MarkCommand(int index, boolean isMark) {
        super(index);
        this.isMark = isMark;
    }

    /**
     * Formats and prints Tasks objects in taskList
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws Validator.TARSInvalidCommandParam, Storage.TARSStorageOperationException {
        int numberTasks = taskList.length();
        Validator.validateIndexParam(numberTasks, index);

        Task task = taskList.getTask(index-1);
        task.setIsDone(isMark);

        if (isMark) {
            ui.printResponseMessage("Task Done. Good job Captain!");
        } else {
            ui.printResponseMessage("Ok, your task is marked as not done yet.");
        }
        ui.printResponseMessage(task.toString());

        Storage.write(taskList.toString());

    }
}
