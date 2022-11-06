import Classes.User;

import java.util.*;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {

    @Override
    public void present(Scanner scan) {
        System.out.println("*******************");
        String[] input = Menu.validateLoginSignUpInput(scan);

        String username = input[0];
        String email = input[1];
        String password = input[2];

        // TODO: Validate in Marketplace
        System.out.println(username + " | " + email + " | " + password);
        System.out.println("*******************");
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
