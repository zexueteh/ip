package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public abstract class Command {
    protected final int index;
    protected final Task task;


    private Command(int index, Task task) {
        this.index = index;
        this.task = task;
    }

    public Command(int index) {
        this(index, null);
    }

    public Command(Task task) {
        this(-1, task);
    }

    public Command() {
        this(-1, null);
    }

    public abstract void execute(TaskList taskList, Ui ui, Storage storage)
            throws Validator.TARSInvalidCommandParam, Storage.TARSStorageOperationException;
    public boolean isExit() {
        return false;
    };
}
