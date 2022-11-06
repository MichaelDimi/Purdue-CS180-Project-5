import java.util.ArrayList;

public class Seller extends User{
    private ArrayList<Store> stores = new ArrayList<>();

    public Seller(String email, String password) {
        super(email, password);
    }

    public void createNewStore(String storeName) {
        stores.add(new Store(storeName));
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "stores=" + stores +
                '}';
    }
}
