package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import GUI.*;
public class AddBook implements Runnable{
    String selectedStore;
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

    public AddBook(String selectedStore) {
        this.selectedStore = selectedStore;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addBook) {
                // //TODO: Add book and quanitity using JTextField data and add it to the database
                JOptionPane.showMessageDialog(null, "Book Successfully Added");
                SwingUtilities.invokeLater(new ManageStore(selectedStore));
                frame.dispose();
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
        
        title = new JLabel("Selected store: " + selectedStore);
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

        quantity = new JTextField("Enter quanitity");
        quantity.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantity.addActionListener(actionListener);
        optionPanel.add(quantity);

        addBook = new JButton("Add Book");
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
}
