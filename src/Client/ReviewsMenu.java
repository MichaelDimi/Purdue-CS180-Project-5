package Client;

import Objects.*;
import Query.*;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class contains the menu in which a buyer
 * can create a review of a certain store.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class ReviewsMenu {

    /**
     * This presents a menu for leaving a review on a store
     * @param scan The scanner used to prompt the user
     * @param user The buyer leaving the review
     * @param store The store the user is leaving a review for
     */
    public void leaveReview(Scanner scan, User user, Store store) {
        System.out.println("*******************");

        while (store == null) {
            System.out.println("Whoops: You have not entered a valid store");
            System.out.println("Try entering the stores name again or type 'CANCEL' to exit: ");
            String storeName = scan.nextLine();
            if (storeName.equals("CANCEL")) {
                return;
            }
            Query storeQuery = BookApp.getQuery(storeName, "stores", "name");
            if (!(storeQuery.getObject() instanceof Store) || storeQuery.getObject().equals(false)) {
                System.out.println("Whoops: We couldn't get that store from the server");
                return;
            }
            store = (Store) storeQuery.getObject();
        }

        if (user instanceof Seller) {
            System.out.println("Whoops: Sellers cannot leave reviews");
            return;
        }

        Buyer buyer = (Buyer) user;

        String heading;
        do {
            System.out.println("*Add a headline: ");
            heading = scan.nextLine();
            if (heading.isEmpty()) {
                System.out.println("Whoops: Please include a headline");
            } else {
                break;
            }
        } while (true);

        System.out.println("Add a description: ");
        String description = scan.nextLine();

        int rating;
        do {
            System.out.println("*Add a rating out of 5: ");
            try {
                rating = Integer.parseInt(scan.nextLine());
                if (rating < 0 || rating > 5) {
                    System.out.println("Whoops: Your rating must be an integer 0 through 5");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Whoops: Your rating must be an integer 0 through 5");
            }
        } while (true);

        do {
            System.out.println("Are you sure you want to add this review (Y/N): ");
            String response = scan.nextLine();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n")) {
                if (response.equalsIgnoreCase("y")) {
                    Query sellerQuery = BookApp.getQuery(store.getSellerName(), "users", "name");
                    if (!(sellerQuery.getObject() instanceof Seller)) {
                        System.out.println("Whoops: Couldn't get the owner of this store");
                        System.out.println("Please go back and try again");
                        break;
                    }
                    Seller seller = (Seller) sellerQuery.getObject();
                    if (store.getReviews() == null) {
                        store.setReviews(new ArrayList<>());
                    }
                    store.getReviews().add(new Review(rating, buyer, seller.getName(), heading, description));
                    System.out.println("Uploading review...");
                    try {
                        Thread.sleep(1000); // For dramatic effect
                    } catch (InterruptedException e) {
                        System.out.println("Error: Program interruption");
                    }
                    System.out.println("Review Added Successfully!");
                }
                break;
            }
            System.out.println("Whoops: Please enter 'Y' or 'N' to confirm");
        } while (true);
    }

    /**
     * This presents the menu for viewing stores' reviews
     * @param scan The scanner used for getting input and navigating the menu
     * @param store The store for the review (can be null)
     */
    public void viewStoreReviews(Scanner scan, Store store) {
        System.out.println("*******************");

        while (store == null) {
            System.out.println("Whoops: You have not entered a valid store");
            System.out.println("Try entering the stores name again or type 'CANCEL' to exit: ");
            String storeName = scan.nextLine();
            if (storeName.equals("CANCEL")) {
                return;
            }
            Query storeQuery = BookApp.getQuery(storeName, "stores", "name");
            if (!(storeQuery.getObject() instanceof Store) || storeQuery.getObject().equals(false)) {
                System.out.println("Whoops: We couldn't get that store from the server");
                return;
            }
            store = (Store) storeQuery.getObject();
        }

        if (store.getReviews() == null || store.getReviews().isEmpty()) {
            System.out.println("Whoops: Looks like this store has no reviews");
            System.out.println("Would you like to add a review? (Y/N)");
            String response = scan.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                leaveReview(scan, BookApp.currentUser, store);
            } else {
                return;
            }
        }

        if (store.getReviews() == null) {
            System.out.println("Whoops: Couldn't display reviews");
            return;
        } else if (store.getReviews().isEmpty()) {
            System.out.println("This store still has no reviews");
            return;
        }

        Review[] reviews = new Review[store.getReviews().size()];
        reviews = store.getReviews().toArray(reviews);

        do {
            System.out.println("REVIEWS FOR: " + store.getName());

            for (int i = 1; i <= reviews.length; i++) {
                Review r = reviews[i - 1]; // zero index
                System.out.printf("%d. %s %s\n", i, r.getTitle(), Review.starDisplay(r.getRating()));
            }
            System.out.println(reviews.length + 1 + ". EXIT");

            int option;
            do {
                System.out.println("Select a review to see more:");

                try {
                    option = Integer.parseInt(scan.nextLine());
                    if (option >= 1 && option <= reviews.length + 1) {
                        break;
                    } else {
                        if (reviews.length == 1) {
                            System.out.println("Whoops: Must be (1) or (2)");
                        } else {
                            System.out.printf("Whoops: Must be (1) -> (%d)\n", reviews.length + 1);
                        }
                    }
                } catch (NumberFormatException e) {
                    if (reviews.length == 1) {
                        System.out.println("Whoops: Must be (1) or (2)");
                    } else {
                        System.out.printf("Whoops: Must be (1) -> (%d)\n", reviews.length + 1);
                    }
                }
            } while (true);

            int exitInt = reviews.length + 1;
            if (option == exitInt) {
                return;
            }
            System.out.println("===================");
            System.out.println(reviews[option - 1].print());
            System.out.println("Press ENTER to go back");
            scan.nextLine();
        } while (true);
    }
}
