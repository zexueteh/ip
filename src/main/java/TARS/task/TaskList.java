package TARS.task;

import java.util.ArrayList;
import TARS.logic.Parser;

public class TaskList {

    private ArrayList<Task> taskList;

    public TaskList(ArrayList<String> rawFileData) throws Parser.TARSParserTaskReadException {
        taskList = new ArrayList<Task>();

        for (String taskData : rawFileData) {
            Task newTask = Parser.parseFileLine(taskData);
            taskList.add(newTask);
        }
    }

    public int length() {
        return taskList.size();
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public void add(Task newTask) {
        taskList.add(newTask);
    }

    public void remove(int index) {
        taskList.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString().trim();
    }
}
