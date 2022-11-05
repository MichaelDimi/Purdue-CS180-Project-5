import java.util.HashMap;

public class Store {
    private String storeName;

    private HashMap<Book, Integer> sellHistory = new HashMap<Book, Integer>();

    public Store(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public HashMap<Book, Integer> getSellHistory() {
        return sellHistory;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeName='" + storeName + '\'' +
                '}';
    }
}
