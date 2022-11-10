import Objects.Buyer;
import Objects.Seller;
import Objects.User;
import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SignUpMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {

        String username;
        String email;
        String password;
        boolean validationSuccess;
        do {
            String[] input = Menu.validateSignUpInput(scan);

            username = input[0];
            email = input[1];
            password = input[2];

            System.out.println("Validating...");

            try {
                Thread.sleep(1000); // For dramatic effect
            } catch (InterruptedException e) {
                System.out.println("Error: Program interruption");
            }

            // Validate in Marketplace
            validationSuccess = BookApp.marketplace.validateName(username) && BookApp.marketplace.validateEmail(email);
            if (validationSuccess) {
                System.out.println("Validation successful!");
            }
        } while (!validationSuccess);

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
            newUser = new Buyer(username, email, password);
        } else {
            newUser = new Seller(username, email, password);
        }

        BookApp.marketplace.addToUsers(newUser);
        BookApp.marketplace.setCurrentUser(newUser);

        return true; // Always return true
    }
}
