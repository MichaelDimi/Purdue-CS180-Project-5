package GUI.SellerPages;

import Client.ClientQuery;
import Objects.Book;
import Objects.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class EditBook implements Runnable{
    Store store;
    Book book;
    HashMap<Book, Integer> storeStock;
    String bookGenre;
    String bookDescription;
    String bookPrice;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JTextField bookName;
    JTextField genre;
    JTextField description;
    JTextField price;
    JButton updateBook;
    JButton back;
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new EditBook(null, null));
    }

    public EditBook(Store store, Book book, HashMap<Book, Integer> storeStock) {
        this.store = store;
        this.book = book;
        this.storeStock = storeStock;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == updateBook) {
                int bookQty = storeStock.get(book);

                //TODO: Edit book info using JTextFields and update database
                if (!book.getName().equals(bookName.getText())) {
                    book.setName(bookName.getText());
                }

                if (!book.getGenre().equals(genre.getText())) {
                    book.setGenre(bookName.getText());
                }

                if (!book.getDescription().equals(description.getText())) {
                    book.setDescription(bookName.getText());
                }

                if (book.getPrice() != Double.parseDouble(price.getText())) {
                    book.setPrice(Double.parseDouble(price.getText()));
                }

                storeStock.remove(book);
                storeStock.put(book, bookQty);

                // updates stock on server
                new ClientQuery().updateQuery(store, "stock", "set", storeStock);

                JOptionPane.showMessageDialog(null, "Book Successfully Updated");
                SwingUtilities.invokeLater(new ManageStore(store));
                frame.dispose();
            } else if (e.getSource() == back) {
                SwingUtilities.invokeLater(new ManageStore(store));
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
        
        title = new JLabel("Selected book: " + book.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        bookName = new JTextField(book.getName());
        bookName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookName.addActionListener(actionListener);
        optionPanel.add(bookName);

        genre = new JTextField(book.getGenre());
        genre.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre.addActionListener(actionListener);
        optionPanel.add(genre);

        description = new JTextField(book.getDescription());
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.addActionListener(actionListener);
        optionPanel.add(description);

        price = new JTextField(String.valueOf(book.getPrice()));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);
        price.addActionListener(actionListener);
        optionPanel.add(price);

        back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(actionListener);
        optionPanel.add(back);

        updateBook = new JButton("Save");
        updateBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateBook.addActionListener(actionListener);
        optionPanel.add(updateBook);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}

