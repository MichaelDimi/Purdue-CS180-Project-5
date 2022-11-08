package Objects;

import Exceptions.IdenticalStoreException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Seller extends User implements Serializable {
    /**
     * List of the stores owned by Seller
     */
    private ArrayList<Store> stores;
    /**
     * Reference to class containing the statistics for Seller
     */
    private Stats stats;

    public Seller(String name, String email, String password) {
        super(name, email, password);

        this.stores = new ArrayList<>();
    }

    public void createNewStore(String storeName) throws IdenticalStoreException {
        // Check that the name is not identical
        if (this.getStoreByName(storeName) != null) {
            // TODO: Catch this exception wherever this function is used.
            throw new IdenticalStoreException("You cannot have an identical store");
        }
        stores.add(new Store(storeName));
    }

    // lets user add new store which gets added to seller's store arraylist
    public void createNewStore() throws IdenticalStoreException {
        System.out.println("CREATING A NEW STORE");
        System.out.println("*******************");
        System.out.println("Please enter a new store name (enter 0 to cancel):");
        Scanner scanner = new Scanner(System.in);
        String storeName = scanner.nextLine();

        // Check that the name is not identical
        if (this.getStoreByName(storeName) != null) {
            // TODO: Catch this exception wherever this function is used.
            throw new IdenticalStoreException("You cannot have an identical store");
        }

        if (storeName.equals("0")) {
            System.out.println("STORE CREATION CANCELED");
        } else {
            stores.add(new Store(storeName));
            System.out.println("STORE SUCCESSFULLY CREATED");
        }
    }

    // displays all stores a seller owns and lets seller select a store
    public Store selectStore() {
        Scanner scanner = new Scanner(System.in);

        // checks that seller owns at least 1 store and asks if user wants to make a new store
        if (stores.size() <= 0) {
            System.out.println("You currently do not own any stores.\nWould you like to create one?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            // brings up prompt to create a new store
            if (scanner.nextLine().equals("1")) {
                // TODO: IdenticalStoreException not needed?
                try {
                    createNewStore();
                } catch (IdenticalStoreException e) {
                    System.out.println(e.getMessage());
                }
                // prompts user to select store again from the updated list
                return selectStore();
            }

        } else {
            System.out.println("CHOOSE STORE TO EDIT");
            System.out.println("*******************");

            int storeSelection = -1;
            while (storeSelection > stores.size() || storeSelection < 0) {
                // displays stores the seller owns
                for (int i = 0; i < stores.size(); i++) {
                    System.out.println((i + 1) + ". " + stores.get(i).getName());
                }

                System.out.println((stores.size() + 1) + ". CANCEL");

                storeSelection = scanner.nextInt() - 1;
                scanner.nextLine();

                // invalid input
                if (storeSelection > stores.size())
                    System.out.println("That is not a valid input.");
            }

            //  selection was not the cancel option or invalid
            if (storeSelection < stores.size()) {
                System.out.println("CURRENT STORE SELECTION: " + stores.get(storeSelection).getName());
                return stores.get(storeSelection);
            }
        }

        return null;
    }

    public void editStore() {

    }

    public void editStoreName(Store store) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a new name (enter 0 to cancel):");
        String storeName = scanner.nextLine();

        if (storeName.equals("0")) {
            System.out.println("STORE RENAMING CANCELED");
        } else {
            store.setName(storeName);
        }
    }

    public void editStoreInventory(Store store) {
        // TODO: ADD AND REMOVE BOOK LISTINGS
        HashMap<Book, Integer> stock = store.getStock();

        System.out.println("Would you like to:");
        System.out.println("1. Add new books");
        System.out.println("2. Remove books");
        System.out.println("3. Edit existing books");
        System.out.println("4. View books in store");
        System.out.println("5. DONE");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                // add books
                System.out.println("Enter a book name:");
                String bookName = scanner.nextLine();

                System.out.println("Enter book genre(s):");
                String genre = scanner.nextLine();

                System.out.println("Enter book description:");
                String description = scanner.nextLine();

                System.out.println("Enter book genre(s):");
                double price = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                Book book = new Book(bookName, store.getName(), genre, description, price);

                // loops and adds the quantity of books specified to store's stock
                for (int i = 0; i < quantity; i++) {
                    // current quantity of specified book
                    Integer currentCount = store.getStock().get(book);

                    // checks if user already has book in cart, increments current quantity if so
                    if (currentCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                        stock.put(book, quantity);
                    } else {
                        stock.put(book, currentCount + quantity);
                    }
                }
                break;
            case "2":
                // remove books
                break;
            case "3":
                // edit books
                break;
            case "4":
                // displays all books
                break;
            default:
                break;
        }
    }

    public HashMap<Book, Integer> getSellerBooks() {
        HashMap<Book, Integer> sellersBooks = new HashMap<>();
        for (Store store : this.stores) {
            HashMap<Book, Integer> stock = store.getStock();
            for (Book book : stock.keySet()) {
                if (sellersBooks.get(book) == null) {
                    sellersBooks.put(book, stock.get(book));
                } else {
                    sellersBooks.put(book, stock.get(book) + sellersBooks.get(book));
                }
            }
        }
        return sellersBooks;
    }

    public Store getStoreByName(String storeName) {
        for (Store store : this.stores) {
            if (storeName.equals(store.getName())) {
                return store;
            }
        }
        return null;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "Seller<" + this.getName() + ", " +
                this.getEmail() + ", " +
                this.getPassword() + ", " +
                stores + ", " +
                stats +
                ">";
    }
}
