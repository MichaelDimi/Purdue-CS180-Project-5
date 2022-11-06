package Objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Store {

    /**
     * The store name
     */
    private String name;

    /**
     * The number and type of books in stock at this store.
     * Basically a limit to how many books can be sold.
     */
    private HashMap<Book, Integer> stock;

    /**
     * The reviews left on this store
     */
    private ArrayList<Review> reviews;

    public Store(String name, HashMap<Book, Integer> stock, ArrayList<Review> reviews) {
        this.name = name;
        this.stock = stock;
        this.reviews = reviews;
    }

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Book, Integer> getStock() {
        return stock;
    }

    public void setStock(HashMap<Book, Integer> stock) {
        this.stock = stock;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Store{" + "name='" + name + '\'' + ", stock=" + stock + ", reviews=" + reviews + '}';
    }
}
