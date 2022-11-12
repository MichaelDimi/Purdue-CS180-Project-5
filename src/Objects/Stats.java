package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Stats implements Serializable {

    /**
     * The number and type of books sold by the Seller
     */
    private HashMap<Book, Integer> booksSold;
    /**
     * The Buyers who has bought books from this Seller
     */
    private HashMap<User, Integer> buyers;

    public Stats(HashMap<Book, Integer> booksSold, HashMap<User, Integer> buyers) {
        this.booksSold = booksSold;
        this.buyers = buyers;
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

    @Override
    public String toString() {
        return "Stats{" + "booksSold=" + booksSold + ", buyers=" + buyers + '}';
    }
}
