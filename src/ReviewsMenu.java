import Objects.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ReviewsMenu {

    /**
     * This presents a menu for leaving a review on a store
     * @param scan The scanner used to prompt the user
     * @param user The buyer leaving the review
     * @param store The store the user is leaving a review for
     */
    // TODO: add this as an option when viewing a store (as a buyer)
    public void leaveReview(Scanner scan, User user, Store store) {
        System.out.println("*******************");

        while (store == null) {
            System.out.println("Whoops: You have not entered a valid store");
            System.out.println("Try entering the stores name again: ");
            String storeName = scan.nextLine();
            store = BookApp.marketplace.getStoreByName(storeName);
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
                    Seller seller = (Seller) BookApp.marketplace.getUserByUsername(store.getSellerName());
                    if (store.getReviews() == null) {
                        store.setReviews(new ArrayList<>());
                    }
                    store.getReviews().add(new Review(rating, buyer, seller, heading, description));
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

    public void viewStoreReviews(Scanner scan) {

    }
}
