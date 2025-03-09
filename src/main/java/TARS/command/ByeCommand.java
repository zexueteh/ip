package TARS.command;

import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a command to add a task to the task list.
 */
public class ByeCommand extends Command {
    /**
     * Constructs an {@code ByeCommand} that prints GoodBye Message.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printGoodbyeMessage();
    }

    /**
     * Returns exit status as True.
     * @return Exit status as True.
     */
    @Override
    public boolean isExit(){
        return true;
    }
}
