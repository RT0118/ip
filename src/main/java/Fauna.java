import java.util.Scanner;

public class Fauna {
    private static final String chatbotName = "Fauna";

    // level-1
    private static void chatbotEcho() {
        // loop user chatbot conversation
        Scanner sc = new Scanner(System.in);
        while (true) {
            // get user's input
            String userInput = sc.nextLine();
            System.out.println("____________________________________________________________");

            if (userInput.equals("bye")) {
                break;
            }

            // echo user's input
            System.out.println(userInput);
            System.out.println("____________________________________________________________\n");
        }
    }

    public static void main(String[] args) {
        String logo = """
             _____ _   _   _ _   _    _    
            |  ___/ \\ | | | | \\ | |  / \\   
            | |_ / _ \\| | | |  \\| | / _ \\  
            |  _/ ___ \\ |_| | |\\  |/ ___ \\ 
            |_|/_/   \\_\\___/|_| \\_/_/   \\_\\""";
        System.out.println("Hello from\n" + logo + "\n");

        // greet the user
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + chatbotName);
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");

        // Level-1 Echo
        chatbotEcho();

        // exit
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}