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

/*
System.out.println("PURCHASE A BOOK");
            System.out.println("*******************");
            Query booksQuery = new ClientQuery().getQuery(null, "books", "*");
            if (booksQuery.getObject().equals(false)) {
                System.out.println("Whoops: There was an error getting the books from the server");
                return true;
            }
            @SuppressWarnings("unchecked")
            HashMap<Book, Integer> books = (HashMap<Book, Integer>) booksQuery.getObject();

            // Convert the hashmap to an array, since its easier to manipulate
            Book[] booksArr = new Book[books.size()];
            booksArr = books.keySet().toArray(booksArr);

            if (books.isEmpty()) {
                System.out.println("There are no books for sale");
                System.out.println("Create a new account and become a seller to start selling books");
                return true;
            }
            // TODO: May fix this to not be text block
            System.out.println("""
                    How would you like to view the books?
                    1. Sorted by price
                    2. Sorted by quantity
                    3. Not sorted""");
            choice = scan.nextLine();
            if (choice.equals("1")) { // Lowest to highest
                Marketplace.sortBooksByPrice(booksArr);
            } else if (choice.equals("2")) {
                booksArr = Marketplace.sortBooksByQuantity(books);
            }

            System.out.println("Select a book to buy:");
            int i = 1;
            for (Book book : booksArr) { //Printing list of books available for sale
                book.printBookListItem(i, books.get(book));
                i++;
            }
            System.out.println(i + ". BACK");

            int response = Menu.selectFromList(i, scan);

            Book selection;
            // if the back option is selected
            if (response == i) {
                return true; // Go back
            } else {
                // book to be bought
                selection = booksArr[response - 1];
            }

            // shows more details about selected book and asks user how many copies of book to buy
            selectedBookMenu(scan, selection, buyer);
 */
public class PurchaseBook extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    JButton sortPrice;
    JButton sortQty;
    HashMap<Book, Integer> books;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sortPrice) {
                Book[] booksArr = new Book[books.size()];
                booksArr = books.keySet().toArray(booksArr);
                Marketplace.sortBooksByPrice(booksArr);

            }
            if (e.getSource() == sortQty) {
                System.out.println("qty sort");
            }
        }
    };
    public void run() {
        Query booksQuery = new ClientQuery().getQuery(null, "books", "*");
        if (booksQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the books from the server");
        }
        @SuppressWarnings("unchecked")
        HashMap<Book, Integer> books = (HashMap<Book, Integer>) booksQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        Book[] booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no books for sale\nCreate a new account and become a seller to start selling books");
        }
        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new BorderLayout());
        //content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JLabel frametitle = new JLabel("Purchase a Book!");
        frametitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(frametitle);
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
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseBook());
    }
}
