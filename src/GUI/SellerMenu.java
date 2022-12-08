package GUI;

import javax.swing.*;
import GUI.SellerPages.*;
import java.awt.*;
import java.awt.event.*;
public class SellerMenu implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JButton createStore;
    JButton manageStore;
    JButton addSale;
    JButton viewReviews;
    JButton viewStats;
    JButton viewCarts;
    JButton importExport;
    JButton signOut;
    //TODO: import all stores, books and carts
    String[] stores = {"a", "b", "c"}; //Temporary Array
    String[] books = {"a", "b", "c"}; //Temporary Array
    String[] carts = {"a", "b", "c"}; //contains info about carts, in this order: Buyer, Book and Qty
    //eg: {"user1|book1|5", "user2|book2|4"}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SellerMenu());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        //TODO: invoke other GUI's
            if (e.getSource() == createStore) {
                SwingUtilities.invokeLater(new CreateStore());
                frame.dispose();
            } else if (e.getSource() == manageStore) {
                String store = (String) JOptionPane.showInputDialog(null, "Select store", "Manage store",
                    JOptionPane.QUESTION_MESSAGE, null, stores, null);
                    if (store != null) {
                        SwingUtilities.invokeLater(new ManageStore(store));
                        frame.dispose();
                    }
            } else if (e.getSource() == addSale) {
                //TODO: Add the specific sale to the database
                String book = (String) JOptionPane.showInputDialog(null, "Select book", "Manage store",
                JOptionPane.QUESTION_MESSAGE, null, books, null);
                if (book != null) {
                    try {
                        int sale = Integer.parseInt(JOptionPane.showInputDialog(null, "Select sale Eg. 20 = 20% off", "Manage Store",
                        JOptionPane.QUESTION_MESSAGE));
                        if (sale < 0 || sale >= 100) {
                            throw new NumberFormatException();
                        }
                        JOptionPane.showMessageDialog(null, "Sale added");
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null, "Invalid Amount", "Manage store",
                                JOptionPane.INFORMATION_MESSAGE, null);
                        }
                    }
            } else if (e.getSource() == viewReviews) {
                String store = (String) JOptionPane.showInputDialog(null, "Select store", "Seller Menu",
                JOptionPane.QUESTION_MESSAGE, null, stores, null);
                if (store != null) {
                    SwingUtilities.invokeLater(new ViewReviews(store));
                    frame.dispose();
                }
            } else if (e.getSource() == viewStats) {
                SwingUtilities.invokeLater(new ViewStats());
                frame.dispose();
            } else if (e.getSource() == viewCarts) {
                JOptionPane.showInputDialog(null, "View Carts", "Seller Menu",
                JOptionPane.QUESTION_MESSAGE, null, carts, null);
            } else if (e.getSource() == importExport) {
                SwingUtilities.invokeLater(new ImportExport());
                frame.dispose();
            } else if (e.getSource() == signOut) {
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
        
        title = new JLabel("Seller Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        createStore = new JButton("Create Store");
        createStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        createStore.addActionListener(actionListener);
        optionPanel.add(createStore);

        manageStore = new JButton("Manage Store");
        manageStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageStore.addActionListener(actionListener);
        optionPanel.add(manageStore);

        addSale = new JButton("Add Sale");
        addSale.setAlignmentX(Component.CENTER_ALIGNMENT);
        addSale.addActionListener(actionListener);
        optionPanel.add(addSale);

        viewReviews = new JButton("View Reviews");
        viewReviews.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewReviews.addActionListener(actionListener);
        optionPanel.add(viewReviews);
        
        viewStats = new JButton("View Stats");
        viewStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStats.addActionListener(actionListener);
        optionPanel.add(viewStats);

        viewCarts = new JButton("View Carts");
        viewCarts.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCarts.addActionListener(actionListener);
        optionPanel.add(viewCarts);

        importExport = new JButton("Import/Export");
        importExport.setAlignmentX(Component.CENTER_ALIGNMENT);
        importExport.addActionListener(actionListener);
        optionPanel.add(importExport);

        signOut = new JButton("Sign Out");
        signOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        signOut.addActionListener(actionListener);
        optionPanel.add(signOut);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
