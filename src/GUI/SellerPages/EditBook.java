package GUI.SellerPages;

import Objects.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class EditBook implements Runnable{
    Store store;
    //TODO: Add previous book info
    String bookTitle;
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
        SwingUtilities.invokeLater(new EditBook(null, null));
    }

    public EditBook(Store store, String bookTitle) {
        this.store = store;
        this.bookTitle = bookTitle;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == updateBook) {
                //TODO: Edit book info using JTextFields and update database
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
        
        title = new JLabel("Selected book: " + bookTitle);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        bookName = new JTextField(bookTitle);
        bookName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookName.addActionListener(actionListener);
        optionPanel.add(bookName);

        genre = new JTextField(bookGenre);
        genre.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre.addActionListener(actionListener);
        optionPanel.add(genre);

        description = new JTextField(bookDescription);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.addActionListener(actionListener);
        optionPanel.add(description);

        price = new JTextField(bookPrice);
        price.setAlignmentX(Component.CENTER_ALIGNMENT);
        price.addActionListener(actionListener);
        optionPanel.add(price);

        updateBook = new JButton("Edit Book");
        updateBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateBook.addActionListener(actionListener);
        optionPanel.add(updateBook);
        
        back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(actionListener);
        optionPanel.add(back);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}

