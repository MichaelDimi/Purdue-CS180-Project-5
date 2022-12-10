package GUI.SellerPages;

import Client.BookApp;
import Client.ClientQuery;
import Objects.Book;
import Objects.Seller;
import Objects.Store;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Scanner;

public class AddBook implements Runnable{
    Store selectedStore;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JTextField bookName;
    JTextField genre;
    JTextField description;
    JTextField price;
    JTextField quantity;
    JButton addBook;
    JButton back;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new AddBook(null));
    }

    public AddBook(Store selectedStore) {
        this.selectedStore = selectedStore;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addBook) {
                boolean addBookSuccess = addBook(selectedStore, bookName.getText(), genre.getText(), description.getText(), price.getText(), quantity.getText());
                if (!addBookSuccess) {
                    JOptionPane.showMessageDialog(null, "Please make sure inputs are valid",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Book Successfully Added");
                    SwingUtilities.invokeLater(new ManageStore(selectedStore));
                    frame.dispose();
                }
            } else if (e.getSource() == back) {
                SwingUtilities.invokeLater(new ManageStore(selectedStore));
                frame.dispose();
            }
        }
    };
    public void run() {
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title = new JLabel("Selected store: " + selectedStore.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        bookName = new JTextField("Enter book name");
        bookName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookName.addActionListener(actionListener);
        optionPanel.add(bookName);

        genre = new JTextField("Enter genre");
        genre.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre.addActionListener(actionListener);
        optionPanel.add(genre);

        description = new JTextField("Enter book description");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.addActionListener(actionListener);
        optionPanel.add(description);

        price = new JTextField("Enter price");
        price.setAlignmentX(Component.CENTER_ALIGNMENT);
        price.addActionListener(actionListener);
        optionPanel.add(price);

        quantity = new JTextField("Enter quanitity (number)");
        quantity.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantity.addActionListener(actionListener);
        optionPanel.add(quantity);

        addBook = new JButton("Add Book (number)");
        addBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBook.addActionListener(actionListener);
        optionPanel.add(addBook);
        
        back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(actionListener);
        optionPanel.add(back);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public static boolean addBook(Store store, String bookName, String genre, String description, String priceString, String quantityString) {
        Seller seller = (Seller) BookApp.currentUser;

        HashMap<Book, Integer> stock = store.getStock();

//        System.out.println("Enter a price:");
//        try {
//            price = Double.parseDouble(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            System.out.println("Whoops: Price must be a number");
//            return false;
//        }
        double price = 0;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            System.out.println("Whoops: Price must be a number");
            return false;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity < 1) {
                System.out.println("Whoops: Quantity must be a number greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Whoops: Quantity must be a number greater than 0");
            return false;
        }

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

        // updates stock on server
        new ClientQuery().updateQuery(store, "stock", "set", store.getStock());
        return true;
    }
}
