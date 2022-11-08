package Objects;

import Exceptions.IdenticalStoreException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Seller extends User implements Serializable {
    /**
     * List of the stores owned by Seller
     */
    private ArrayList<Store> stores;
    /**
     * Reference to class containing the statistics for Seller
     */
    private Stats stats;

    public Seller(String name, String email, String password) {
        super(name, email, password);

        this.stores = new ArrayList<>();
    }

    public HashMap<Book, Integer> getSellerBooks() {
        HashMap<Book, Integer> sellersBooks = new HashMap<>();
        for (Store store : this.stores) {
            HashMap<Book, Integer> stock = store.getStock();
            for (Book book : stock.keySet()) {
                if (sellersBooks.get(book) == null) {
                    sellersBooks.put(book, stock.get(book));
                } else {
                    sellersBooks.put(book, stock.get(book) + sellersBooks.get(book));
                }
            }
        }
        return sellersBooks;
    }

    public void createNewStore(String storeName) throws IdenticalStoreException {
        // Check that the name is not identical
        if (this.getStoreByName(storeName) != null) {
            // TODO: Catch this exception wherever this function is used.
            throw new IdenticalStoreException("You cannot have an identical store");
        }
        stores.add(new Store(storeName));
    }

    public Store getStoreByName(String storeName) {
        for (Store store : this.stores) {
            if (storeName.equals(store.getName())) {
                return store;
            }
        }
        return null;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "Seller<" + this.getName() + ", " +
                this.getEmail() + ", " +
                this.getPassword() + ", " +
                stores + ", " +
                stats +
                ">";
    }
}
