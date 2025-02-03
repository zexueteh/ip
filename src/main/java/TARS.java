import java.util.Scanner;

public class TARS {

    public static void main(String[] args) {
        printHelloMessage();
        String line;
        Scanner sc = new Scanner(System.in);

        do {
            line = sc.nextLine().strip();
            System.out.println("____________________________________________________________");
            System.out.println(line);
            System.out.println("____________________________________________________________");

        } while (!line.equals("bye"));
        
        printGoodbyeMessage();
    }

    private static void printHelloMessage() {
        System.out.println("____________________________________________________________");
        System.out.println("  _____  _    ____  ____  ");
        System.out.println(" |_   _|/ \\  |  _ \\/ ___| ");
        System.out.println("   | | / _ \\ | |_) \\___ \\ ");
        System.out.println("   | |/ ___ \\|  _ < ___) |");
        System.out.println("   |_/_/   \\_\\_| \\_\\____/ ");
        System.out.println("                          ");
        System.out.println("Hello, I'm TARS, your friendly chatbot assistant. ");
        System.out.println("How can I help you today?");
        System.out.println("____________________________________________________________");
    }

    private static void printGoodbyeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println("Goodbye and Goodnight! ");
        System.out.println("____________________________________________________________");
    }
}
