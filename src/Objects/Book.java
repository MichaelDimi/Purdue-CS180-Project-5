package Objects;

import Client.BookApp;
import Query.Query;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class contains the fields and methods for
 * a book.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class Book implements Serializable {
    /**
     * The name of the book
     */
    private String name;

    /**
     * The name of the store selling the book.
     */
    private String store;

    /**
     * The genre of the book
     */
    private String genre;

    /**
     * The description of the book
     */
    private String description;

    /**
     * The price of the book
     */
    private double price;

    /**
     * The percent sale of the book.
     * Eg $100 book at percentOff = 10 becomes $90
     */
    private double percentOff;

    public Book(String name, String store, String genre, String description, double price) {
        this.name = name;
        this.store = store;
        this.genre = genre;
        this.description = description;
        this.price = price;
    }

    public Book(Book book) {
        this.name = book.getName();
        this.store = book.getStore();
        this.genre = book.getGenre();
        this.description = book.getDescription();
        this.price = book.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double finalPrice() {
        return price - ((price / 100) * percentOff); // Calculates percent off
    }

    /**
     * Returns the raw price of the book.<br><br>
     * <STRONG>YOU MUST USE: finalPrice() to get the price of a book with the sale</STRONG>
     */
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(double percentOff) {
        this.percentOff = percentOff;
    }

    @Override
    public String toString() {
        return "Book{" + "name='" + name + '\'' + ", store='" + store + '\'' + ", genre='" + genre + '\'' + ", " +
                "description='" + description + '\'' + ", price=" + price + '}';
    }

    /**
     * Prints a list item for a book
     * @param i the number item this book is in the list. If null, don't number the list
     * @param q the quantity of the book, taken from the hashmap
     */
    public void printBookListItem(Integer i, int q) {
        if (i == null) {
            System.out.printf("- %s -- Store: %s -- Original Price: $%.2f -- " +
                            "Percent off: %.2f%% -- Final price: $%.2f -- " +
                            "Quantity: " + "[%d]\n",
                    getName(), getStore(), getPrice(), getPercentOff(), finalPrice(), q);
            return;
        }
        System.out.printf("%d. %s -- Store: %s -- Original Price: $%.2f " +
                        "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                        "Quantity: " + "[%d]\n",
                i, getName(), getStore(), getPrice(), getPercentOff(), finalPrice(), q);
    }

    /**
     * Prints detailed information about the book
     */
    public void printBookDetails() {
        String quantity = "Error";
        Query bookQuantityQuery = BookApp.getQuery(this, "books", "quantity");
        if (bookQuantityQuery.getObject() != null && !bookQuantityQuery.getObject().equals(false)) {
            quantity = ((Integer) bookQuantityQuery.getObject()).toString();
        }

        System.out.println("Title: " + name);
        System.out.printf("Price: $%.2f | Qty Available: %s\n", price, quantity);
        System.out.println("Store: " + store);
        System.out.println("Genre(s): " + genre);
        System.out.println("Description: " + description);
    }

    /**
     * Overrides the hashcode so that when iterating over sets, the set compares books correctly.
     * The hashcode is based on the properties of the book.
     * @return Return the hashcode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode() + store.hashCode() + genre.hashCode() + description.hashCode();
        return result;
    }

    /**
     * Overrides the equals to work with new hashcode
     * @return Returns true if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        return Objects.equals(name, other.getName());
    }
}

