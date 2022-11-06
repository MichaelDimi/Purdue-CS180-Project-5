package Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Stats {

    /**
     * The number and type of books sold by the Seller
     */
    private HashMap<Book, Integer> booksSold;
    /**
     * The Buyers who has bought books from this Seller
     */
    private ArrayList<Buyer> buyers;

    public Stats(HashMap<Book, Integer> booksSold, ArrayList<Buyer> buyers) {
        this.booksSold = booksSold;
        this.buyers = buyers;
    }

    public HashMap<Book, Integer> getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(HashMap<Book, Integer> booksSold) {
        this.booksSold = booksSold;
    }

    public ArrayList<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(ArrayList<Buyer> buyers) {
        this.buyers = buyers;
    }

    @Override
    public String toString() {
        return "Stats{" + "booksSold=" + booksSold + ", buyers=" + buyers + '}';
    }
}
