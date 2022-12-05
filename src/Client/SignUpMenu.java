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

            System.out.println("Validating...");

            try {
                Thread.sleep(1000); // For dramatic effect
            } catch (InterruptedException e) {
                System.out.println("Whoops: Program interruption");
            }

            // Add user
            Query validateAddQuery = new ClientQuery().updateQuery(null, "users", "add", new String[]{username, email, password, isBuyer ? "T" : "F"});
            if (validateAddQuery.getObject().equals(false)) {
                if (validateAddQuery.getOptions().equals("validation err")) {
                    System.out.println("Whoops: Validation failed. Please try again");
                } else if (validateAddQuery.getOptions().equals("hash err")) {
                    System.out.println("Whoops: Unable to hash password");
                } else {
                    System.out.println("Whoops: Unable to create your account. Please try again");
                }
                validationSuccess = false;
            } else {
                System.out.println("Validation successful!");
                // logs user in after signing up; uses get query to get user with the username of the just created account from server
                BookApp.currentUser = (User) BookApp.getQuery(username, "users", "name").getObject();
                validationSuccess = true;
            }
        } while (!validationSuccess);

        return true;
    }
}
