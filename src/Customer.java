import java.util.HashMap;

public class Customer extends User {
    private HashMap<Integer, Book> cart = new HashMap<Integer, Book>();
    private HashMap<Integer, Book> purchaseHistory = new HashMap<Integer, Book>();

    public Customer(String email, String password) {
        super(email, password);
    }
}
