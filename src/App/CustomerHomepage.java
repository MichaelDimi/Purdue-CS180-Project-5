package App;

import Exceptions.BookNotFoundException;
import Objects.Book;
import Objects.Marketplace;
import Objects.*;

import java.util.*;

/**
* This class contains the menu in which a buyer
* can search for books, buy books ,view their
* shopping cart and purchase history
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

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

        Buyer buyer = (Buyer) BookApp.marketplace.getCurrentUser();

        // stores the number of items in user's cart
        int cartCount = 0;
        for (Book book : buyer.getCart().keySet())
            cartCount += buyer.getCart().get(book);

        String choice;
        do {
            System.out.println("What would you like to do?\n" +
                    "1. Purchase a Book\n" +
                    "2. Search for a Book\n" +
                    "3. View List of Stores / Store's Inventory or Reviews\n" +
                    "4. Leave a Review\n" +
                    "5. View / Export Purchase History\n" +
                    "6. Your Shopping Cart (" + cartCount + ")\n" +
                    "7. Edit Account\n" +
                    "8. SIGN OUT");
            choice = scan.nextLine();
            if (!"12345678".contains(choice)) {
                System.out.println("Whoops: Please enter a number from 1-5");
            }
        } while (!"12345678".contains(choice));

        if (choice.equalsIgnoreCase("1")) {
            System.out.println("PURCHASE A BOOK");
            System.out.println("*******************");
            HashMap<Book, Integer> books = BookApp.marketplace.getBooks();

            // Convert the hashmap to an array, since its easier to manipulate
            Book[] booksArr = new Book[books.size()];
            booksArr = books.keySet().toArray(booksArr);

            if (books.isEmpty()) {
                System.out.println("There are no books for sale");
                System.out.println("Create a new account and become a seller to start selling books");
                BookApp.marketplace.saveMarketplace();
                return true;
            }

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
            // if the back option is selected
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                // book to be bought
                selection = booksArr[response-1];
            }

            // shows more details about selected book and asks user how many copies of book to buy
            selectedBookMenu(scan, selection, buyer);

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
            // if the back option is selected
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                // book to be bought
                selection = booksArr[response - 1];
            }

            // shows more details about selected book and asks user how many copies of book to buy
            selectedBookMenu(scan, selection, buyer);
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
                BookApp.marketplace.saveMarketplace();
                return true;
            }

            System.out.println("How would you like to sort stores?");
            System.out.println("1. Most number of products offered");
            System.out.println("2. Least number of products offered");
            System.out.println("3. Your most frequently shopped at");
            System.out.println("4. Your least frequently shopped at");
            System.out.println("5. Not sorted");

            // store index - used to make numbered list
            int i = 1;

            // displays all books
            String sortSelection;
            do {
                sortSelection = scan.nextLine();

                System.out.println("Select a store to see their books or reviews:");

                // Books sorted by quantity sold
                Store[] sortedStore;
                switch (sortSelection) {
                    case "1":
                        sortedStore = Marketplace.sortStoresByVarietyOfProducts(stores);
                        // sorted by most number of products offered
                        for (Store store : sortedStore) { //Printing list of books available for sale
                            System.out.println(i + ". " + store.getName() + " -- Owner: " + store.getSellerName() + " -- Rating: " + Review.starDisplay(store.getAverageRating()) + " -- Products Offered: " + store.getStock().size());
                            i++;
                        }

                        // updates the store array with the sorted one
                        storesArr = sortedStore;
                        break;
                    case "2":
                        sortedStore = Marketplace.sortStoresByVarietyOfProducts(stores);
                        // sorted by least number of products offered
                        // prints the sorted array in reverse
                        for (int k = sortedStore.length - 1; k >= 0; k--) {
                            // updates store array with reversed sorted array
                            storesArr[sortedStore.length - k - 1] = sortedStore[k];
                            System.out.println(i + ". " + sortedStore[k].getName() + " -- Owner: " + sortedStore[k].getSellerName() + " -- Rating: " + Review.starDisplay(sortedStore[k].getAverageRating()) + " -- Products Offered: " + sortedStore[k].getStock().size());
                            i++;
                        }

                        break;
                    case "3":
                        sortedStore = Marketplace.sortStoreByMostFrequentPurchases(buyer, stores);
                        // sorted by your most frequently shopped at
                        for (Store store : sortedStore) { //Printing list of books available for sale
                            System.out.println(i + ". " + store.getName() + " -- Owner: " + store.getSellerName() + " -- Rating: " + Review.starDisplay(store.getAverageRating()) + " -- Purchases Made at Store: " + Marketplace.getNumPurchasesFromStore(buyer, store));
                            i++;
                        }

                        // updates the store array with the sorted one
                        storesArr = sortedStore;
                        break;
                    case "4":
                        sortedStore = Marketplace.sortStoreByMostFrequentPurchases(buyer, stores);
                        // sorted by your least frequently shopped at
                        // prints the sorted array in reverse
                        for (int k = sortedStore.length - 1; k >= 0; k--) {
                            // updates store array with reversed sorted array
                            storesArr[sortedStore.length - k - 1] = sortedStore[k];
                            System.out.println(i + ". " + sortedStore[k].getName() + " -- Owner: " + sortedStore[k].getSellerName() + " -- Rating: " + Review.starDisplay(sortedStore[k].getAverageRating()) + " -- Purchases Made at Store: " + Marketplace.getNumPurchasesFromStore(buyer, sortedStore[k]));
                            i++;
                        }

                        break;
                    case "5":
                        // no sort
                        for (Store store : storesArr) { //Printing list of books available for sale
                            System.out.println(i + ". " + store.getName() + " -- Owner: " + store.getSellerName() + " -- Rating: " + Review.starDisplay(store.getAverageRating()));
                            i++;
                        }
                        break;
                }
            } while (!"12345".contains(sortSelection));

            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Store storeSelected;
            if (response == i) {
                BookApp.marketplace.saveMarketplace();
                return true; // Go back
            } else {
                storeSelected = storesArr[response - 1];
            }

            System.out.println("Selected store: " + storeSelected.getName());

            String option;
            do {
                System.out.println("Do you want to see the store's inventory or reviews?");
                System.out.println("1. Stock");
                System.out.println("2. Reviews");
                System.out.println("3. CANCEL");
                option = scan.nextLine();
            } while (!option.equals("1") && !option.equals("2") && !option.equals("3"));

            if (option.equals("3")) {
                BookApp.marketplace.saveMarketplace();
                return true;
            } else if (option.equals("1")) {
                HashMap<Book, Integer> stock = storeSelected.getStock();

                // Convert the hashmap to an array, since its easier to manipulate
                Book[] stockArr = new Book[stock.size()];
                stockArr = stock.keySet().toArray(stockArr);

                System.out.println("Select a book to buy:");
                int j = 1;
                for (Book book : stockArr) { //Printing list of books available for sale
                    book.printBookListItem(j, stock.get(book));
                    j++;
                }
                System.out.println(j + ". BACK");

                int bookSelectedIndex = Menu.selectFromList(j, scan);

                Book selection;
                // if the back option is selected
                if (bookSelectedIndex == j) {
                    BookApp.marketplace.saveMarketplace();
                    return true; // Go back
                } else {
                    // book to be bought
                    selection = stockArr[bookSelectedIndex - 1];
                }

                // shows more details about selected book and asks user how many copies of book to buy
                selectedBookMenu(scan, selection, buyer);
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
                BookApp.marketplace.saveMarketplace();
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
            System.out.println("Would you like to export your purchase history? (Y/N)");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                FileIOMenu fileIOMenu = new FileIOMenu();
                fileIOMenu.fileIOMenu(scan, buyer);
                BookApp.marketplace.saveMarketplace();
            }

        } else if (choice.equals("6")) { // TODO: Come back to test this after buying is done
            boolean viewingCart = true;
            while (viewingCart) {
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

                System.out.println("1. Remove items\n" +
                                    "2. Clear Cart\n" +
                                    "3. Checkout\n" +
                                    "4. BACK");
                String cartOptions = scan.nextLine();

                switch (cartOptions) {
                    case "1":
                        // remove from cart

                        // stores all cart book options in an ArrayList
                        ArrayList<Book> booksInCart = new ArrayList<>(cart.keySet());
                        int removeFromCartSelection = -1;
                        do {
                            System.out.println("SELECT BOOK TO REMOVE");
                            System.out.println("*******************");
                            // prints all books in cart with cancel option at the end
                            for (int i = 0; i < booksInCart.size(); i++) {
                                System.out.println((i + 1) + ". " + booksInCart.get(i).getName());
                            }
                            System.out.println((booksInCart.size() + 1) + ". CANCEL");

                            String selectionInput = scan.nextLine();

                            // try checks user inputted a valid number by attempting to parse the string into an int
                            try {
                                removeFromCartSelection = Integer.parseInt(selectionInput);

                                // makes sure user does not input negative number or a number that isn't an option
                                if (removeFromCartSelection < 0 || removeFromCartSelection > booksInCart.size())
                                    throw new NumberFormatException();

                            } catch (NumberFormatException e) {
                                System.out.println("PLEASE ENTER A VALID NUMBER");
                            }


                            // loops until a valid input is entered
                        } while (removeFromCartSelection > cart.size() + 1 || removeFromCartSelection < 0);

                        // checks option selected was not the cancel option and then removes selected book by index
                        if (removeFromCartSelection - 1 != cart.size()) {

                            // loops until a valid input is received
                            int quantityToRemove = -1;
                            while (quantityToRemove < 0) {
                                // asks user for how many books to remove from cart
                                System.out.println("Please input the quantity you would like to remove:");

                                // prompts for the quantity of books to buy
                                String quantityInput = scan.nextLine();

                                // try checks user inputted a valid number by attempting to parse the str into an int
                                try {
                                    quantityToRemove = Integer.parseInt(quantityInput);

                                    // makes sure user does not input negative number
                                    if (quantityToRemove < 0)
                                        throw new NumberFormatException();

                                } catch (NumberFormatException e) {
                                    System.out.println("PLEASE ENTER A VALID NUMBER");
                                }
                            }

                            // removes book from cart
                            try {
                                buyer.removeFromCart(booksInCart.get(removeFromCartSelection - 1), quantityToRemove);
                            } catch (BookNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "2":
                        buyer.clearCart();
                        break;
                    case "3":
                        // checkout
                        System.out.println("Thank you for your purchase!");
                        System.out.println("*******************");
                        // lists off all the books purchased
                        for (Book book : buyer.getCart().keySet()) {
                            book.printBookListItem(null, buyer.getCart().get(book));
                        }
                        buyer.checkoutCart();
                        viewingCart = false;
                        break;
                    case "4":
                        // back
                        viewingCart = false;
                        break;
                }
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
            BookApp.marketplace.saveMarketplace();
            return false;
        }
        BookApp.marketplace.saveMarketplace();
        return true;
    }

    /**
     * Displays more details of the selected book and prompts the user to enter a quantity to buy
     * @param selectedBook the book to be bought
     * @param buyer the user who is buying the book
     */
    public void selectedBookMenu(Scanner scan, Book selectedBook, Buyer buyer) {
        // loops until user inputs a valid quantity of books to buy
        boolean buyingBooks = true;
        while (buyingBooks) {
            // prints more details about the book
            selectedBook.printBookDetails();

            // prompts for the quantity of books to buy
            System.out.println("Please input the quantity you would like to purchase (0 to cancel):");
            String quantityInput = scan.nextLine();

            // try checks that user inputted a valid number by attempting to parse the string into an int
            try {
                //System.out.println(selectedBook);
                int quantityToBuy = Integer.parseInt(quantityInput);

                // makes sure user does not input negative number
                if (quantityToBuy < 0)
                    throw new NumberFormatException();

                // adds books to the buyer's cart
                buyer.addToCart(selectedBook, quantityToBuy);
                System.out.println("BOOK HAS BEEN ADDED TO CART");
                buyingBooks = false;
            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER A VALID NUMBER");
            }
        }
    }
}