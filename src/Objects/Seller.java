package Objects;

import java.util.ArrayList;

public class Seller extends User {
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
    public String toString() { // TODO: Combine this with the super toString()
        return "Seller{" + "stores=" + stores + ", stats=" + stats + '}';
    }
}
