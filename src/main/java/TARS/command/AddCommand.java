package TARS.command;

import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    /**
     * Constructs an {@code AddCommand} with the specified task.
     *
     * @param newTask The task to be added.
     */
    public AddCommand(Task newTask) {
        super(newTask);
    }

    /**
     * Executes the add command by adding the task to the task list,
     * displaying a confirmation message, and updating storage.
     *
     * @param taskList The task list to which the task is added.
     * @param ui The user interface to display messages.
     * @param storage The storage to update with the new task.
     * @throws Storage.TARSStorageOperationException If an error occurs while writing to storage.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws Storage.TARSStorageOperationException {
        taskList.add(this.task);
        int numberTasks = taskList.length();

        ui.printResponseMessage("Yes Captain. I've added the following task:");
        ui.printResponseMessage(task.toString());
        ui.printResponseMessage(String.format("There are now %d tasks in your list.", numberTasks));

        Storage.write(taskList.toString());
    }
}
