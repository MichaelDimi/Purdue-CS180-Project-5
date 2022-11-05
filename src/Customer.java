import java.util.HashMap;

public class Customer extends User {
    private HashMap<Book, Integer> cart = new HashMap<Book, Integer>();
    private HashMap<Book, Integer> purchaseHistory = new HashMap<Book, Integer>();

    public Customer(String email, String password) {
        super(email, password);
    }
}
