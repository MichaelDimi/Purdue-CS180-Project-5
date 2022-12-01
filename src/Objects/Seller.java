package Objects;

import Client.*;
import Exceptions.IdenticalStoreException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/* TODO
    - check for duplicate books
    - check for duplicate stores
    - update books in cart when something is edited
 */

/**
 * Contains an ArrayList of Stores and
 * methods for creating, editing or deleting
 * a seller’s store.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class Seller extends User implements Serializable {
    /**
     * List of the stores owned by Seller
     */
    private ArrayList<Store> stores;
    /**
     * Reference to class containing the statistics for Seller
     */
    private SellerStats stats;

    public Seller(String name, String email, String password, String rawPassword) {
        super(name, email, password, rawPassword);

        this.stores = new ArrayList<>();
        this.stats = new SellerStats();
    }

    public void createNewStore(String storeName) throws IdenticalStoreException {
        // Check that the name is not identical
        if (this.getStoreByName(storeName) != null) {
            // TODO: Catch this exception wherever this function is used.
            throw new IdenticalStoreException("You cannot have an identical store");
        }
        stores.add(new Store(storeName, this.getName()));
    }

    // lets user add new store which gets added to seller's store arraylist
    public void createNewStore(Scanner scanner, Seller seller) throws IdenticalStoreException {
        System.out.println("CREATE NEW STORE");
        System.out.println("*******************");
        System.out.println("Please enter a new store name (enter 0 to cancel):");
        String storeName = scanner.nextLine();

        // Check that the name is not identical
        if (this.getStoreByName(storeName) != null) {
            // TODO: Catch this exception wherever this function is used.
            throw new IdenticalStoreException("You cannot have an identical store");
        }

        if (storeName.equals("0")) {
            System.out.println("STORE CREATION CANCELED\n");
        } else {
            stores.add(new Store(storeName, seller.getName()));
            System.out.println("STORE SUCCESSFULLY CREATED");
        }
    }
    // menu when creating/editing stores or adding/editing books
    public boolean editStore(Scanner scanner) {

        String option;
        do {
            System.out.println("SELLER PAGE");
            System.out.println("*******************");
            System.out.println("1. Edit store or manage stock");
            System.out.println("2. Create new store");
            System.out.println("3. Delete store");
            System.out.println("4. View your stores");
            System.out.println("5. Add a SALE");
            System.out.println("6. View reviews");
            System.out.println("7. View seller stats");
            System.out.println("8. View buyer carts");
            System.out.println("9. Import / Export Inventory");
            System.out.println("10. Edit Account");
            System.out.println("11. SIGN OUT");

            option = scanner.nextLine();

        } while (!"12345678910".contains(option));

        switch (option) {
            case "1":
                System.out.println("MANAGE STORE");
                System.out.println("*******************");
                // editing store and manging stock
                Store selectedStore = selectStore(scanner);

                // checks if selectStore was cancelled and returned null
                if (selectedStore == null)
                    break;

                System.out.println("What would you like to do?");
                System.out.println("1. Mange stock");
                System.out.println("2. Edit store name");
                System.out.println("3. CANCEL");

                switch (scanner.nextLine()) {
                    case "1":
                        // manage stock
                        editStoreInventory(scanner, selectedStore);
                        break;
                    case "2":
                        // edit store name
                        editStoreName(scanner, selectedStore);
                        break;
                }
                break;
            case "2":
                try {
                    createNewStore(scanner, this);
                } catch (IdenticalStoreException ignored) {
                    System.out.println("Whoops: You cannot create a store with the same name as another");
                }
                break;
            case "3":
                System.out.println("DELETE STORE");
                System.out.println("*******************");
                // delete store
                Store storeToDelete = selectStore(scanner);

                // checks if selectStore was cancelled and returned null
                if (storeToDelete == null)
                    break;

                for (int i = 0; i < stores.size(); i++) {
                    // store references should be the same (see selectStore() method)
                    if (stores.get(i) == storeToDelete) {
                        stores.remove(i);
                    }
                }
                break;
            case "4":
                System.out.println("VIEW STORES");
                System.out.println("*******************");

                if (this.getStores().size() == 0) {
                    System.out.println("YOU CURRENTLY DO NOT OWN ANY STORES");
                } else {
                    // lists out all owned stores
                    for (Store store : stores)
                        System.out.println("- " + store.getName() + " -- Owner: " + store.getSellerName() + " -- " +
                                "Rating:" + " " + Review.starDisplay(store.getAverageRating()));
                }
                System.out.println("Press ENTER to exit");
                scanner.nextLine();
                break;
            case "5":
                System.out.println("ADD A SALE");
                System.out.println("*******************");

                SalesMenu salesMenu = new SalesMenu();
                salesMenu.createSale(scanner, this);
                break;
            case "6":
                System.out.println("VIEW STORE REVIEWS");
                System.out.println("*******************");

                // Convert the arraylist to an array, since its easier to manipulate
                Store[] storesArr = new Store[stores.size()];
                storesArr = stores.toArray(storesArr);

                if (storesArr.length < 1) {
                    System.out.println("There are no stores in the market yet");
                    System.out.println("Be the first to open a store");
//                    BookApp.marketplace.saveMarketplace();
                    return true;
                }

                System.out.println("Select a store to see their books or reviews:");
                int i = 1;
                for (Store store : storesArr) { //Printing list of books available for sale
                    System.out.println(i + ". " + store.getName() +
                            " -- Owner: " + store.getSellerName() +
                            " -- Rating:" + " " +
                            Review.starDisplay(store.getAverageRating()));
                    i++;
                }
                System.out.println(i + ". BACK");

                int response = Menu.selectFromList(i, scanner);

                Store storeSelected;
                if (response == i) {
//                    BookApp.marketplace.saveMarketplace();
                    return true; // Go back
                } else {
                    storeSelected = storesArr[response - 1];
                }

                ReviewsMenu reviewsMenu = new ReviewsMenu();
                reviewsMenu.viewStoreReviews(scanner, storeSelected);

                break;
            case "7":
                // view stats
                String statsSelectionInput;
                do {
                    System.out.println("YOUR SALES STATS");
                    System.out.println("*******************");
                    System.out.println("1. Sales by store");
                    System.out.println("2. Buyers by store");
                    System.out.println("3. All sales");
                    System.out.println("4. All buyers");
                    System.out.println("5. Total Revenue");
                    System.out.println("6. Most popular genre");
                    System.out.println("7. BACK");

                    statsSelectionInput = scanner.nextLine();

                    Store storeSelectionStats;
                    switch (statsSelectionInput) {
                        case "1":
                            // get sales by store
                            // prompts user for store to view stats for
                            System.out.println("Select a store: ");
                            storeSelectionStats = selectStore(scanner);

                            // if user selects cancel, select store will return null
                            if (storeSelectionStats != null)
                                stats.listSoldBooks(scanner, storeSelectionStats);
                            break;
                        case "2":
                            // get all buyers by store
                            // prompts user for store to view stats for
                            System.out.println("Select a store: ");
                            storeSelectionStats = selectStore(scanner);

                            // if user selects cancel, select store will return null
                            if (storeSelectionStats != null)
                                stats.listBuyers(scanner, storeSelectionStats);
                            break;
                        case "3":
                            // get all books sold
                            stats.listAllSoldBooks(scanner);
                            break;
                        case "4":
                            // get all buyers
                            stats.listAllBuyers(scanner);
                            break;
                        case "5":
                            // get total revenue
                            System.out.printf("Total Revenue: $%.2f\n", stats.getRevenue());
                            break;
                        case "6":
                            // get most popular genre
                            stats.listMostPopularGenre();
                            break;
                        case "7":
                            // go back
                            break;
                    }
                } while (!"1234567".contains(statsSelectionInput));
                break;
            case "8":
                // lists off all users that have a cart with the Seller's product and prints the contents of the carts
                System.out.println("*******************");
                System.out.println("NOTE: You can only see the products you sell in carts for privacy");

                // loops through all users
                boolean isCartWithProduct = false;
//                for (User user : BookApp.marketplace.getUsers()) {
//                    // checks if user is a buyer
//                    if (user instanceof Buyer) {
//                        // string lists all of Buyer's books in cart that are sold by the Seller
//                        String cartContents = "";
//                        // loops through each book in that buyers cart
//                        for (Book bookInCart : ((Buyer) user).getCart().keySet()) {
//                            // loops through Seller's stores and cross-references to see if the book belongs
//                            // to one of the Seller's store
//                            for (Store store : stores) {
//                                // checks if book's store is one of the Seller's
//                                if (bookInCart.getStore().equals(store.getName())) {
//                                    isCartWithProduct = true;
//                                    cartContents += String.format("- %s | Qty: %d",
//                                            bookInCart.getName(), ((Buyer) user).getCart().get(bookInCart));
//                                }
//                            }
//                        }
//
//                        // prints Buyer's name and cart if user had any books in cart that are sold by the seller
//                        // ONLY PRINTS CART CONTENTS THAT ARE SOLD BY THE SELLER; WILL NOT SHOW EVERYTHING IN CART
//                        if (cartContents.length() > 0) {
//                            System.out.println("CART CONTENTS OF: " + user.getName());
//                            System.out.println(cartContents);
//                        }
//                    }
//                }

                if (!isCartWithProduct)
                    System.out.println("NO CARTS CONTAIN PRODUCTS YOU SELL");
                break;
            case "9":
                // Note: Menu header is provided in fileIOMenu
                /* Seller seller = (Seller) BookApp.marketplace.getCurrentUser();*/ Seller seller = new Seller("", ""
                    , "", "");

                FileIOMenu fileIOMenu = new FileIOMenu();
                fileIOMenu.fileIOMenu(scanner, seller);
                break;
            case "10":
                System.out.println("YOUR ACCOUNT MENU");
                AccountMenu accountMenu = new AccountMenu();
                boolean accountMenuResult = accountMenu.present(scanner); // If false, sign out
                if (!accountMenuResult) {
                    // Sign out
                    BookApp.currentUser = null;
                    return false; // Should break main loop
                }
                break;
            case "11":
//                BookApp.marketplace.saveMarketplace();
                return false;
        }
//        BookApp.marketplace.saveMarketplace();
        return true;
    }

    public void editStoreName(Scanner scanner, Store store) {
        System.out.println("Enter a new name (enter 0 to cancel):");
        String storeName = scanner.nextLine();

        if (storeName.equals("0")) {
            System.out.println("STORE RENAMING CANCELED");
        } else {
            store.setName(storeName);
        }
    }

    public Book getBookByName(String name) {
        HashMap<Book, Integer> books = this.getSellerBooks();
        for (Book book : books.keySet()) {
            if (book.getName().equalsIgnoreCase(name)) {
                return book;
            }
        }

        return null;
    }

    public boolean addBookMenu(Scanner scanner, Store store) {
        if (store == null) {
            boolean error;
            do {
                if (stores == null || stores.isEmpty()) {
                    // User has no stores
                    return false;
                }
                error = false;
                System.out.println("What store would you like to add a book to?");
                String storeName = scanner.nextLine();

                store = getStoreByName(storeName);
                if (getStoreByName(storeName) == null) {
                    error = true;
                } else {
                    store = getStoreByName(storeName);
                }
            } while (error);

        }
        HashMap<Book, Integer> stock = store.getStock();

        // add books
        String bookName;
        do {
            System.out.println("Enter a title:");
            bookName = scanner.nextLine();
            if (bookName.isEmpty()) {
                System.out.println("Whoops: Book must have a title");
            }
        } while (bookName.isEmpty());

        String genre;
        do {
            System.out.println("Enter a genre:");
            genre = scanner.nextLine();
            if (genre.isEmpty()) {
                System.out.println("Whoops: Book must have a genre");
            }
        } while (genre.isEmpty());

        String description;
        do {
            System.out.println("Enter a description:");
            description = scanner.nextLine();
            if (description.isEmpty()) {
                System.out.println("Whoops: Book must have a description");
            }
        } while (description.isEmpty());

        double price = 0;
        boolean error;
        do {
            error = false;
            System.out.println("Enter a price:");
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Whoops: Price must be a number");
                error = true;
            }
        } while (error);

        int quantity = 1;
        boolean error2;
        do {
            error2 = false;
            System.out.println("Enter quantity:");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity < 1) {
                    System.out.println("Whoops: Quantity must be a number greater than 0");
                    error2 = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Whoops: Quantity must be a number greater than 0");
                error2 = true;
            }
        } while (error2);

        Book newBook = new Book(bookName, store.getName(), genre, description, price);

        // current quantity of specified book
        Integer newBookCount = stock.get(newBook);

        if (newBookCount == null) { // could be replaced with merge, not sure if Vocareum will like?
            stock.put(newBook, quantity);
            store.getStock().put(newBook, quantity);
        } else {
            stock.put(newBook, newBookCount + quantity);
            store.getStock().put(newBook, newBookCount + quantity);
        }
        return true;
    }

    // edit, create, and delete books in specified store
    public void editStoreInventory(Scanner scanner, Store store) {
        HashMap<Book, Integer> stock = store.getStock();

        // makes sure that stock is always initialized
        if (stock == null)
            stock = new HashMap<>();

        boolean isEditingInventory = true;
        while (isEditingInventory) {
            System.out.println("What would you like to do:");
            System.out.println("1. Add new books");
            System.out.println("2. Remove books");
            System.out.println("3. Edit existing books");
            System.out.println("4. View books in store");
            System.out.println("5. DONE");

            switch (scanner.nextLine()) {
                case "1":
                    this.addBookMenu(scanner, store);

                    break;
                case "2":
                    if (stock.size() == 0) {
                        System.out.println("THERE ARE CURRENTLY NO BOOKS in " + store.getName());
                    } else {
                        // remove books
                        System.out.println("SELECT BOOK TO REMOVE");
                        System.out.println("*******************");

                        // cant select from hashmap by index
                        // gets key object from books arraylist with same order as hashmap
                        Book bookToRemove = selectBook(scanner, stock);

                        // checks if selectBook was cancelled and returned null
                        if (bookToRemove == null)
                            break;

                        // current quantity of specified book
                        Integer bookToRemoveCount = stock.get(bookToRemove);

                        // loops until a valid input is received
                        int quantityToRemove = -1;
                        while (quantityToRemove < 0) {
                            // asks Seller for quantity of items to remove
                            System.out.println("Please input the quantity you would like to remove:");

                            // prompts for the quantity of books to remove
                            String quantityInput = scanner.nextLine();

                            // try checks user inputted a valid number by attempting to parse the str into an int
                            try {
                                quantityToRemove = Integer.parseInt(quantityInput);

                                // makes sure user does not input negative number
                                if (quantityToRemove < 0)
                                    throw new NumberFormatException();

                            } catch (NumberFormatException e) {
                                System.out.println("PLEASE ENTER A VALID NUMBER");
                            }
                        }

                        // removes book from hashmap if final quantity is less than or equal 0
                        if (bookToRemoveCount - quantityToRemove <= 0) {
                            stock.remove(bookToRemove);
                        } else {
                            stock.put(bookToRemove, bookToRemoveCount - quantityToRemove);
                        }
                    }
                    break;
                case "3":
                    if (stock.size() == 0) {
                        System.out.println("THERE ARE CURRENTLY NO BOOKS in " + store.getName());
                    } else {
                        // edit books
                        System.out.println("SELECT BOOK TO EDIT");
                        System.out.println("*******************");

                        // cant select from hashmap by index
                        // gets key object from books arraylist with same order as hashmap
                        Book bookToEdit = selectBook(scanner, stock);

                        // checks if selectBook was cancelled and returned null
                        if (bookToEdit == null)
                            break;

                        boolean isEditingBook = true;
                        while (isEditingBook) {
                            System.out.println("EDITING BOOK " + bookToEdit.getName());
                            System.out.println("*******************");
                            System.out.println("Name: " + bookToEdit.getName());
                            System.out.println("Genre: " + bookToEdit.getGenre());
                            System.out.println("Description: " + bookToEdit.getDescription());
                            System.out.printf("Price: $%.2f\n", bookToEdit.finalPrice());

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
                    }
                    break;
                case "4":
                    if (stock.size() == 0) {
                        System.out.println("THERE ARE CURRENTLY NO BOOKS in " + store.getName());
                    } else {
                        // displays all books
                        System.out.println("CURRENT STOCK of " + store.getName());
                        System.out.println("*******************");

                        for (Book book : stock.keySet()) {
                            book.printBookListItem(null, stock.get(book));
                        }
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
    public Book selectBook(Scanner scanner, HashMap<Book, Integer> stock) {
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

            String selectionInput = scanner.nextLine();

            // try checks user inputted a valid number by attempting to parse the string into an int
            try {
                bookSelection = Integer.parseInt(selectionInput) - 1;

                // makes sure user does not input negative number or a number that isn't an option
                if (bookSelection < 0 || bookSelection > stores.size())
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER A VALID NUMBER");
            }
        }

        // ensures selection was not the cancel option or invalid
        if (bookSelection < books.size()) {
            // cant select from hashmap by index
            // gets key object from books arraylist with same order as hashmap
            return books.get(bookSelection);
        }

        return null;
    }

    // displays all stores a seller owns and lets seller select a store
    // returns user selected Store and null if operation cancelled
    public Store selectStore(Scanner scanner) {

        // checks that seller owns at least 1 store and asks if user wants to make a new store
        if (stores.size() == 0) {
            System.out.println("You currently do not own any stores.\nWould you like to create one?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            // brings up prompt to create a new store
            // recursion; could infinitely loop?
            if (scanner.nextLine().equals("1")) {
                // TODO: IdenticalStoreException not needed?
                try {
                    createNewStore(scanner, this);
                } catch (IdenticalStoreException e) {
                    System.out.println(e.getMessage());
                }
                // prompts user to select store again from the updated list
                return selectStore(scanner);
            }

        } else {
//            System.out.println("CHOOSE A STORE");
//            System.out.println("*******************");

            // loops until a valid input is inputted
            int storeSelection = -1;
            while (storeSelection > stores.size() || storeSelection < 0) {
                // displays stores the seller owns
                for (int i = 0; i < stores.size(); i++) {
                    System.out.println((i + 1) + ". " + stores.get(i).getName());
                }
                System.out.println((stores.size() + 1) + ". CANCEL");

                String selectionInput = scanner.nextLine();

                // try checks user inputted a valid number by attempting to parse the string into an int
                try {
                    storeSelection = Integer.parseInt(selectionInput) - 1;

                    // makes sure user does not input negative number or a number that isn't an option
                    if (storeSelection < 0 || storeSelection > stores.size())
                        throw new NumberFormatException();

                } catch (NumberFormatException e) {
                    System.out.println("PLEASE ENTER A VALID NUMBER");
                }
            }

            //  ensures selection was not the cancel option or invalid
            if (storeSelection < stores.size()) {
                System.out.println("CURRENT STORE SELECTION: " + stores.get(storeSelection).getName());
                return stores.get(storeSelection);
            }
        }

        return null;
    }

    // runs when a Buyer purchases a book
    public void updateStock(Book purchasedBook, int quantity, Buyer buyer) {
        // gets store where book was bought
        Store store = getStoreByName(purchasedBook.getStore());
        for (Book book : store.getStock().keySet()) {
            if (book.equals(purchasedBook)) {
                // removes book quantity bought from store
                // current quantity of specified book
                Integer bookToRemoveCount = store.getStock().get(book);

                // removes book from hashmap if final quantity is less than or equal 0
                if (bookToRemoveCount - quantity <= 0) {
                    store.getStock().remove(book);
                } else {
                    store.getStock().put(book, bookToRemoveCount - quantity);
                }

                // increments Seller's revenue
                stats.incrementRevenue(book.getPrice() * quantity);

                // adds Buyer to Seller's buyers stat
                Integer buyerCount = stats.getBuyers().get(buyer);

                // checks if Buyer has boughten from Seller before and increments if so, else adds new Buyer
                if (buyerCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                    stats.getBuyers().put(buyer, 1);
                } else {
                    stats.getBuyers().put(buyer, buyerCount + 1);
                }

                // the number of that book the Seller has sold
                Integer booksSoldCount = stats.getBooksSold().get(book);

                // adds number of books bought to the Seller's stats
                if (booksSoldCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                    stats.getBooksSold().put(book, quantity);
                } else {
                    stats.getBooksSold().put(book, booksSoldCount + quantity);
                }
            }

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

    public SellerStats getStats() {
        return stats;
    }

    public void setStats(SellerStats stats) {
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
