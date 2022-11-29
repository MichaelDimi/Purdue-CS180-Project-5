package Client;

import Objects.User;

import java.util.*;

/**
 * This class displays the menu
 * for logging in as a user.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class LoginMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        System.out.println("Type 'CANCEL' at anytime to go back to start");

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
        String hashedPassword = User.hashPassword(password);
        if (hashedPassword == null) {
            return false;
        }

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
