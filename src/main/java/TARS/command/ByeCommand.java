package TARS.command;

import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printGoodbyeMessage();
    }

    @Override
    public boolean isExit(){
        return true;
    }
}
