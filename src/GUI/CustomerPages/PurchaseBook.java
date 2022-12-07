package GUI.CustomerPages;

import Client.ClientQuery;
import Objects.Book;
import Objects.Marketplace;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PurchaseBook extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    JTextField bookSelection;
    JButton sortPrice;
    JButton sortQty;
    HashMap<Book, Integer> books;
    JTextArea listOfBooks;
    Query booksQuery;
    JButton selectBook;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            Book[] booksArr = new Book[books.size()];
            booksArr = books.keySet().toArray(booksArr);
            if (e.getSource() == sortPrice) {
                //books = (HashMap<Book, Integer>) booksQuery.getObject();

                Marketplace.sortBooksByPrice(booksArr);
                int i = 1;
                StringBuilder bookList = new StringBuilder();
                for (Book book : booksArr) { //Printing list of books available for sale

                    bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                                    "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                                    "Quantity: " + "[%d]\n",
                            i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), books.get(book)));
                    i++;
                }
                listOfBooks.setText(bookList.toString());
            }
            if (e.getSource() == sortQty) {
                booksArr = Marketplace.sortBooksByQuantity(books);
                int i = 1;
                StringBuilder bookList = new StringBuilder();
                for (Book book : booksArr) { //Printing list of books available for sale

                    bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                                    "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                                    "Quantity: " + "[%d]\n",
                            i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), books.get(book)));
                    i++;
                }
                listOfBooks.setText(bookList.toString());
            }
            if (e.getSource() == selectBook) {
                System.out.println(bookSelection.getText());
            }
        }
    };
    public void run() {
        booksQuery = new ClientQuery().getQuery(null, "books", "*");
        if (booksQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the books from the server");
        }
        books = (HashMap<Book, Integer>) booksQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        Book[] booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no books for sale\nCreate a new account and become a seller to start selling books");
        }
        //panel = new JPanel();
        frame = new JFrame("Purchase Book");
        content = frame.getContentPane();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        content.setLayout(new BorderLayout());
        //content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        //TODO: implement selecting a book
        panel = new JPanel();
        JLabel select = new JLabel("Select a book to buy (enter number):");
        panel.add(select);
        bookSelection = new JTextField(10);
        panel.add(bookSelection);
        selectBook = new JButton("Select");
        selectBook.addActionListener(actionListener);
        panel.add(selectBook);
        frame.add(panel, BorderLayout.NORTH);

        panel = new JPanel();
        int i = 1;
        StringBuilder bookList = new StringBuilder();
        for (Book book : booksArr) { //Printing list of books available for sale

            bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                            "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                            "Quantity: " + "[%d]\n",
                    i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), books.get(book)));
            i++;
        }
        listOfBooks = new JTextArea(bookList.toString());
        listOfBooks.setEditable(false);
        panel.add(listOfBooks);
        content.add(panel, BorderLayout.CENTER);

        panel = new JPanel();
        sortPrice = new JButton("Sort by Price");
//        sortPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortPrice.addActionListener(actionListener);
        panel.add(sortPrice);
        sortQty = new JButton("Sort by Quantity");
//        sortQty.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortQty.addActionListener(actionListener);
        panel.add(sortQty);
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        //frame.setSize();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseBook());
    }
}