import Objects.*;

import java.util.Scanner;

public class AccountMenu extends Menu {

    @Override
    public boolean present(Scanner scan) {
        // The beginning of each menu should have some indicator
        // I've gone with stars, but we can change later
        System.out.println("*******************");
        User user = BookApp.marketplace.getCurrentUser();

        do {

            System.out.printf(
                    "You are a %s. Create a new account to become a %s.\n",
                    user instanceof Buyer ? "BUYER" : "SELLER",
                    user instanceof Buyer ? "SELLER" : "BUYER"
                    );
            System.out.println("Username: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassword());
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
                        askUsernameAgain = !BookApp.marketplace.validateName(newName);
                        if (!askUsernameAgain) {
                            BookApp.marketplace.getCurrentUser().setName(newName);
                            BookApp.marketplace.saveMarketplace();
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
                        askEmailAgain = !BookApp.marketplace.validateEmail(newEmail);
                        if (!askEmailAgain) {
                            BookApp.marketplace.getCurrentUser().setEmail(newEmail);
                            BookApp.marketplace.saveMarketplace();
                        }
                    } while (askEmailAgain);
                    break;
                case "3":
                    boolean askPasswordAgain;
                    do {
                        System.out.println("New Password:");
                        String newPassword = scan.nextLine();
                        askPasswordAgain = newPassword.isEmpty();
                        if (!askPasswordAgain) {
                            BookApp.marketplace.getCurrentUser().setPassword(newPassword);
                            BookApp.marketplace.saveMarketplace();
                        }
                    } while (askPasswordAgain);
                    break;
                case "4": // Delete Account

                    System.out.println("Confirm by entering your password...");
                    String confirmPassword = scan.nextLine();
                    System.out.println("Validating...");
                    try {
                        Thread.sleep(1000); // For dramatic effect
                    } catch (InterruptedException e) {
                        System.out.println("Error: Program interruption");
                    }
                    if (confirmPassword.equals(user.getPassword())) {
                        // Remove from marketplace and sign out
                        BookApp.marketplace.getUsers().remove(user);
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
