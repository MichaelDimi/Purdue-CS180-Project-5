package App;

import Objects.*;

import java.util.Objects;
import java.util.Scanner;

public class BookApp {

    public static Marketplace marketplace;

    public static String appName = "BOOK APP"; // TODO: make a pun-y name

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        BookApp.marketplace = new Marketplace();

        System.out.println("WELCOME to " + appName);
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        // Login or Sign up Loop TODO: add exit
        do {
            System.out.println(marketplace.getUsers()); // TODO: REMOVE THIS (useful for logging in correctly)

            String loginSignup;
            boolean validUser = false;
            do {
                System.out.println("1. Login\n2. Sign Up\n3. EXIT");
                loginSignup = scan.nextLine();
                if (!Objects.equals(loginSignup, "1") && !Objects.equals(loginSignup, "2") && !Objects.equals(loginSignup, "3")) {
                    System.out.println("Whoops: Please enter (1), (2), or (3)");
                    continue;
                }

                if (loginSignup.equals("1")) {
                    LoginMenu loginMenu = new LoginMenu();
                    validUser = loginMenu.present(scan); // if false, return to login or signup
                } else if (loginSignup.equals("2")) {
                    SignUpMenu signUpMenu = new SignUpMenu();
                    validUser = signUpMenu.present(scan); // Ignore this if we are signing up
                } else {
                    System.out.println("Thanks for using " + appName + "\nGoodbye!");
                    return;
                }
            } while (!validUser);

            // Seller test
//            Seller newSeller = (Seller) marketplace.getCurrentUser();
//            newSeller.editStore();

            marketplace.saveMarketplace();

            // Main loop
            do {
                User currentUser = marketplace.getCurrentUser();

                // FILE IO
                FileIOMenu fileIOMenu = new FileIOMenu();
//                fileIOMenu.fileIOMenu(scan, currentUser);
                marketplace.saveMarketplace();

                // REVIEWS MENUS
//                marketplace.addToUsers(new Seller("seller", "asd@asd.asd", "blah"));
                ReviewsMenu reviewsMenu = new ReviewsMenu();
//                Seller seller = (Seller) marketplace.getUserByUsername("seller");
//                seller.getStores().add(new Store("Store 1", seller.getName()));
//                Store store = marketplace.getStoreByName("Store 1");
//                reviewsMenu.leaveReview(scan, currentUser, store);

                marketplace.saveMarketplace();

//                marketplace.addToUsers(new Seller("seller", "asd@asd.asd", "blah"));
//                Seller seller = (Seller) marketplace.getUserByUsername("seller");
//                seller.getStores().add(new Store("Store 1", seller.getName()));
//                Store store = marketplace.getStoreByName("Store 1");
//                reviewsMenu.viewStoreReviews(scan, store);

                SalesMenu salesMenu = new SalesMenu();
//                Seller seller = (Seller) currentUser;
//                salesMenu.createSale(scan, currentUser);

                marketplace.saveMarketplace();

                // ACCOUNT MENU
                AccountMenu accountMenu = new AccountMenu();
                boolean accountMenuResult = accountMenu.present(scan); // If false, sign out
                if (!accountMenuResult) {
                    // Sign out
                    marketplace.setCurrentUser(null);
                    BookApp.marketplace.saveMarketplace();
                    break; // Should break main loop
                }

                marketplace.saveMarketplace();

            } while (true); // Main loop
        } while (true); // Login or Sign up Loop
    }
}


