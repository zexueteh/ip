package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a command to delete a task from the task list
 */
public class DeleteCommand extends Command {
    /**
     * Constructs a {@code DeleteCommand} with the specified task index.
     *
     * @param index The index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        super(index);
    }

    /**
     * Executes the delete command by removing the specified task from the task list,
     * displaying a confirmation message, and updating storage.
     *
     * @param taskList The task list from which the task is removed.
     * @param ui The user interface to display messages.
     * @param storage The storage to update after deleting the task.
     * @throws Validator.TARSInvalidCommandParam If the task index is invalid.
     * @throws Storage.TARSStorageOperationException If an error occurs while writing to storage.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws Validator.TARSInvalidCommandParam, Storage.TARSStorageOperationException {
        Validator.validateIndexParam(taskList.length(), index);

        Task task = taskList.getTask(index-1);
        taskList.remove(index-1);

        ui.printResponseMessage("Roger Captain. I've deleted this task.");
        ui.printResponseMessage(task.toString());
        ui.printResponseMessage(String.format("You have %d task(s) in your list:", taskList.length()));

        Storage.write(taskList.toString());
    }
}
