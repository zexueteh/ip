package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a command to mark or unmark a task as completed.
 */
public class MarkCommand extends Command {
    boolean isMark;
    /**
     * Constructs a {@code MarkCommand} with the specified task index and mark status.
     *
     * @param index The index of the task to be marked/unmarked.
     * @param isMark {@code true} to mark as done, {@code false} to unmark.
     */
    public MarkCommand(int index, boolean isMark) {
        super(index);
        this.isMark = isMark;
    }

    /**
     * Executes the mark command by updating the task status and displaying a confirmation message.
     *
     * @param taskList The task list containing the task.
     * @param ui The user interface to display messages.
     * @param storage The storage to update after marking the task.
     * @throws Validator.TARSInvalidCommandParam If the task index is invalid.
     * @throws Storage.TARSStorageOperationException If an error occurs while writing to storage.
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
