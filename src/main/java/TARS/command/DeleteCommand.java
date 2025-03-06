package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand(int index) {
        super(index);
    }
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
