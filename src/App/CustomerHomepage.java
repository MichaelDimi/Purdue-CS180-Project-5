package App;

import Objects.Book;
import Objects.Marketplace;
import Objects.*;
import java.io.*;
import java.util.*;

public class CustomerHomepage extends Menu {
    /**
     * Displays homepage view for buyer type user.
     * 1. Access Stores
     *      1.1 View list of stores and choose from them
     *      1.2 View products from chosen store
     *      1.3 View product (Can add to cart from here)
     * 2. View purchase history
     * 3. Search products (name, store or description)
     *      3.1 sort products on price or quantity
     * 4. Shopping cart
     */
    public boolean present(Scanner scan) {
        System.out.println("CUSTOMER HOME");
        System.out.println("*******************");

        String choice;
        do {
            System.out.println("What would you like to do?\n" +
                    "1. Purchase a book\n" +
                    "2. Search for a book\n" +
                    "3. View Store's Inventory or Reviews\n" +
                    "4. Leave a review\n" +
                    "5. View / Export Purchase History\n" +
                    "6. View Shopping Cart\n" +
                    "7. Edit Account\n" +
                    "8. SIGN OUT");
            choice = scan.nextLine();
            if (!"12345678".contains(choice)) {
                System.out.println("Whoops: Please enter a number from 1-5");
            }
        } while (!"12345678".contains(choice));

        Buyer buyer = (Buyer) BookApp.marketplace.getCurrentUser();

        if (choice.equalsIgnoreCase("1")) {
            System.out.println("PURCHASING A BOOK");
            System.out.println("*******************");
            HashMap<Book, Integer> books = BookApp.marketplace.getBooks();

            // Convert the hashmap to an array, since its easier to manipulate
            Book[] booksArr = new Book[books.size()];
            booksArr = books.keySet().toArray(booksArr);

            System.out.println("How would you like to view the books?\n" +
                    "1. Sorted by price\n" +
                    "2. Sorted by quantity\n" +
                    "3. Not sorted");
            choice = scan.nextLine();
            if (choice.equals("1")) { // Lowest to highest
                Marketplace.sortBooksByPrice(booksArr);
            } else if (choice.equals("2")) {
                booksArr = Marketplace.sortBooksByQuantity(books);
            }

            System.out.println("Select a book to buy:");
            int i = 1;
            for (Book book : booksArr) { //Printing list of books available for sale
                book.printBookListItem(i, books.get(book));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Book selection;
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                selection = booksArr[response-1];
            }

            System.out.println(selection); // Aaron, you can use this for buying a book

            // TODO: Add buying a book here (2 more spots below) (may want to abstract as much as possible)
        } else if (choice.equals("2")) {
            System.out.println("SEARCH FOR A BOOK");
            System.out.println("*******************");
            System.out.println("Enter search query: ");
            System.out.println("- Note the query will be used to search for name, genre, and description.");
            String query = scan.nextLine();
            HashMap<Book, Integer> books = BookApp.marketplace.findBooks(query);

            // Convert the hashmap to an array, since its easier to manipulate
            Book[] booksArr = new Book[books.size()];
            booksArr = books.keySet().toArray(booksArr);

            System.out.println("Select a book to buy:");
            int i = 1;
            for (Book book : booksArr) { //Printing list of books available for sale
                book.printBookListItem(i, books.get(book));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Book selection;
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                selection = booksArr[response - 1];
            }

            System.out.println(selection); // Aaron, you can use this for buying a book

            // TODO: Add buying a book here (one more below)

        } else if (choice.equals("3")) {
            System.out.println("VIEW STORES");
            System.out.println("*******************");

            ArrayList<Store> stores = BookApp.marketplace.getStores();
            // Convert the arraylist to an array, since its easier to manipulate
            Store[] storesArr = new Store[stores.size()];
            storesArr = stores.toArray(storesArr);

            if (storesArr.length < 1) {
                System.out.println("There are no stores in the market yet");
                System.out.println("Create an new account and become a seller to start a store");
                return true;
            }

            System.out.println("Select a store to see their books or reviews:");
            int i = 1;
            for (Store store : storesArr) { //Printing list of books available for sale
                System.out.println(i + ". " + store.getName() + " -- Owner: " + store.getSellerName() + " -- Rating:" + " " + Review.starDisplay(store.getAverageRating()));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Store storeSelected;
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                storeSelected = storesArr[response - 1];
            }

            String option;
            do {
                System.out.println("Do you want to see the store's inventory or reviews?");
                System.out.println("1. Stock");
                System.out.println("2. Reviews");
                System.out.println("3. CANCEL");
                option = scan.nextLine();
            } while (!option.equals("1") && !option.equals("2") && !option.equals("3"));

            if (option.equals("3")) {
                return true;
            } else if (option.equals("1")) {
                HashMap<Book, Integer> stock = storeSelected.getStock();

                // Convert the hashmap to an array, since its easier to manipulate
                Book[] stockArr = new Book[stock.size()];
                stockArr = stock.keySet().toArray(stockArr);

                System.out.println("Select a book to buy:");
                int j = 1;
                for (Book book : stockArr) { //Printing list of books available for sale
                    book.printBookListItem(i, stock.get(book));
                    j++;
                }
                System.out.println(i + ". BACK");

                int bookSelectedIndex = Menu.selectFromList(j, scan);

                Book selectedBook;
                if (bookSelectedIndex == j) {
                    BookApp.marketplace.saveMarketplace();
                    return true; // Go back
                } else {
                    selectedBook = stockArr[bookSelectedIndex - 1];
                }

                System.out.println(selectedBook); // Aaron, you can use this for buying a book

                // TODO: Add buying a book here
            } else {
                // REVIEWS
                ReviewsMenu reviewsMenu = new ReviewsMenu();
                reviewsMenu.viewStoreReviews(scan, storeSelected);
            }

        } else if (choice.equals("4")) {
            System.out.println("LEAVE A REVIEW");
            System.out.println("*******************");

            ArrayList<Store> stores = BookApp.marketplace.getStores();
            // Convert the arraylist to an array, since its easier to manipulate
            Store[] storesArr = new Store[stores.size()];
            storesArr = stores.toArray(storesArr);

            if (storesArr.length < 1) {
                System.out.println("There are no stores in the market yet");
                System.out.println("You can create an new account and become a seller to open a store");
                return true;
            }

            System.out.println("Select a store:");
            int i = 1;
            for (Store store : storesArr) { //Printing list of books available for sale
                System.out.println(i + ". " + store.getName() + " -- Owner: " + store.getSellerName() + " -- Rating:" + " " + Review.starDisplay(store.getAverageRating()));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Store storeSelected;
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                storeSelected = storesArr[response - 1];
            }

            ReviewsMenu reviewsMenu = new ReviewsMenu();
            reviewsMenu.leaveReview(scan, buyer, storeSelected);

        } else if (choice.equals("5")) { // TODO: Come back to test this after buying is done
            System.out.println("YOUR PURCHASE HISTORY");
            System.out.println("*******************");
            HashMap<Book, Integer> purchaseHistory = buyer.getPurchaseHistory();
            if (purchaseHistory.isEmpty()) {
                System.out.println("You have never bought anything");
                BookApp.marketplace.saveMarketplace();
                return true;
            }
            for (Book book : purchaseHistory.keySet()) {
                book.printBookListItem(null, purchaseHistory.get(book));
            }
            System.out.println("Would you like to export your purchase history? (Y)");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                FileIOMenu fileIOMenu = new FileIOMenu();
                fileIOMenu.fileIOMenu(scan, buyer);
                BookApp.marketplace.saveMarketplace();
            }

        } else if (choice.equals("6")) { // TODO: Come back to test this after buying is done
            System.out.println("YOUR CART");
            System.out.println("*******************");
            HashMap<Book, Integer> cart = buyer.getCart();
            if (cart.isEmpty()) {
                System.out.println("Your cart is empty");
                BookApp.marketplace.saveMarketplace();
                return true;
            }

            for (Book book : cart.keySet()) {
                book.printBookListItem(null, cart.get(book));
            }
        } else if (choice.equals("7")) {
            System.out.println("YOUR ACCOUNT MENU");
            AccountMenu accountMenu = new AccountMenu();
            boolean accountMenuResult = accountMenu.present(scan); // If false, sign out
            if (!accountMenuResult) {
                // Sign out
                BookApp.marketplace.setCurrentUser(null);
                BookApp.marketplace.saveMarketplace();
                return false; // Should break main loop
            }
        } else if (choice.equals("8")) {
            return false;
        }
        BookApp.marketplace.saveMarketplace();
        return true;
    }
}