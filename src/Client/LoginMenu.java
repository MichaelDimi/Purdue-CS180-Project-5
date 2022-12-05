package Client;

import Objects.*;
import Query.*;

import java.util.*;
import java.util.stream.Stream;

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
            System.out.println("Whoops: Program interruption");
        }

        User returningUser;

        Query loginQuery = new ClientQuery().computeQuery(new String[]{usernameEmail, hashedPassword}, "users", "login");
        if (loginQuery.getObject() == null || loginQuery.getObject().equals(false)) {
            System.out.println("Invalid username or password");
            return false;
        }
        returningUser = (User) loginQuery.getObject();

        BookApp.currentUser = returningUser;

        System.out.println("Welcome back " + returningUser.getName() + "!");

        return true;
    }
}
