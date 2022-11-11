package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {

    /**
     * Presents a menu
     */
    public boolean present(Scanner scan) {
        System.out.println("Override this method to make your custom menu");
        return false;
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
            regexPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$");
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
