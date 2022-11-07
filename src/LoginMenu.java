import Objects.User;

import java.util.*;

public class LoginMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        String usernameEmail;
        String password;

        User returningUser = null;

        String[] input = Menu.validateLoginInput(scan);

        usernameEmail = input[0];
        password = input[1];

        System.out.println("Validating...");

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
                if (user.getPassword().equals(password)) {
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
