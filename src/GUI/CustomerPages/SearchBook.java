package GUI.CustomerPages;

import Client.ClientQuery;
import Objects.Book;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SearchBook extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    JTextField searchField;
    HashMap<Book, Integer> books;
    Book[] booksArr;
    JTextArea listOfBooks;
    Query booksQuery;
    JButton selectBook;

    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectBook) {
                Query booksQuery = new ClientQuery().getQuery(searchField.getText(), "books", "find");
                if (booksQuery.getObject().equals(false)) {
                    JOptionPane.showMessageDialog(null, "Whoops: There was an error finding books",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                @SuppressWarnings("unchecked")
                HashMap<Book, Integer> books = (HashMap<Book, Integer>) booksQuery.getObject();

                booksArr = new Book[books.size()];
                booksArr = books.keySet().toArray(booksArr);

                int i = 1;

                StringBuilder bookList = new StringBuilder();

                // when no books match search query
                if (booksArr.length <= 0) {
                    bookList.append("No books found.");
                    listOfBooks.setText(bookList.toString());
                    return;
                }

                for (Book book : booksArr) { //Printing list of books available for sale

                    bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                                    "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                                    "Quantity: " + "[%d]\n",
                            i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), books.get(book)));
                    i++;
                }
                listOfBooks.setText(bookList.toString());
            }
        }
    };
    public void run() {
        booksQuery = new ClientQuery().getQuery(null, "books", "*");
        if (booksQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the books from the server",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //TODO: display message when there's a server error
        // if (booksQuery.getObject().equals(false)) {
        //     JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the books from the server");
        // }
        books = (HashMap<Book, Integer>) booksQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        if (booksArr.length <= 0) {
            JOptionPane.showMessageDialog(null, "There are no books for sale\nCreate a new account and become a seller to start selling books");
            return;
        }

        frame = new JFrame("Search For A Book");
        content = frame.getContentPane();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        content.setLayout(new BorderLayout());

        //TODO: implement selecting a book
        panel = new JPanel();
        JLabel select = new JLabel("Enter a search query (name, genre, or description):");
        panel.add(select);
        searchField = new JTextField(10);
        panel.add(searchField);
        selectBook = new JButton("Search");
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

        
        frame.pack();
        //frame.setSize();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SearchBook());
    }
}