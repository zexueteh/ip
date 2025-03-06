package TARS.command;

import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class AddCommand extends Command {
    public AddCommand(Task newTask) {
        super(newTask);
    }

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
