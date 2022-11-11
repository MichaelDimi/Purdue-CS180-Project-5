import Objects.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LoginMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        System.out.println("Type 'CANCEL at anytime to go back to start");

        String usernameEmail;
        String password;

        User returningUser = null;

        String[] input = Menu.validateLoginInput(scan);

        if (input == null) {
            return false;
        }

        usernameEmail = input[0];
        password = input[1];

        System.out.println("Validating...");

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

        try {
            Thread.sleep(1000); // For dramatic effect
        } catch (InterruptedException e) {
            System.out.println("Error: Program interruption");
        }

        boolean foundUser = false;
        // Validate in Marketplace
        ArrayList<User> users = BookApp.marketplace.getUsers();
        for (User user : users) {
            System.out.println(user);
            if (user.getName().equals(usernameEmail) || user.getEmail().equals(usernameEmail)) {
                if (user.getPassword().equals(hashedPassword)) {
                    if (user.getName().equals(usernameEmail)) {
                        returningUser = BookApp.marketplace.getUserByUsername(usernameEmail);
                    } else {
                        returningUser = BookApp.marketplace.getUserByEmail(usernameEmail);
                    }
                    System.out.println("Welcome back " + returningUser.getName() + "!");
                    foundUser = true;
                    break;
                }
            }
        }
        if (!foundUser) {
            System.out.println("Invalid username or password");
            return false;
        }

        BookApp.marketplace.setCurrentUser(returningUser);

        return true;
    }
}
