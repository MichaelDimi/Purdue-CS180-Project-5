package Client;

import GUI.Customer;
import GUI.Login;
import GUI.SellerGUI;
import GUI.Start;
import Objects.*;
import Query.*;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class contains the main method and is the
 * center of our program. It runs on a loop until the
 * user exits.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class BookApp {
//    public static Marketplace marketplace;

    public static User currentUser;

    public static void main(String[] args) {
        //Scanner scan = new Scanner(System.in);
        SwingUtilities.invokeLater(new Start());
//        do {
//
//            String loginSignup;
//            boolean validUser = false;
//            do {
//                currentUser = null;
//                System.out.println("1. Login\n2. Sign Up\n3. EXIT");
//                loginSignup = scan.nextLine();
//                if (!loginSignup.equals("1") && !loginSignup.equals("2") && !loginSignup.equals("3")) {
//                    System.out.println("Whoops: Please enter (1), (2), or (3)");
//                    continue;
//                }
//
//                if (loginSignup.equals("1")) {
//                    LoginMenu loginMenu = new LoginMenu();
//                    validUser = loginMenu.present(scan); // if false, return to login or signup
//                } else if (loginSignup.equals("2")) {
//                    SignUpMenu signUpMenu = new SignUpMenu();
//                    validUser = signUpMenu.present(scan); // Ignore this if we are signing up
//                } else {
//                    System.out.println("Thanks for using " + "DELETE THIS" + "\nGoodbye!");
//                    return;
//                }
//            } while (!validUser);
//
//            // Main loop
//            do {
//                if (currentUser == null) {
//                    break; // Exit to the login loop
//                }
//
//                // Update the current user
//                Query updateUserQuery = new ClientQuery().getQuery(currentUser, "users", "currentUser");
//                if (updateUserQuery.getObject() == null || updateUserQuery.getObject().equals(false)) {
//                    break;
//                }
//                currentUser = (User) updateUserQuery.getObject();
//
//                if (currentUser instanceof Buyer) {
//                    CustomerHomepage customerHomepage = new CustomerHomepage();
//                    boolean mainMenu = customerHomepage.present(scan);
//                    if (!mainMenu) {
//                        break;
//                    }
//                } else if (currentUser instanceof Seller) {
//                    SellerHomepage sellerHomepage = new SellerHomepage();
//                    boolean mainMenu = sellerHomepage.present(scan);
//                    if (!mainMenu) {
//                        break;
//                    }
//                } else {
//                    break;
//                }
//
//            } while (true); // Main loop
//
//        } while (true); // Login or Sign up Loop
    }

    public static void displayHomepage() {
        Scanner scan = new Scanner(System.in);
        if (currentUser instanceof Buyer) {
//            CustomerHomepage customerHomepage = new CustomerHomepage();
//            boolean mainMenu = customerHomepage.present(scan);
            SwingUtilities.invokeLater(new Customer()); // WILL NOT WORK WITH SCANNERS
        } else if (currentUser instanceof Seller) {
            //JOptionPane.showMessageDialog(null, "Is seller");
//            SellerHomepage sellerHomepage = new SellerHomepage();
//            boolean mainMenu = sellerHomepage.present(scan);
            SwingUtilities.invokeLater(new SellerGUI());
        }
    }

    // updates current user with the most up-to-date info
    public static void updateCurrentUser() {
        Query updateUserQuery = new ClientQuery().getQuery(currentUser, "users", "currentUser");
        if (updateUserQuery.getObject() == null || updateUserQuery.getObject().equals(false)) {
            return;
        }
        currentUser = (User) updateUserQuery.getObject();
    }

    // returns array of Stores sorted by the amount of unique products they sell
    public static Store[] sortStoresByVarietyOfProducts(Store[] stores) {
        int n = stores.length;

        Store[] storeArr = stores;

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (stores[j].getStock().size() < stores[j+1].getStock().size()) {
                    Store temp = storeArr[j];
                    storeArr[j] = storeArr[j+1];
                    storeArr[j+1] = temp;
                }
            }
        }

        return storeArr;
    }

    // returns array of Stores sorted by the frequency of purchases from store
    public static Store[] sortStoreByMostFrequentPurchases(Store[] stores) {
        int n = stores.length;

        Store[] storeArr = stores;

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (getNumPurchasesFromStore((Buyer) currentUser, stores[j])
                        < getNumPurchasesFromStore((Buyer) currentUser, stores[j + 1])) {
                    Store temp = storeArr[j];
                    storeArr[j] = storeArr[j+1];
                    storeArr[j+1] = temp;
                }
            }
        }

        return storeArr;
    }

    // returns an int with the number of purchases the current user has made at specified store
    public static int getNumPurchasesFromStore(Buyer buyer, Store store) {
        int purchaseCount = 0;
        for (Book book : buyer.getPurchaseHistory().keySet()) {
            if (book.getStore().equals(store.getName())) {
                purchaseCount += buyer.getPurchaseHistory().get(book);
            }
        }

        return purchaseCount;
    }

    public static void sortBooksByPrice(Book[] books) {
        int n = books.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books[j].finalPrice() > books[j + 1].finalPrice()) {
                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }
    }

    public static Book[] sortBooksByQuantity(HashMap<Book, Integer> books) {
        int n = books.size();

        Book[] booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(booksArr[j]) < books.get(booksArr[j + 1])) {
                    Book temp = booksArr[j];
                    booksArr[j] = booksArr[j + 1];
                    booksArr[j + 1] = temp;
                }
            }
        }

        return booksArr;
    }

    public static void signOut() {
        currentUser = null;
        SwingUtilities.invokeLater(new Start());
    }
}


