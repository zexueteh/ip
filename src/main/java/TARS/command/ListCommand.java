package TARS.command;

import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class ListCommand extends Command {

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
