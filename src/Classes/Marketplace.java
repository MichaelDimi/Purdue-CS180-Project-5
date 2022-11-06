package Classes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
/**
 * TODO: Add javadoc headers to all classes
 */
public class Marketplace implements Serializable {
    /**
     * Array of users (buyers and sellers) who have accounts for the marketplace
     */
    User[] users;
    /**
     * Stores frequency of books in the marketplace
     */
    HashMap<Book, Integer> books;
    /**
     * The current user logged in.
     * Null before login
     */
    User currentUser;

    public Marketplace() {
        // TODO: Deserialize books and users
        this.currentUser = null;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return "Marketplace{" + "users=" + Arrays.toString(users) + ", books=" + books + ", currentUser=" + currentUser + '}';
    }
}