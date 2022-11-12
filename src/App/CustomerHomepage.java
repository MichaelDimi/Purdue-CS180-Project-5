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
        System.out.println("*******************");

        String choice;
        do {
            System.out.println("What would you like to do?\n" +
                    "1. Purchase a book\n" +
                    "2. Search for a book\n" +
                    "3. View Purchase History\n" +
                    "4. View Shopping Cart\n" +
                    "5. SIGN OUT");
            choice = scan.nextLine();
            if (!"12345".contains(choice)) {
                System.out.println("Whoops: Please enter a number from 1-5");
            }
        } while (!"12345".contains(choice));

        Buyer buyer = (Buyer) BookApp.marketplace.getCurrentUser();

        // TODO: Clean up:
        if (choice.equalsIgnoreCase("1")) {
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
            
            int i = 1;
            for (Book book : booksArr) { //Printing list of books available for sale
                book.printBookListItem(i, books.get(book));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = 0;
            boolean error;
            do {
                error = false;
                try {
                    response = Integer.parseInt(scan.nextLine());
                    if (response < 1 || response > i) {
                        if (i == 1) {
                            System.out.println("Whoops: Must be (1) or (2)");
                        } else {
                            System.out.printf("Whoops: Must be (1) -> (%d)\n", i);
                        }
                        error = true;
                    }
                } catch (NumberFormatException e) {
                    if (i == 1) {
                        System.out.println("Whoops: Must be (1) or (2)");
                    } else {
                        System.out.printf("Whoops: Must be (1) -> (%d)\n", i);
                    }
                    error = true;
                }
            } while (error);

            if (response == i) {
                return true; // Go back
            } else {

            }

            // TODO: Add buying a book here
        } else if (choice.equals("2")) {
            System.out.println("Enter search query: ");
            String searchBook = scan.nextLine();
            HashMap<Book, Integer> books = BookApp.marketplace.getBooks(); //getting hashmap of books
            for (Book book: books.keySet()) { //Printing list of books available for sale
                //String name = book.getName();
                Book b = new Book(book);
                //b.getName() changed
                if(book.getName().equalsIgnoreCase(searchBook) || b.getStore().equalsIgnoreCase(searchBook)
                        || b.getDescription().equalsIgnoreCase(searchBook)) {
                    System.out.println(b.toString()); //need to format properly
                }
            }

            // TODO: Add buying a book here
        } else if (choice.equals("3")) {
            if (BookApp.marketplace.getCurrentUser() instanceof Buyer) {
                HashMap<Book, Integer> purchaseHist = ((Buyer) BookApp.marketplace.getCurrentUser()).getPurchaseHistory();
                for (Book book: purchaseHist.keySet()) {
                    System.out.println(book.toString() + "\t" + String.valueOf(purchaseHist.get(book)));
                }
            }

        } else if (choice.equals("4")) { // TODO: Come back to this
            HashMap<Book, Integer> cart = buyer.getCart();
            if (cart.isEmpty()) {
                System.out.println("Your cart is empty");
                return true;
            }
            int i = 0;
            for (Book book : cart.keySet()) {
                book.printBookListItem(i, cart.get(book));
                i++;
            }
        } else if (choice.equals("5")) {
            return false;
        }
        return true;
    }
}