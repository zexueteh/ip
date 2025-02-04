import java.util.Scanner;

public class TARS {
    private static int nTasks = 0;
    private static Task[] taskList = new Task[100];

    public static void main(String[] args) {
        printHelloMessage();
        String line;
        Scanner sc = new Scanner(System.in);

        while (true) {
            line = sc.nextLine().strip();
            if (line.equals("bye")) {
                break;
            } else if (line.equals("list")) {
                System.out.println("    ____________________________________________________________");
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < nTasks; i++) {
                    System.out.println("    " + (i+1) + "." +taskList[i]);
                }
                System.out.println("    ____________________________________________________________");

            } else if (line.indexOf("mark") == 0 || line.indexOf("unmark") == 0) {
                int index = Integer.parseInt(line.substring(line.indexOf(" ") + 1)) - 1;

                if (line.indexOf("mark") == 0) {
                    taskList[index].setIsDone(true);
                } else {
                    taskList[index].setIsDone(false);
                }

                System.out.println("    ____________________________________________________________");
                if (taskList[index].getIsDone())
                    System.out.println("    Your task has been marked as done.");
                else
                    System.out.println("    Ok, your task is marked as not done yet.");
                System.out.println("      " + taskList[index]);
                System.out.println("    ____________________________________________________________");

            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    added: " + line);
                System.out.println("    ____________________________________________________________");

                taskList[nTasks++] = new Task(line);
            }
        }

        printGoodbyeMessage();
    }

    private static void printHelloMessage() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      _____  _    ____  ____  ");
        System.out.println("     |_   _|/ \\  |  _ \\/ ___| ");
        System.out.println("       | | / _ \\ | |_) \\___ \\ ");
        System.out.println("       | |/ ___ \\|  _ < ___) |");
        System.out.println("       |_/_/   \\_\\_| \\_\\____/ ");
        System.out.println("                              ");
        System.out.println("    Hello, I'm TARS, your friendly chatbot assistant. ");
        System.out.println("    How can I help you today?");
        System.out.println("    ____________________________________________________________");
    }

    private static void printGoodbyeMessage() {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Goodbye and Goodnight! ");
        System.out.println("    ____________________________________________________________");
    }
}
