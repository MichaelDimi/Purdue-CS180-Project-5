package Client;

import Objects.Buyer;
import Objects.Seller;
import Objects.User;
import Query.*;

import java.util.Objects;
import java.util.Scanner;

public class SignUpConcurrencyTest extends Menu {

    @Override
    public boolean present(Scanner scan) {
        String username;
        String email;
        String password;
        boolean validationSuccess;
        do {
            String[] input = {"Aaron", "SomeEmail@email.cum", "CyberSecure"};

            username = input[0];
            email = input[1];
            password = input[2];

            boolean isBuyer = true;

            System.out.println("Validating...");

//            try {
//                Thread.sleep(1000); // For dramatic effect
//            } catch (InterruptedException e) {
//                System.out.println("Whoops: Program interruption");
//            }

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
                BookApp.currentUser = new User("", "", "", ""); //TODO: CHEck THis
                validationSuccess = true;
            }
        } while (!validationSuccess);

        return false;
    }
}
