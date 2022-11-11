package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerStats implements Serializable {

    /**
     * The number and type of books sold by the Seller
     */
    private HashMap<Book, Integer> booksSold;
    /**
     * The total amount of money the Seller has made
     */
    private double revenue;
    /**
     * The Buyers who has bought books from this Seller
     */
    private HashMap<User, Integer> buyers;

    public SellerStats(HashMap<Book, Integer> booksSold, int revenue, HashMap<User, Integer> buyers) {
        this.booksSold = booksSold;
        this.revenue = revenue;
        this.buyers = buyers;
    }

    public SellerStats() {
        this.booksSold = new HashMap<>();
        this.revenue = 0;
        this.buyers = new HashMap<>();
    }

    public HashMap<Book, Integer> getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(HashMap<Book, Integer> booksSold) {
        this.booksSold = booksSold;
    }

    public HashMap<User, Integer> getBuyers() {
        return buyers;
    }

    public void setBuyers(HashMap<User, Integer> buyers) {
        this.buyers = buyers;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void incrementRevenue(double profit) {
        this.revenue += profit;
    }

    public double getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Stats{" + "booksSold=" + booksSold + ", buyers=" + buyers + '}';
    }
}
