import Classes.Marketplace;

import java.util.Objects;
import java.util.Scanner;

public class BookApp {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Marketplace marketplace = new Marketplace();

        System.out.println("WELCOME to BOOK APP");
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        String loginSignup;
        do {
            System.out.println("1. Login\n2. Sign Up");
            loginSignup = scan.nextLine();
            if (Objects.equals(loginSignup, "1") || Objects.equals(loginSignup, "2")) {
                break;
            }
            System.out.println("Whoops: Please enter (1) or (2)");
        } while (true);

        if (loginSignup.equals("1")) {
            System.out.println("Login");
        } else {
            System.out.println("Sign Up");
        }
    }
}
