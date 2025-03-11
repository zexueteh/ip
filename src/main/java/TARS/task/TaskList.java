package TARS.task;

import java.util.ArrayList;
import TARS.logic.Parser;

/**
 * Represents a list of tasks managed by the chatbot.
 * <p>
 * This class CRUD operations for task management, such as adding, removing, retrieving and stringify
 */
public class TaskList {

    private final ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Constructs a {@code TaskList} from raw file data.
     * <p>
     * Parses each line of stored task data to reconstruct the list of tasks.
     *
     * @param rawFileData A list of strings representing tasks stored in a file.
     * @throws Parser.TARSParserTaskReadException If there is an error in parsing task data.
     */
    public TaskList(ArrayList<String> rawFileData) throws Parser.TARSParserTaskReadException {

        for (String taskData : rawFileData) {
            Task newTask = Parser.parseFileLine(taskData);
            taskList.add(newTask);
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int length() {
        return taskList.size();
    }

    /**
     * Retrieves a task a specified index.
     *
     * @param index The task at the specified index.
     * @return Task object at specified index.
     */
    public Task getTask(int index) {
        return taskList.get(index);
    }

    /**
     * Adds a new task to the list.
     *
     * @param newTask Task object to be added to list.
     */
    public void add(Task newTask) {
        taskList.add(newTask);
    }

    /**
     * Removes a task from the list at specified index.
     *
     * @param index The index of the task to be removed.
     */
    public void remove(int index) {
        taskList.remove(index);
    }

    /**
     * Returns a string representation of task list. Each task is represented by a line.
     * The string representation is directly written to storage file
     *
     * @return A string representation of the task list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString().trim();
    }
}
