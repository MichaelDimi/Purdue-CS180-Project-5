package Client;

import Objects.Buyer;
import Objects.Seller;
import Objects.User;
import Query.*;

import javax.swing.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class contains the menu in which
 * the user can create an account that can
 * be a buyer or seller
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class SignUpMenu extends Menu {

    public boolean present(String username, String email, String password, String buyerSeller) {
        boolean isBuyer = !buyerSeller.equals("Seller");

        // Add user
        Query validateAddQuery = new ClientQuery().updateQuery(null, "users", "add", new String[]{username, email, password, isBuyer ? "T" : "F"});
        if (validateAddQuery.getObject().equals(false)) {
            if (validateAddQuery.getOptions().equals("validation err")) {
                JOptionPane.showMessageDialog(null, "Account already exists",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (validateAddQuery.getOptions().equals("hash err")) {
                JOptionPane.showMessageDialog(null, "Whoops: Unable to hash password",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Whoops: Unable to create your account. Please try again",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            System.out.println("Validation successful!");
            // logs user in after signing up; uses get query to get user with the username of the just created account from server
            BookApp.currentUser = (User) new ClientQuery().getQuery(username, "users", "name").getObject();
        }

        return true;
    }
}
