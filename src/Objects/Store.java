package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class mainly contains a hashmap of books
 * an ArrayList of Reviews
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
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

    private String sellerName;

    public Store(String name, String sellerName, ArrayList<Review> reviews) {
        this.name = name;
        // This gets set later by the seller. For each book, you give a quantity declaring the stock
        this.stock = new HashMap<>();
        this.reviews = reviews;
        this.sellerName = sellerName;
    }

    public Store(String name, String sellerName) {
        this.name = name;
        this.stock = new HashMap<>();
        this.reviews = new ArrayList<>();
        this.sellerName = sellerName;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public Integer getAverageRating() {
        Integer rating = 0;
        for (Review review : this.reviews) {
            rating += review.getRating();
        }
        try {
            rating /= this.reviews.size();
        } catch (ArithmeticException e) { // has no reviews
            rating = null;
        }
        return rating;
    }

    @Override
    public String toString() {
        return String.format("Store{name=%s,\nstock=%s,\nreviews=%s}", name, stock, reviews);
    }
}
