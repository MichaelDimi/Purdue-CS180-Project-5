package Client;

import Objects.*;
import Query.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class contains the main method and is the
 * center of our program. It runs on a loop until the
 * user exits.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class BookApp {
//    public static Marketplace marketplace;

    public static User currentUser;

    public static String appName = "Barnes & Novel";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

//        BookApp.marketplace = new Marketplace();

        System.out.println("WELCOME to " + appName);
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        // Login or Sign up Loop
        do {

            String loginSignup;
            boolean validUser = false;
            do {
                currentUser = null;
                System.out.println("1. Login\n2. Sign Up\n3. EXIT");
                loginSignup = scan.nextLine();
                if (!loginSignup.equals("1") && !loginSignup.equals("2") && !loginSignup.equals("3")) {
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

            // Main loop
            do {
                if (currentUser == null) {
                    break; // Exit to the login loop
                }

                // Update the current user
                Query updateUserQuery = new ClientQuery().getQuery(currentUser, "users", "currentUser");
                if (updateUserQuery.getObject() == null || updateUserQuery.getObject().equals(false)) {
                    break;
                }
                currentUser = (User) updateUserQuery.getObject();

                if (currentUser instanceof Buyer) {
                    CustomerHomepage customerHomepage = new CustomerHomepage();
                    boolean mainMenu = customerHomepage.present(scan);
                    if (!mainMenu) {
                        break;
                    }
                } else if (currentUser instanceof Seller) {
                    SellerHomepage sellerHomepage = new SellerHomepage();
                    boolean mainMenu = sellerHomepage.present(scan);
                    if (!mainMenu) {
                        break;
                    }
                } else {
                    break;
                }

            } while (true); // Main loop

        } while (true); // Login or Sign up Loop
    }
}


