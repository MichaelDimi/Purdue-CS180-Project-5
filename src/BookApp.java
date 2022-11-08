import Objects.*;

import java.util.Objects;
import java.util.Scanner;

public class BookApp {

    public static Marketplace marketplace;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        BookApp.marketplace = new Marketplace();

        System.out.println("WELCOME to BOOK APP");
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        // Login or Sign up Loop TODO: add exit
        do {
            String loginSignup;
            boolean validUser = false;
            do {
                System.out.println("1. Login\n2. Sign Up");
                loginSignup = scan.nextLine();
                if (!Objects.equals(loginSignup, "1") && !Objects.equals(loginSignup, "2")) {
                    System.out.println("Whoops: Please enter (1) or (2)");
                    continue;
                }

                if (loginSignup.equals("1")) {
                    LoginMenu loginMenu = new LoginMenu();
                    validUser = loginMenu.present(scan); // if false, return to login or signup
                } else {
                    SignUpMenu signUpMenu = new SignUpMenu();
                    validUser = signUpMenu.present(scan); // Ignore this if we are signing up
                }
            } while (!validUser);

            Seller newSeller = (Seller) marketplace.getCurrentUser();
            newSeller.editStore();
            marketplace.saveMarketplace();

            // Main loop
            do {
                // ACCOUNT MENU
                AccountMenu accountMenu = new AccountMenu();
                boolean accountMenuResult = accountMenu.present(scan); // If false, sign out
                if (!accountMenuResult) {
                    // Sign out
                    marketplace.setCurrentUser(null);
                    BookApp.marketplace.saveMarketplace();
                    break; // Should break main loop
                }
            } while (true); // Main loop
        } while (true); // Login or Sign up Loop
    }
}
