package Objects;

import java.util.HashMap;

public class Buyer extends User {
    private HashMap<Book, Integer> cart;
    private HashMap<Book, Integer> purchaseHistory;

    public Buyer(String name, String email, String password) {
        super(name, email, password);

        this.cart = new HashMap<>();
        this.purchaseHistory = new HashMap<>();
    }
}
