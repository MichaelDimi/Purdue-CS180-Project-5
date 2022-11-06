package Classes;

import Classes.Book;

import java.util.HashMap;

public class Buyer extends User {
    private HashMap<Book, Integer> cart;
    private HashMap<Book, Integer> purchaseHistory;

    public Buyer(String email, String password, HashMap<Book, Integer> cart, HashMap<Book, Integer> purchaseHistory) {
        super(email, password);

        this.cart = cart;
        this.purchaseHistory = purchaseHistory;
    }
}
