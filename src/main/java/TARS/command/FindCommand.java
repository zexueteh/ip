package TARS.command;

import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class FindCommand extends Command {
    private final String searchTerm;
    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage){
        int numberTasks = taskList.length();
        ui.printResponseMessage("Here are the tasks matching the search term: \"" + searchTerm + "\"");

        for (int i = 0; i < numberTasks; i++) {
            Task task = taskList.getTask(i);

            if (task.contains(searchTerm)) {
                ui.printResponseMessage(String.format("%d.%s", i+1, task));
            }

        }

    }
}
