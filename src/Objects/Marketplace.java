package Objects;

import java.io.*;
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

    static String filename = "marketplace.ser";

    public Marketplace() {
        this.currentUser = null;

        // Deserialize books and users
        Marketplace readMarket = Marketplace.readMarketplace();
        if (readMarket == null) {
            this.users = new ArrayList<>();
            this.books = new HashMap<>();
            System.out.println("HERE");
            return;
        }
        try {
            this.users = readMarket.getUsers();
            this.books = readMarket.getBooks();
            System.out.println(readMarket);
        } catch (NullPointerException e) {
            this.users = new ArrayList<>();
            this.books = new HashMap<>();
            System.out.println("HERE2");
        }
    }

    public void saveMarketplace() {
        // serializes data
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Marketplace readMarketplace() {
        // deserializes data
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Marketplace marketplace = (Marketplace) in.readObject();

            in.close();
            file.close();

            return marketplace;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ignored) {

        }

        return null;
    }

    /**
     * Takes in a username and searches marketplace users to find user
     * @param username The username of the user
     * @return The user with that name
     */
    public User getUserByUsername(String username) {
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }
    /**
     * Takes in an email and searches marketplace users to find user
     * @param email The email of the user
     * @return The user with that email
     */
    public User getUserByEmail(String email) {
        for (User user : this.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
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