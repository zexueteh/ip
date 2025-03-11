package TARS.command;

import TARS.task.TaskList;
import TARS.util.Storage;
import TARS.util.Ui;

public class HelpCommand extends Command {
    private static final String HELP_RESPONSE = """
            Here are the available commands you can use:
            
            \t1. Add ToDo Task: /todo {task_description}
            \t2. Add Deadline Task: /deadline {task_description} /by {due_date}
            \t3. Add Event Task: /event {task_description} /from {start_time} /to {end_time}
            \t4. List All Tasks: /list
            \t5. Mark Task: /mark {task_number}
            \t6. Unmark Task: /unmark {task_number}
            \t7. Delete Task: /delete {task_number}
            \t8. Find Task: /find {search_term}
            \t9. Exit: /bye
            
            \tNote that commands are case-insensitive.
            \tBackslashes are used exclusively for flags and should not be used as input.""";

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printResponseMessage(HELP_RESPONSE);
    }
}
