package TARS.command;

import TARS.logic.Validator;
import TARS.task.Task;
import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

/**
 * Represents a generic command that can be executed by the chatbot.
 */
public abstract class Command {
    protected final int index;
    protected final Task task;


    private Command(int index, Task task) {
        this.index = index;
        this.task = task;
    }

    /**
     * Constructs a {@code Command} with the specified index and task.
     *
     * @param index The task index.
     */
    public Command(int index) {
        this(index, null);
    }

    public Command(Task task) {
        this(-1, task);
    }

    public Command() {
        this(-1, null);
    }

    /**
     * Executes the command using the provided task list, user interface, and storage.
     *
     * @param taskList The task list to be manipulated.
     * @param ui The user interface for displaying messages.
     * @param storage The storage system for saving tasks.
     * @throws Validator.TARSInvalidCommandParam If the command parameters are invalid.
     * @throws Storage.TARSStorageOperationException If an error occurs while accessing storage.
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage)
            throws Validator.TARSInvalidCommandParam, Storage.TARSStorageOperationException;
    public boolean isExit() {
        return false;
    };
}
