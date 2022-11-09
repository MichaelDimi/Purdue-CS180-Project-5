package Objects;

import Exceptions.BookNotFoundException;
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
    // returns user selected Store and null if operation cancelled
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

            // loops until a valid input is inputted
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

            //  ensures selection was not the cancel option or invalid
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

        boolean isEditingInventory = true;
        while (isEditingInventory) {
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

                    Book newBook = new Book(bookName, store.getName(), genre, description, price);

                    // loops and adds the quantity of books specified to store's stock
                    for (int i = 0; i < quantity; i++) {
                        // current quantity of specified book
                        Integer currentCount = stock.get(newBook);

                        // checks if user already has book in cart, increments current quantity if so
                        if (currentCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                            stock.put(newBook, quantity);
                        } else {
                            stock.put(newBook, currentCount + quantity);
                        }
                    }
                    break;
                case "2":
                    // remove books
                    System.out.println("SELECT BOOK TO REMOVE");
                    System.out.println("*******************");

                    // cant select from hashmap by index
                    // gets key object from books arraylist with same order as hashmap
                    Book bookToRemove = selectBook(stock);

                    // current quantity of specified book
                    Integer currentCount = stock.get(bookToRemove);

                    System.out.println("Please input the quantity you would like to remove:");
                    int quantityToRemove = scanner.nextInt();
                    scanner.nextLine();

                    // removes book from hashmap if final quantity is less than or equal 0
                    if (currentCount - quantityToRemove <= 0) {
                        stock.remove(bookToRemove);
                    } else {
                        stock.put(bookToRemove, currentCount - quantityToRemove);
                    }
                    break;
                case "3":
                    // edit books
                    System.out.println("SELECT BOOK TO EDIT");
                    System.out.println("*******************");

                    // cant select from hashmap by index
                    // gets key object from books arraylist with same order as hashmap
                    Book bookToEdit = selectBook(stock);

                    boolean isEditingBook = true;
                    while (isEditingBook) {
                        System.out.println("EDITING BOOK " + bookToEdit.getName());
                        System.out.println("*******************");
                        System.out.println("Name: " + bookToEdit.getName());
                        System.out.println("Genre: " + bookToEdit.getGenre());
                        System.out.println("Description: " + bookToEdit.getDescription());
                        System.out.printf("Price: $%.2f", bookToEdit.getPrice());

                        System.out.println("What would you like to edit?");
                        System.out.println("1. Name");
                        System.out.println("2. Genre");
                        System.out.println("3. Description");
                        System.out.println("4. Price");
                        System.out.println("5. DONE");

                        int currentBookQuantity = stock.get(bookToEdit);
                        switch (scanner.nextLine()) {
                            case "1":
                                System.out.println("Input a new name:");
                                String newName = scanner.nextLine();

                                // removes old Book object and adds new Book object with the updated name
                                bookToEdit.setName(newName);
                                stock.remove(bookToEdit);
                                stock.put(bookToEdit, currentBookQuantity);
                                break;
                            case "2":
                                System.out.println("Input new genre(s):");
                                String newGenre = scanner.nextLine();

                                // removes old Book object and adds new Book object with the updated genre(s)
                                bookToEdit.setGenre(newGenre);
                                stock.remove(bookToEdit);
                                stock.put(bookToEdit, currentBookQuantity);
                                break;
                            case "3":
                                System.out.println("Input new description:");
                                String newDescription = scanner.nextLine();

                                // removes old Book object and adds new Book object with the updated description
                                bookToEdit.setDescription(newDescription);
                                stock.remove(bookToEdit);
                                stock.put(bookToEdit, currentBookQuantity);
                                break;
                            case "4":
                                System.out.println("Input new price:");
                                double newPrice = scanner.nextDouble();
                                scanner.nextLine();

                                // removes old Book object and adds new Book object with the updated price
                                bookToEdit.setPrice(newPrice);
                                stock.remove(bookToEdit);
                                stock.put(bookToEdit, currentBookQuantity);
                                break;
                            default:
                                isEditingBook = false;
                        }
                    }

                    break;
                case "4":
                    // displays all books
                    System.out.println("CURRENT STOCK of " + store.getName());
                    System.out.println("*******************");

                    for (Book book : stock.keySet()) {
                        System.out.println("Name: " + book.getName());
                        System.out.println("Genre: " + book.getGenre());
                        System.out.println("Description: " + book.getDescription());
                        System.out.printf("Price: $%.2f", book.getPrice());
                        System.out.println("Quantity: " + stock.get(book));
                    }
                    break;
                default:
                    isEditingInventory = false;
            }
        }

        // updates the store's stock with the new modified stock
        store.setStock(stock);
    }

    // displays all books in a store lets user select a book
    // returns user selected Book and null if operation cancelled
    public Book selectBook(HashMap<Book, Integer> stock) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();

        // loops until a valid input is inputted
        int bookSelection = -1;
        while (bookSelection > stock.size() || bookSelection < 0) {
            books.clear();

            // prints out all books and their quantities; adds them to arraylist to preserve order
            int i = 1;
            for (Book book : stock.keySet()) {
                System.out.println(i + ". " + book.getName() + " | Qty: " + stock.get(book));
                books.add(book);
                i++;
            }

            System.out.println((books.size() + 1) + ". CANCEL");

            bookSelection = scanner.nextInt() - 1;
            scanner.nextLine();

            // invalid input
            if (bookSelection > books.size())
                System.out.println("That is not a valid input.");
        }

        // ensures selection was not the cancel option or invalid
        if (bookSelection < books.size()) {
            // cant select from hashmap by index
            // gets key object from books arraylist with same order as hashmap
            return books.get(bookSelection);
        }

        return null;
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
