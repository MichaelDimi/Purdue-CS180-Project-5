package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Client.BookApp;
import Client.ClientQuery;
import GUI.*;
import Objects.Book;
import Objects.Seller;
import Objects.Store;
import Query.Query;

public class ManageStore implements Runnable {
    Store store;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JButton changeName;
    JButton addBooks;
    JButton removeBooks;
    JButton editBooks;
    JButton deleteStore;
    JButton back;
    Query storeStockQuery;
    HashMap<Book, Integer> storeStock;
    Book[] storeStockArr;
    String[] books = {"a", "b", "c"}; //Temporary Array
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ManageStore(null));
    }
    public ManageStore(Store store) {
        this.store = store;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == changeName) {
                String newStoreName = JOptionPane.showInputDialog(null, "Type new store name", "Manage Store",
                JOptionPane.QUESTION_MESSAGE);
                if (newStoreName != null) {
                    editStoreName(store, newStoreName);
                    JOptionPane.showMessageDialog(null, "Store Name Changed");
                    SwingUtilities.invokeLater(new SellerMenu());
                    frame.dispose();
                }
            } else if (e.getSource() == addBooks) {
                SwingUtilities.invokeLater(new AddBook(store));
                frame.dispose();   
            } else if (e.getSource() == removeBooks) {
                if (storeStockArr.length <= 0) {
                    JOptionPane.showMessageDialog(null, "You have no stores",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //TODO: Remove the specified amount of a book from a database
                int amount = 0;
                String bookName = (String) JOptionPane.showInputDialog(null, "Select book", "Manage store",
                        JOptionPane.QUESTION_MESSAGE, null, books, null);

                // this is duplicated down from below, this is bad but i dont care
                // gets quantity of selected bookName
                int currQuantity = 0;
                for (Book book : storeStockArr) {
                    if (book.getName().equals(bookName)) {
                        currQuantity = storeStock.get(book);
                        break;
                    }
                }
                if (bookName != null) {
                    try {
                        amount = Integer.parseInt(JOptionPane.showInputDialog(null, String.format("Select amount to remove (current qty: %d)", currQuantity), "Manage Store",
                        JOptionPane.QUESTION_MESSAGE));
                        JOptionPane.showMessageDialog(null, "Book successfully removed");
                    } catch (Exception er) {
                        JOptionPane.showMessageDialog(null, "Invalid Amount", "Manage store",
                            JOptionPane.INFORMATION_MESSAGE, null);
                    }

                    // finds selected book to remove
                    for (Book book : storeStockArr) {
                        if (book.getName().equals(bookName)) {
                            if (storeStock.get(book) - amount <= 0) {
                                System.out.println("tf");
                                storeStock.remove(book);
                            } else {
                                System.out.println("wtf");
                                storeStock.put(book, storeStock.get(book) - amount);
                            }

                            // updates stock on server
                            new ClientQuery().updateQuery(store, "stock", "set", storeStock);
                            break;
                        }
                    }

                }
            } else if (e.getSource() == editBooks) {
                if (storeStockArr.length <= 0) {
                    JOptionPane.showMessageDialog(null, "You have no stores",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String book = (String) JOptionPane.showInputDialog(null, "Select book", "Manage store",
                JOptionPane.QUESTION_MESSAGE, null, books, null);
                if (book != null) {
                    SwingUtilities.invokeLater(new EditBook(store, book));
                    frame.dispose();
                }
            } else if (e.getSource() == deleteStore) {
                Query deleteStoreQuery = new ClientQuery().deleteQuery(store, "stores");
                if (deleteStoreQuery.getObject().equals(false)) {
                    System.out.println("Whoops: Couldn't delete your store. Please try again");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Store Successfully Deleted");
                SwingUtilities.invokeLater(new SellerMenu());
                frame.dispose();
            } else if (e.getSource() == back) {
                SwingUtilities.invokeLater(new SellerMenu());
                frame.dispose();
            }
        }
    };
    public void run() {
        // gets updated store stock
        storeStockQuery = new ClientQuery().getQuery(store.getName(), "stores", "books");
        if (storeStockQuery.getObject() == null || storeStockQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Your cart cannot be retrieved",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // gets book names in cart
        storeStock = (HashMap<Book, Integer>) storeStockQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        storeStockArr = new Book[storeStock.size()];
        storeStockArr = storeStock.keySet().toArray(storeStockArr);

        books = new String[storeStockArr.length];
        for (int i = 0; i < storeStockArr.length; i++) {
            books[i] = storeStockArr[i].getName();
        }

        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4,4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title = new JLabel("Selected store: " + store.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        /* 
        String[] options = {};
        jComboBox = new JComboBox<>(options);
        jComboBox.setBounds(50, 150, 300, 20);
        optionPanel.add(jComboBox);
        */
        
        changeName = new JButton("Change Name");
        changeName.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeName.addActionListener(actionListener);
        optionPanel.add(changeName);

        addBooks = new JButton("Add Books");
        addBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBooks.addActionListener(actionListener);
        optionPanel.add(addBooks);

        removeBooks = new JButton("Remove Books");
        removeBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBooks.addActionListener(actionListener);
        optionPanel.add(removeBooks);

        editBooks = new JButton("Edit Books");
        editBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        editBooks.addActionListener(actionListener);
        optionPanel.add(editBooks);

        deleteStore = new JButton("Delete Store");
        deleteStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStore.addActionListener(actionListener);
        // deleteStore.setBounds(20, 20, 0, 0);
        optionPanel.add(deleteStore);

        back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(actionListener);
        optionPanel.add(back);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // logic
    public void editStoreName(Store store, String storeName) {
        Query updateStoreNameQuery = new ClientQuery().updateQuery(store, "stores", "name", storeName);
        if (updateStoreNameQuery.getObject().equals(false)) {
            System.out.println("Whoops: Couldn't rename your store");
            if (updateStoreNameQuery.getOptions().equals("taken")) {
                System.out.println("Tip: That store is taken. Try a different name");
            }
        }
    }

    public static boolean addBookMenu(Scanner scanner, Store store) {
        Seller seller = (Seller) BookApp.currentUser;

        if (store == null) {
            boolean error;
            do {
                if (seller.getStores() == null || seller.getStores().isEmpty()) {
                    // User has no stores
                    return false;
                }
                error = false;
                System.out.println("What store would you like to add a book to?");
                String storeName = scanner.nextLine();

                store = seller.getStoreByName(storeName);
                if (store == null) {
                    error = true;
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

        // Adds books to stock
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

    public Book selectBook(Scanner scanner, HashMap<Book, Integer> stock) {
        Seller seller = (Seller) BookApp.currentUser;

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
                if (bookSelection < 0 || bookSelection > seller.getStores().size())
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

        Seller seller = (Seller) BookApp.currentUser;

        // checks that seller owns at least 1 store and asks if user wants to make a new store
        if (seller.getStores().size() == 0) {
            System.out.println("You currently do not own any stores.\nWould you like to create one?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            // brings up prompt to create a new store
            // recursion; could infinitely loop?
            if (scanner.nextLine().equals("1")) {
                // TODO: IdenticalStoreException not needed?
//                try {
//                    seller.createNewStore(scanner, seller);
//                } catch (IdenticalStoreException e) {
//                    System.out.println(e.getMessage());
//                }
                // prompts user to select store again from the updated list
                return selectStore(scanner);
            }

        } else {
//            System.out.println("CHOOSE A STORE");
//            System.out.println("*******************");

            // loops until a valid input is inputted
            int storeSelection = -1;
            ArrayList<Store> stores = seller.getStores();
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
}
