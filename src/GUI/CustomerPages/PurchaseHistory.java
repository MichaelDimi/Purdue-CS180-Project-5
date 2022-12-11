package GUI.CustomerPages;

import Client.BookApp;
import Client.ClientQuery;
import Client.FileIOMenu;
import Objects.Book;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PurchaseHistory implements Runnable {
    Query puchaseHistoryQuery;
    HashMap<Book, Integer> pastPurchases;
    Book[] pastPurchasesArr;
    JFrame frame;
    JPanel panel;
    JTextArea listOfBooks;
    JButton export;
    JButton no;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseHistory());
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == export) {
                FileIOMenu fileIOMenu = new FileIOMenu();
                fileIOMenu.fileIOMenu(BookApp.currentUser);
            }
        }
    };

    public void run() {
        BookApp.updateCurrentUser();

        // gets updated purchase history of buyer
        puchaseHistoryQuery = new ClientQuery().getQuery(BookApp.currentUser, "purchaseHistory", "*");
        if (puchaseHistoryQuery.getObject() == null || puchaseHistoryQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Your cart cannot be retrieved",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // gets book names in cart
        pastPurchases = (HashMap<Book, Integer>) puchaseHistoryQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        pastPurchasesArr = new Book[pastPurchases.size()];
        pastPurchasesArr = pastPurchases.keySet().toArray(pastPurchasesArr);

        // GUI
        panel = new JPanel();
        frame = new JFrame("View and Export Purchase History");
        Container content = frame.getContentPane();

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        // book display
        panel = new JPanel();
        int i = 1;

        StringBuilder bookList = new StringBuilder();
        // checks if user has a purchase history
        if (pastPurchases.size() > 0) {
            for (Book book : pastPurchasesArr) { //Printing list of books available for sale

                bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                                "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                                "Quantity: " + "[%d]\n",
                        i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), pastPurchases.get(book)));
                i++;
            }
        } else {
            bookList.append("You have never bought anything");
        }

        listOfBooks = new JTextArea(bookList.toString());
        listOfBooks.setEditable(false);
        panel.add(listOfBooks);
        content.add(panel, BorderLayout.CENTER);

        panel = new JPanel();
        export = new JButton("Export purchase history");
        panel.add(export);
        content.add(panel, BorderLayout.CENTER);

        frame.pack();
        //frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
