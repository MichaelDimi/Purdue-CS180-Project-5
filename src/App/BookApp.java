package App;

import Objects.*;

import java.util.Objects;
import java.util.Scanner;
/**
* This class contains the main method and is the
* center of our program. It runs on a loop until the
* user exits.
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class BookApp {

    public static Marketplace marketplace;

    public static String appName = "BOOK APP"; // TODO: make a pun-y name

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        BookApp.marketplace = new Marketplace();

        System.out.println("WELCOME to " + appName);
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        // Login or Sign up Loop
        do {
            System.out.println(marketplace.getUsers()); // TODO: REMOVE THIS (useful for logging in correctly)

            String loginSignup;
            boolean validUser = false;
            do {
                System.out.println("1. Login\n2. Sign Up\n3. EXIT");
                loginSignup = scan.nextLine();
                if (!Objects.equals(loginSignup, "1") && !Objects.equals(loginSignup, "2") && !Objects.equals(loginSignup, "3")) {
                    System.out.println("Whoops: Please enter (1), (2), or (3)");
                    continue;
                }

                if (loginSignup.equals("1")) {
                    LoginMenu loginMenu = new LoginMenu();
                    validUser = loginMenu.present(scan); // if false, return to login or signup
                } else if (loginSignup.equals("2")) {
                    SignUpMenu signUpMenu = new SignUpMenu();
                    validUser = signUpMenu.present(scan); // Ignore this if we are signing up
                } else {
                    System.out.println("Thanks for using " + appName + "\nGoodbye!");
                    return;
                }
            } while (!validUser);

            marketplace.saveMarketplace();

            // Main loop
            do {
                User currentUser = marketplace.getCurrentUser();

                if (currentUser instanceof Buyer) {
                    CustomerHomepage customerHomepage = new CustomerHomepage();
                    boolean mainMenu = customerHomepage.present(scan);
                    if (!mainMenu) {
                        break;
                    }
                } else {
                    Seller seller = (Seller) marketplace.getCurrentUser();
                    boolean mainMenu = seller.editStore(scan);
                    if (!mainMenu) {
                        break;
                    }
                }

            } while (true); // Main loop

            marketplace.saveMarketplace();
        } while (true); // Login or Sign up Loop
    }
}


