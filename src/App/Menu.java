package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class contains is a parent class to other menus,
 * and contains useful features for child menus
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public abstract class Menu {

    /**
     * Presents a menu
     */
    public boolean present(Scanner scan) {
        System.out.println("Override this method to make your custom menu");
        return false;
    }

    /**
     * Abstraction to select a specific item from a list of i size.
     * @param i The size of the list
     * @param scan the scanner for user input
     * @return the integer of the selected option
     */
    public static int selectFromList(int i, Scanner scan) {
        int response = 0;
        boolean error;
        do {
            error = false;
            try {
                response = Integer.parseInt(scan.nextLine());
                if (response < 1 || response > i) {
                    if (i == 1) {
                        System.out.println("Whoops: Must be (1) or (2)");
                    } else {
                        System.out.printf("Whoops: Must be (1) -> (%d)\n", i);
                    }
                    error = true;
                }
            } catch (NumberFormatException e) {
                if (i == 1) {
                    System.out.println("Whoops: Must be (1) or (2)");
                } else {
                    System.out.printf("Whoops: Must be (1) -> (%d)\n", i);
                }
                error = true;
            }
        } while (error);
        return response;
    }

    /**
     * Queries the user for username, email, and password, and ensures
     * the user is entering valid input before returning.
     * @param scan The scanner that is passed by reference
     * @return Returns an array with the username, email, and password
     */
    public static String[] validateSignUpInput(Scanner scan) {
        String username;
        do {
            System.out.println("Enter your username: ");
            username = scan.nextLine();

            if (username.equals("CANCEL")) {
                return null;
            }
            if (username.isEmpty()) {
                System.out.println("Whoops: Please enter a valid username");
            }
        } while(username.isEmpty());

        String email;
        Pattern regexPattern;
        do {
            System.out.println("Enter your email: ");
            email = scan.nextLine();
            // Email regex that I got from here:
            // https://www.baeldung.com/java-email-validation-regex
            // Supports all types of valid emails
            regexPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)" +
                    "*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$");
            if (email.equals("CANCEL")) {
                return null;
            }
            if (!regexPattern.matcher(email).matches()) {
                System.out.println("Whoops: Please enter a valid email");
            } else if (email.isEmpty()) {
                System.out.println("Whoops: Please enter a valid email");
            }
        } while(email.isEmpty() || !regexPattern.matcher(email).matches());

        String password;
        do {
            System.out.println("Enter your password: ");
            password = scan.nextLine();
            if (password.equals("CANCEL")) {
                return null;
            }
            if (password.isEmpty()) {
                System.out.println("Whoops: Please enter a valid password");
            }
        } while(password.isEmpty());

        return new String[]{username, email, password};
    }

    /**
     * Queries the user for username or email, and password, and ensures
     * the user is entering valid input before returning.
     * @param scan The scanner that is passed by reference
     * @return Returns an array with the username or email, and password
     */
    public static String[] validateLoginInput(Scanner scan) {
        String input;
        do {
            System.out.println("Enter your username or email: ");
            input = scan.nextLine();
            if (input.equals("CANCEL")) {
                return null;
            }
            if (input.isEmpty()) {
                System.out.println("Whoops: Please enter a valid username or email");
            }
        } while(input.isEmpty());

        String password;
        do {
            System.out.println("Enter your password: ");
            password = scan.nextLine();
            if (password.equals("CANCEL")) {
                return null;
            }
            if (password.isEmpty()) {
                System.out.println("Whoops: Please enter a valid password");
            }
        } while(password.isEmpty());

        return new String[]{input, password};
    }
}
