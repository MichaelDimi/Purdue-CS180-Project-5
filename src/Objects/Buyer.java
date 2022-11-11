package Objects;

import Exceptions.BookNotFoundException;

import java.io.Serializable;
import java.util.HashMap;

public class Buyer extends User implements Serializable {
    private HashMap<Book, Integer> cart = new HashMap<Book, Integer>();
    private HashMap<Book, Integer> purchaseHistory = new HashMap<Book, Integer>();

    public Buyer(String username, String email, String password, String rawPassword) {
        super(username, email, password, rawPassword);
    }

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

    public void checkoutCart() {
        for (Book book : cart.keySet()) {
            boolean identicalEntry = false;
            for (Book b : purchaseHistory.keySet()) {
                if (b.equals(book)) {
                    identicalEntry = true;
                    break;
                }
            }

            // checks if user already has book in cart, increments current quantity if so
            if (identicalEntry) {
                purchaseHistory.put(book, purchaseHistory.get(book) + cart.get(book));
            } else {
                purchaseHistory.put(book, cart.get(book));
            }
        }
        cart.clear();
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
