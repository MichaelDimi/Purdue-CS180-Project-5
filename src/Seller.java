import java.util.ArrayList;

public class Seller extends User{
    private ArrayList<Store> stores = new ArrayList<>();

    public Seller(String email, String password) {
        super(email, password);
    }
}
