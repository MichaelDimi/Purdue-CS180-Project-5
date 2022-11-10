package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Store implements Serializable {

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

    public Store(String name, ArrayList<Review> reviews) {
        this.name = name;
        // This gets set later by the seller. For each book, you give a quantity declaring the stock
        this.stock = null;
        this.reviews = reviews;
    }

    public Store(String name) {
        this.name = name;
        this.stock = null;
        this.reviews = null;
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

    public void addStock(int quantity, Book book) {
        this.stock.put(book, quantity);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return String.format("Store{name=%s,\nstock=%s,\nreviews=%s}", name, stock, reviews);
    }
}
