import Classes.Buyer;
import Classes.Seller;
import Classes.User;

import java.util.Objects;
import java.util.Scanner;

public class SignUpMenu extends Menu {

    @Override
    public void present(Scanner scan) {

        String[] input = Menu.validateLoginSignUpInput(scan);

        String username = input[0];
        String email = input[1];
        String password = input[2];

        boolean isBuyer;
        do {
            System.out.println("Are you a\n1. Buyer\n2. Seller");
            String buyerSeller = scan.nextLine();
            if (Objects.equals(buyerSeller, "1") || Objects.equals(buyerSeller, "2")) {
                isBuyer = Objects.equals(buyerSeller, "1");
                break;
            }
            System.out.println("Whoops: Please enter (1) or (2)");
        } while(true);

        // Saves to marketplace and logs the user in
        User newUser;
        if (isBuyer) {
            newUser = new Buyer(username, email, password);
        } else {
            newUser = new Seller(username, email, password);
        }

        BookApp.marketplace.addToUsers(newUser);
        BookApp.marketplace.setCurrentUser(newUser);
    }
}
