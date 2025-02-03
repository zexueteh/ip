import java.util.Scanner;

public class TARS {
    private static int nTasks = 0;
    private static String[] taskList = new String[100];

    public static void main(String[] args) {
        printHelloMessage();
        String line;
        Scanner sc = new Scanner(System.in);

        while (true) {
            line = sc.nextLine().strip();
            if (line.equals("bye")) {
                break;
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    " + line);
                System.out.println("    ____________________________________________________________");
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
