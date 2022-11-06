package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * TODO: Add javadoc headers to all classes
 */
public class Marketplace implements Serializable {
    /**
     * Array of users (buyers and sellers) who have accounts for the marketplace
     */
    ArrayList<User> users;
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
        this.users = new ArrayList<>();
        this.currentUser = null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Adds a new user to the marketplace
     * @param user The user to add to marketplace
     */
    public void addToUsers(User user) {
        this.users.add(user);
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
        return "Marketplace{" + "users=" + users.toString() + ", books=" + books + ", currentUser=" + currentUser + '}';
    }
}