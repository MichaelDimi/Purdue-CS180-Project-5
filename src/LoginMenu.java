import Objects.User;

import java.util.*;

public class LoginMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        String usernameEmail;
        String email;
        String password;

        User returningUser = null;

        String[] input = Menu.validateLoginInput(scan);

        usernameEmail = input[0];
        password = input[1];

        System.out.println("Validating..." + usernameEmail + " " + password); // TODO: REMOVE

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
        System.out.println(BookApp.marketplace.getCurrentUser());

        System.out.println("*******************");
        return true;
    }

    public static User printLoginAndGetUser(Scanner scan, User[] users) {
        boolean loop = true;
        User currentUser = null;
        while (loop) {
            while (loop) {
                System.out.println("Enter your email");
                String email = scan.nextLine();
                System.out.println("Enter your password");
                String password = scan.nextLine();
                if (email.isEmpty() || password.isEmpty()) {
                    loop = true;
                    break;
                }
                currentUser = checkUserData(email, password, users);
                if (currentUser == null) {
                    System.out.println("Incorrect Email or Password");
                    loop = true;
                    break;
                } 
                loop = false;
            } 
        }
        return currentUser;
    }
    public static User checkUserData(String email, String password, User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getEmail().equals(email) && users[i].getPassword().equals(password)) {
                return users[i];
            }
        }
        return null;
    }



}
