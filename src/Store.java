import java.util.HashMap;

public class Store {
    private String storeName;

    private HashMap<Integer, Book> sellHistory = new HashMap<Integer, Book>();

    public Store(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public HashMap<Integer, Book> getSellHistory() {
        return sellHistory;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeName='" + storeName + '\'' +
                '}';
    }
}
