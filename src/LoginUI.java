import java.util.*;
public class LoginUI {

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
