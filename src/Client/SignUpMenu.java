package Client;

import Objects.Buyer;
import Objects.Seller;
import Objects.User;
import Query.*;

import java.util.Objects;
import java.util.Scanner;

/**
 * This class contains the menu in which
 * the user can create an account that can
 * be a buyer or seller
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class SignUpMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        System.out.println("Type 'CANCEL at anytime to go back to start");

        String username;
        String email;
        String password;
        boolean validationSuccess;
        do {
            String[] input = Menu.validateSignUpInput(scan);

            if (input == null) {
                return false;
            }

            username = input[0];
            email = input[1];
            password = input[2];

            System.out.println("Validating...");

            try {
                Thread.sleep(1000); // For dramatic effect
            } catch (InterruptedException e) {
                System.out.println("Whoops: Program interruption");
            }

            // Validate in Marketplace
            Query validateNameQuery = BookApp.computeQuery(new String[]{username}, "users", "validate name");
            Query validateEmailQuery = BookApp.computeQuery(new String[]{email}, "users", "validate email");
            validationSuccess = (boolean) validateNameQuery.getObject() && (boolean) validateEmailQuery.getObject();
            if (validationSuccess) {
                System.out.println("Validation successful!");
            } else {
                System.out.println("Whoops: Validation failed. Please try again");
            }
        } while (!validationSuccess);

        // PASSWORD HASHING
        String hashedPassword = User.hashPassword(password);
        if (hashedPassword == null) {
            return false;
        }

        boolean isBuyer;
        do {
            System.out.println("Are you a\n1. Buyer\n2. Seller");
            String buyerSeller = scan.nextLine();
            if (buyerSeller.equals("1") || buyerSeller.equals("2")) {
                isBuyer = Objects.equals(buyerSeller, "1");
                break;
            }
            System.out.println("Whoops: Please enter (1) or (2)");
        } while(true);

        // Saves to marketplace and logs the user in
        User newUser;
        if (isBuyer) {
            newUser = new Buyer(username, email, hashedPassword, password);
        } else {
            newUser = new Seller(username, email, hashedPassword, password);
        }

        // Updates that add info rather than change, will have a null object:
        if ((boolean) BookApp.updateQuery(null, "users", "add", newUser).getObject()) {
            BookApp.currentUser = newUser;
            return true;
        } else {
            System.out.println("Whoops: Unable to create your account. Please try again");
            return false;
        }
    }
}
