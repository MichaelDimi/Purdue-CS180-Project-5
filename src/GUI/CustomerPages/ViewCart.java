package GUI.CustomerPages;

import Client.BookApp;
import Client.ClientQuery;
import Exceptions.BookNotFoundException;
import Objects.Book;
import Objects.Buyer;
import Objects.User;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ViewCart implements Runnable {
    Query booksQuery;
    HashMap<Book, Integer> currentCart;
    Book[] cartArr;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JTextArea listOfBooks;
    JButton remove;
    JButton clear;
    JButton checkout;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewCart());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //TODO: display message if cart is empty
            HashMap<Book, Integer> cart =((Buyer) BookApp.currentUser).getCart();
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Your cart is empty");
            }
        
            if (e.getSource() == remove) {
                // gets name of books in cart
                String[] bookNames = new String[cartArr.length];
                for (int i = 0; i < cartArr.length; i++) {
                    bookNames[i] = cartArr[i].getName();
                }

                String selectedBookName = (String)JOptionPane.showInputDialog(null, "Choose a book to remove",
                        null, JOptionPane.QUESTION_MESSAGE, null, bookNames, bookNames[0]);
                if (selectedBookName.equals("Cancel")) return;
                String quantityToRemoveString = JOptionPane.showInputDialog("Quantity to remove");

                // makes sure inputted quantity is valid and gets selected book object by name
                try {
                    //System.out.println(selectedBook);
                    int quantityToRemove = Integer.parseInt(quantityToRemoveString);

                    // makes sure user does not input negative number
                    if (quantityToRemove < 0)
                        throw new NumberFormatException();

                    Book selectedBook = null;
                    for (Book book : cartArr) {
                        if (book.getName().equals(selectedBookName))
                            selectedBook = book;
                    }

                    if (selectedBook == null) {
                        JOptionPane.showMessageDialog(null, "This book is no longer available");
                    } else {
                        // removes book from the buyer's cart
                        try {
                            ((Buyer) BookApp.currentUser).removeFromCart(selectedBook, quantityToRemove);
                        } catch (BookNotFoundException err) {
                            JOptionPane.showMessageDialog(null, "This book is no longer available");
                        }

                        // "refreshes" cart
                        frame.dispose();
                        run();
                        //((Buyer) BookApp.currentUser).addToCart(selectedBook, quantityToRemove);
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid quantity",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                //((Buyer) BookApp.currentUser).removeFromCart();
            } else if (e.getSource() == clear) {
                ((Buyer) BookApp.currentUser).clearCart();
                // "refreshes" cart
                frame.dispose();
            } else if (e.getSource() == checkout) {
                ((Buyer) BookApp.currentUser).checkoutCart();
                // "refreshes" cart
                frame.dispose();
            }
        }
    };
    public void run() {
        BookApp.updateCurrentUser();

        // gets updated cart of buyer
        booksQuery = new ClientQuery().getQuery(BookApp.currentUser, "cart", "currentBuyer");
        if (booksQuery.getObject() == null || booksQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Your cart cannot be retrieved");
            return;
        }
        // gets book names in cart
        currentCart = (HashMap<Book, Integer>) booksQuery.getObject();

        // Convert the hashmap to an array, since its easier to manipulate
        cartArr = new Book[currentCart.size()];
        cartArr = currentCart.keySet().toArray(cartArr);

        if (cartArr.length <= 0) {
            JOptionPane.showMessageDialog(null, "Your cart is empty");
            return;
        }

        // GUI
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame("Your Cart");
        Container content = frame.getContentPane();

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.add(panel, BorderLayout.NORTH);

        // book display
        panel = new JPanel();
        int i = 1;

        StringBuilder bookList = new StringBuilder();
        for (Book book : cartArr) { //Printing list of books available for sale

            bookList.append(String.format("%d. %s -- Store: %s -- Original Price: $%.2f " +
                            "-- Percent off: %.2f%% -- Final price: $%.2f -- " +
                            "Quantity: " + "[%d]\n",
                    i, book.getName(), book.getStore(), book.getPrice(), book.getPercentOff(), book.finalPrice(), currentCart.get(book)));
            i++;
        }
        listOfBooks = new JTextArea(bookList.toString());
        listOfBooks.setEditable(false);

        panel.add(listOfBooks);
        content.add(panel, BorderLayout.CENTER);

        panel = new JPanel();
        remove = new JButton("Remove Items");
        remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        remove.addActionListener(actionListener);
        optionPanel.add(remove);

        clear = new JButton("Clear Cart");
        clear.setAlignmentX(Component.CENTER_ALIGNMENT);
        clear.addActionListener(actionListener);
        optionPanel.add(clear);

        checkout = new JButton("Checkout");
        checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkout.addActionListener(actionListener);
        optionPanel.add(checkout);

        content.add(optionPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
