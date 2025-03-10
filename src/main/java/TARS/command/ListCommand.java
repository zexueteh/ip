package TARS.command;

import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a command to list all tasks from the task list.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command by displaying all tasks in the task list.
     *
     * @param taskList The task list to display.
     * @param ui The user interface to display messages.
     * @param storage The storage system (not used in this command).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int numberTasks = taskList.length();
        ui.printResponseMessage(String.format("You have %d task(s) in your list:", numberTasks));

        for (int i = 0; i < numberTasks; i++) {
            Task task = taskList.getTask(i);
            ui.printResponseMessage(String.format("%d.%s", i+1, task));
        }


    }

}
