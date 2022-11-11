import Objects.Buyer;
import Objects.Seller;
import Objects.User;

import java.nio.charset.StandardCharsets;
import java.security.*;

import java.util.Objects;
import java.util.Scanner;

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
                System.out.println("Error: Program interruption");
            }

            // Validate in Marketplace
            validationSuccess = BookApp.marketplace.validateName(username) && BookApp.marketplace.validateEmail(email);
            if (validationSuccess) {
                System.out.println("Validation successful!");
            }
        } while (!validationSuccess);

        // PASSWORD HASHING
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Whoops: Unable to hash password");
            return false;
        }
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        String hashedPassword = s.toString();

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
            newUser = new Buyer(username, email, hashedPassword);
        } else {
            newUser = new Seller(username, email, hashedPassword);
        }

        BookApp.marketplace.addToUsers(newUser);
        BookApp.marketplace.setCurrentUser(newUser);

        return true; // Always return true
    }
}
