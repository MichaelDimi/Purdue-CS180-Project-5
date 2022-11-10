package Objects;

import java.io.Serializable;

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

    public double getPrice() {
        return price;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode() + store.hashCode() + genre.hashCode() + description.hashCode();
        return result;
    }

    //Compare only account numbers
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (name != other.getName())
            return false;
        return true;
    }
}

