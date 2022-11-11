package Objects;

import java.io.Serializable;
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

    // formats and prints all books Seller has sold
    public void listAllSoldBooks() {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {
            // displays all books
            System.out.println("YOUR SOLD BOOKS");
            System.out.println("*******************");

            for (Book book : booksSold.keySet()) {
                System.out.println("Name: " + book.getName());
                System.out.println("Genre: " + book.getGenre());
                System.out.println("Description: " + book.getDescription());
                System.out.printf("Price: $%.2f\n", book.getPrice());
                System.out.println("Quantity: " + booksSold.get(book) + "\n");
            }
        }
    }

    // formats and prints all the customers of Seller
    public void listAllBuyers() {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {
            // displays all books
            System.out.println("YOUR CUSTOMERS");
            System.out.println("*******************");

            for (User buyer : buyers.keySet()) {
                System.out.println(buyer.getName());
                // TODO: get the number of products this user has bought from specific seller
            }
        }
    }

    // prints the most popular genre the Seller sells
    public void listMostPopularGenre() {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {
            // holds all book genres that have been sold
            HashMap<String, Integer> genres = new HashMap<>();

            // generates hasmap based on genres
            for (Book book : booksSold.keySet()) {
                String bookGenre = book.getGenre();
                // current quantity of specified book
                Integer newBookCount = genres.get(bookGenre);

                // checks if user already has book in cart, increments current quantity if so
                if (newBookCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                    genres.put(bookGenre, 1);
                } else {
                    genres.put(bookGenre, newBookCount + 1);
                }
            }

            // gets most popular genre
            String mostPopular = "";
            for (String genre : genres.keySet()) {
                if (genres.get(genre) > genres.get(mostPopular)) {
                    mostPopular = genre;
                }
            }

            System.out.println("YOUR MOST POPULAR GENRE iS: " + mostPopular);
        }
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
