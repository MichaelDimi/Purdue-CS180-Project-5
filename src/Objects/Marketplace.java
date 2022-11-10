package Objects;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

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
            return;
        }
        try {
            this.users = readMarket.getUsers();
            this.books = readMarket.getBooks();
            System.out.println(readMarket);
        } catch (NullPointerException e) {
            this.users = new ArrayList<>();
            this.books = new HashMap<>();
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
            // serialized object type was not a Marketplace object
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

    /**
     * Gets all books in the marketplace by their name
     * @param name Name of book
     * @return A book
     */
    public ArrayList<Book> getBooksByName(String name) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : this.books.keySet()) {
            if (book.getName().toLowerCase().contains(name.toLowerCase())) {
                books.add(book);
            }
        }

        return books;
    }

    /**
     * Gets all books in the marketplace by the store that sells them
     * @param storeName Name of store
     * @return A book
     */
    public ArrayList<Book> getBooksByStore(String storeName) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : this.books.keySet()) {
            if (book.getStore().toLowerCase().contains(storeName.toLowerCase())) {
                books.add(book);
            }
        }

        return books;
    }

    /**
     * Gets all books in the marketplace by their description
     * @param description Description of book
     * @return A book
     */
    public ArrayList<Book> getBooksByDescription(String description) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : this.books.keySet()) {
            if (book.getDescription().toLowerCase().contains(description.toLowerCase())) {
                books.add(book);
            }
        }

        return books;
    }

    /**
     * Make sure the name provided is not in the marketplace
     * @param name The username being validated
     * @return if the username is taken
     */
    public boolean validateName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        for (User user : this.users) {
            if (user.getName().equals(name)) {
                System.out.println("That username is taken, please try a different one");
                return false;
            }
        }
        return true;
    }
    /**
     * Make sure the email provided is not in the marketplace
     * @param email The email being validated
     * @return if the email is taken
     */
    public boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        // Email regex that I got from here:
        // https://www.baeldung.com/java-email-validation-regex
        // Supports all types of valid emails
        Pattern regexPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$");
        if (!regexPattern.matcher(email).matches()) {
            System.out.println("Whoops: Please enter a valid email");
            return false;
        }
        for (User user : this.users) {
            if (user.getEmail().equals(email)) {
                System.out.println("That email is taken, please try a different one");
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a new user to the marketplace
     * @param user The user to add to marketplace
     */
    public void addToUsers(User user) {
        this.users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
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
        return "Marketplace{" + "users=" + users.toString() + ", books=" + books + ", currentUser=" + currentUser + '}';
    }
}