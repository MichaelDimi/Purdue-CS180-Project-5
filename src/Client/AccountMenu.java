package Client;

import Objects.*;
import Query.Query;

import java.util.Scanner;

/**
 * This class is used to print a menu to modify a
 * user’s account. The options are reset email,
 * password, username or account deletion.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class AccountMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        System.out.println("*******************");
        User user = BookApp.currentUser;

        do {
            // Fetch the user again
            Query updateUserQuery = BookApp.getQuery(user, "users", "currentUser");
            if (updateUserQuery.getObject().equals(false)) {
                return true;
            }
            user = (User) updateUserQuery.getObject();

            System.out.printf("You are a %s. Create a new account to become a %s.\n",
                    user instanceof Buyer ? "BUYER" : "SELLER",
                    user instanceof Buyer ? "SELLER" : "BUYER");
            System.out.println("Username: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getDisplayPassword());
            System.out.println("=======");
            System.out.println("1. Reset Username");
            System.out.println("2. Reset Email");
            System.out.println("3. Reset Password");
            System.out.println("4. Delete Account");
            System.out.println("5. DONE");

            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    boolean askUsernameAgain;
                    do {
                        System.out.println("New Username: (Cancel by entering your current name)");
                        String newName = scan.nextLine();
                        if (newName.equals(user.getName())) { // Cancel by typing your current name
                            break;
                        }

                        Query validateNameQuery = BookApp.computeQuery(new String[]{newName}, "users", "validate name");
                        askUsernameAgain = !(boolean) validateNameQuery.getObject();
                        if (!askUsernameAgain) {
                            Query setNameQuery = BookApp.updateQuery(BookApp.currentUser, "users", "name", newName);
                            if (setNameQuery.getObject().equals(false)) {
                                System.out.println("Whoops: Couldn't set your new username");
                                break;
                            }
                            user.setName(newName);
                        } else {
                            System.out.println("Whoops: Couldn't set your new username");
                            System.out.println("That username might be taken");
                        }
                    } while (askUsernameAgain);
                    break;
                case "2":
                    boolean askEmailAgain;
                    do {
                        System.out.println("New Email: (Cancel by entering your current email)");
                        String newEmail = scan.nextLine();
                        if (newEmail.equals(user.getEmail())) { // Cancel by typing your current email
                            break;
                        }
                        Query validateEmailQuery = BookApp.computeQuery(new String[]{newEmail}, "users", "validate " +
                                "email");
                        askEmailAgain = !(boolean) validateEmailQuery.getObject();
                        if (!askEmailAgain) {
                            Query setEmailQuery = BookApp.updateQuery(BookApp.currentUser, "users", "email", newEmail);
                            if (setEmailQuery.getObject().equals(false)) {
                                System.out.println("Whoops: Couldn't set your new email");
                                break;
                            }
                            user.setEmail(newEmail);
                        } else {
                            System.out.println("Whoops: Couldn't set your new email");
                            System.out.println("Email is either taken, or is invalid");
                        }
                    } while (askEmailAgain);
                    break;
                case "3":
                    System.out.println("Confirm by entering your password...");
                    String confirmPassword = scan.nextLine();
                    System.out.println("Validating...");
                    try {
                        Thread.sleep(1000); // For dramatic effect
                    } catch (InterruptedException e) {
                        System.out.println("Error: Program interruption");
                    }

                    String hashedPassword = User.hashPassword(confirmPassword);
                    if (hashedPassword == null) {
                        return false;
                    }

                    if (hashedPassword.equals(user.getPassword())) {
                        boolean askPasswordAgain;
                        do {
                            System.out.println("New Password:");
                            String newPassword = scan.nextLine();
                            askPasswordAgain = newPassword.isEmpty();
                            if (!askPasswordAgain) {
                                hashedPassword = User.hashPassword(newPassword);
                                if (hashedPassword == null) {
                                    return false;
                                }
                                Query setPassQuery = BookApp.updateQuery(BookApp.currentUser, "users", "password",
                                        new String[]{hashedPassword, newPassword});
                                if (setPassQuery.getObject().equals(false)) {
                                    System.out.println("Whoops: Couldn't set your new password");
                                    break;
                                }
                                user.setPassword(hashedPassword, newPassword);
                            }
                        } while (askPasswordAgain);
                    } else {
                        System.out.println("Invalid password");
                        System.out.println("Press Enter to continue...");
                        scan.nextLine();
                    }

                    break;
                case "4": // Delete Account
                    System.out.println("Confirm by entering your password...");
                    String confirmPassword2 = scan.nextLine();
                    System.out.println("Validating...");
                    try {
                        Thread.sleep(1000); // For dramatic effect
                    } catch (InterruptedException e) {
                        System.out.println("Error: Program interruption");
                    }
                    String hashedPassword2 = User.hashPassword(confirmPassword2);
                    if (hashedPassword2 == null) {
                        return false;
                    }
                    if (hashedPassword2.equals(user.getPassword())) {
                        // Remove from marketplace and sign out
                        Query deleteUser = BookApp.deleteQuery(user, "users");
                        if (deleteUser.getObject().equals(false)) {
                            System.out.println("Whoops: Couldn't delete your account. Please try again");
                        }
                        return false;
                    } else {
                        System.out.println("Invalid password");
                        System.out.println("Press Enter to continue...");
                        scan.nextLine();
                    }
                    break; // Does to main account menu
                case "5": // Done
                    return true;
                default:
                    System.out.println("Please choose an option 1 to 5");
                    System.out.println("Press Enter to continue...");
                    scan.nextLine();
            }
        } while (true);
    }
}
