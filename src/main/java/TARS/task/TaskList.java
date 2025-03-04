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

}
