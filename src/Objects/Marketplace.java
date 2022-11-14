package Objects;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
//    HashMap<Book, Integer> books;
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
//            this.books = new HashMap<>();
            return;
        }
        try {
            this.users = readMarket.getUsers();
//            this.books = readMarket.getBooks();
            System.out.println(readMarket);
        } catch (NullPointerException e) {
            this.users = new ArrayList<>();
//            this.books = new HashMap<>();
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

    /**
     * Use with caution, used only for testing and clearing the marketplace each test
     * - Note if a market is used then the test is run, the market will be cleared
     */
    public void clearMarketplace() {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            this.users = new ArrayList<>();
            this.currentUser = null;
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

            marketplace.setCurrentUser(null);

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
        for (Book book : this.getBooks().keySet()) {
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
        for (Book book : this.getBooks().keySet()) {
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
        for (Book book : this.getBooks().keySet()) {
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
     * Gets the Seller of a book
     * @param book the book whose Seller is to be found
     * @return Seller of the book
     */
    public Seller getSellerByBook(Book book) {
        for (User user : users) {
            if (user instanceof Seller) {
                // loops though all of the Seller's books
                for (Book sellerBooks : ((Seller) user).getSellerBooks().keySet()) {
                    // makes sure that the book is the exact same book and an exact copy
                    if (sellerBooks == book) {
                        return ((Seller) user);
                    }
                }
            }
        }

        return null;
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

    /**
     * Gets the stores of all the sellers in the marketplace
     * @return an array list of stores
     */
    public ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Seller) {
                Seller seller = (Seller) user;

                stores.addAll(seller.getStores());
            }
        }

        return stores;
    }

    /**
     * Gets a specific store from all the stores in the market
     * @param storeName The name of the store you want
     * @return The store with that name. Null if none was found
     */
    public Store getStoreByName(String storeName) {
        ArrayList<Store> stores = getStores();
        for (Store store : stores) {
            if (store.getName().equals(storeName)) {
                return store;
            }
        }

        return null;
    }

    /**
     * Gets the quantity of a specific book
     * @param book name of book being searched
     * @return The quantity of that book. 0 if none was found
     */
    public int getBookQuantity(Book book) {
        for (User user : users) {
            if (user instanceof Seller) {
                // loops through all the Seller's books
                for (Book sellerBooks : ((Seller) user).getSellerBooks().keySet()) {
                    // makes sure that the book is the exact same book and an exact copy
                    if (sellerBooks.equals(book)) {
                        return ((Seller) user).getSellerBooks().get(sellerBooks);
                    }
                }
            }
        }

        return 0;
    }

    public HashMap<Book, Integer> getBooks() {
        HashMap<Book, Integer> books = new HashMap<>();
        ArrayList<Store> stores = getStores();
        for (Store store : stores) {
            HashMap<Book, Integer> stock = store.getStock();
            books.putAll(stock);
        }
        return books;
    }

    public HashMap<Book, Integer> findBooks(String query) {
        HashMap<Book, Integer> books = this.getBooks();

        HashMap<Book, Integer> booksFound = new HashMap<>();
        for (Book book : books.keySet()) {
            if (book.getName().toLowerCase().contains(query.toLowerCase()) ||
                book.getGenre().toLowerCase().contains(query.toLowerCase()) ||
                book.getDescription().toLowerCase().contains(query.toLowerCase())) {

                booksFound.put(book, books.get(book));
            }
        }
        return booksFound;
    }

    public static void sortBooksByPrice(Book[] books) {
        int n = books.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books[j].finalPrice() > books[j+1].finalPrice()) {
                    Book temp = books[j];
                    books[j] = books[j+1];
                    books[j+1] = temp;
                }
            }
        }
    }
    public static Book[] sortBooksByQuantity(HashMap<Book, Integer> books) {
        int n = books.size();

        Book[] booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(booksArr[j]) < books.get(booksArr[j+1])) {
                    Book temp = booksArr[j];
                    booksArr[j] = booksArr[j+1];
                    booksArr[j+1] = temp;
                }
            }
        }

        return booksArr;
    }

    // returns array of Stores sorted by the amount of unique products they sell
    public static Store[] sortStoresByVarietyOfProducts(ArrayList<Store> stores) {
        int n = stores.size();

        Store[] storeArr = new Store[stores.size()];
        storeArr = stores.toArray(storeArr);

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (stores.get(j).getStock().size() < stores.get(j+1).getStock().size()) {
                    Store temp = storeArr[j];
                    storeArr[j] = storeArr[j+1];
                    storeArr[j+1] = temp;
                }
            }
        }

        return storeArr;
    }

    // returns array of Stores sorted by the frequency of purchases from store
    public static Store[] sortStoreByMostFrequentPurchases(Buyer buyer, ArrayList<Store> stores) {
        int n = stores.size();

        Store[] storeArr = new Store[stores.size()];
        storeArr = stores.toArray(storeArr);

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (getNumPurchasesFromStore(buyer, stores.get(j))
                        < getNumPurchasesFromStore(buyer, stores.get(j + 1))) {
                    Store temp = storeArr[j];
                    storeArr[j] = storeArr[j+1];
                    storeArr[j+1] = temp;
                }
            }
        }

        return storeArr;
    }

    // returns an int with the number of purchases the current user has made at specified store
    public static int getNumPurchasesFromStore(Buyer buyer, Store store) {
        int purchaseCount = 0;
        for (Book book : buyer.getPurchaseHistory().keySet()) {
            if (book.getStore().equals(store.getName())) {
                purchaseCount += buyer.getPurchaseHistory().get(book);
            }
        }

        return purchaseCount;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return "Marketplace{" + "users=" + users.toString() + ", currentUser=" + currentUser + '}';
    }
}