package Objects;

import Client.BookApp;
import Client.ClientQuery;
import Exceptions.BookNotFoundException;
import Query.Query;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Contains a cart and purchase
 * history as a Hashmap and has methods to
 * modify a cart
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class Buyer extends User implements Serializable {
    private HashMap<Book, Integer> cart = new HashMap<Book, Integer>();
    private HashMap<Book, Integer> purchaseHistory = new HashMap<Book, Integer>();

    public Buyer(String username, String email, String password, String rawPassword) {
        super(username, email, password, rawPassword);
    }

    // returns and validates that items in cart still exist
    public HashMap<Book, Integer> getCart() {
        return cart;
    }

    public HashMap<Book, Integer> getPurchaseHistory() {
        return purchaseHistory;
    }

    // TODO: do error handling and catch negative inputs
    // adds new books to user's cart
    public void addToCart(Book book, int quantity) {
        boolean identicalEntry = false;
        for (Book b : cart.keySet()) {
            if (b.equals(book)) {
                identicalEntry = true;
                break;
            }
        }

        // checks if user already has book in cart, increments current quantity if so
        if (identicalEntry) { // could be replaced with merge, not sure if Vocareum will like?
            cart.put(book, cart.get(book) + quantity);
        } else {
            cart.put(book, quantity);
        }
    }

    // removes books from user's cart
    public void removeFromCart(Book book, int quantity) throws BookNotFoundException {
        // current quantity of specified book
        Integer currentCount = cart.get(book);

        // checks if user already has book in cart, decrements current quantity if so
        if (currentCount == null) { // could be replaced with merge, not sure if Vocareum will like?
            throw new BookNotFoundException("Book does not exist in the cart");
        } else {
            // removes book from hashmap if final quantity is less than or equal 0
            if (currentCount - quantity <= 0) {
                cart.remove(book);
            } else {
                cart.put(book, currentCount - quantity);
            }
        }
    }

    // adds books to Buyer's purchase history, clears cart's contents, and then returns hashmap will all books purchased
    public void checkoutCart() {
        boolean canCheckout = true;
        for (Book book : cart.keySet()) {
            // checks if there are enough books in stock to purchase
            String quantityString = "?";
            Integer availableQuantity = null;
            Query bookQuantityQuery = new ClientQuery().getQuery(this, "books", "quantity");
            if (bookQuantityQuery.getObject() != null && !bookQuantityQuery.getObject().equals(false)) {
                availableQuantity = (Integer) bookQuantityQuery.getObject();
                quantityString = availableQuantity.toString();
            }
            if (availableQuantity == null) break;
            if (cart.get(book) > availableQuantity) {
                canCheckout = false;
                System.out.println("SORRY, BUT THERE IS NOT ENOUGH STOCK TO PURCHASE: " + book.getName());
                System.out.println("CART QUANTITY: " + cart.get(book) + " | AVAILABLE QUANTITY: " + quantityString);
                break;
            }

            boolean identicalEntry = false;
            for (Book b : purchaseHistory.keySet()) {
                if (b.equals(book)) {
                    identicalEntry = true;
                    break;
                }
            }

            // Update seller's stock
            Query sellerQuery = new ClientQuery().getQuery(book, "sellers", "book");
            if (sellerQuery.getObject() == null || sellerQuery.getObject().equals(false)) {
                return;
            }
            if (!(sellerQuery.getObject() instanceof Seller)) return;
            Seller bookSeller = (Seller) sellerQuery.getObject();
            bookSeller.updateStock(book, cart.get(book), this);

            // checks if user already has book in cart, increments current quantity if so
            if (identicalEntry) {
                purchaseHistory.put(book, purchaseHistory.get(book) + cart.get(book));
            } else {
                purchaseHistory.put(book, cart.get(book));
            }
        }

        if (canCheckout)
            cart.clear();
    }

    // removes all items in the Buyer's cart
    public void clearCart() {
        cart.clear();
    }

    public void setCart(HashMap<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setPurchaseHistory(HashMap<Book, Integer> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    @Override
    public String toString() {
        return "Buyer<" + this.getName() + ", " +
                this.getEmail() + ", " +
                this.getPassword() + ", " +
                cart + ", " +
                purchaseHistory +
                ">";
    }
}
