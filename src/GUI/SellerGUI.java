package GUI;

import Client.BookApp;
import GUI.SellerPages.CreateStore;
import GUI.SellerPages.ViewReviews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SellerGUI implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JButton createStore;
    JButton editStore;
    JButton deleteStore;
    JButton addSale;
    JButton viewReviews;
    JButton viewStats;
    JButton viewCarts;
    JButton signOut;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SellerGUI());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //TODO: invoke other GUI's
            if (e.getSource() == createStore) {
                SwingUtilities.invokeLater(new CreateStore());
            } else if (e.getSource() == editStore) {
                //SwingUtilities.invokeLater(new ());
            } else if (e.getSource() == deleteStore) {
                //SwingUtilities.invokeLater(new CreateStore());
            } else if (e.getSource() == addSale) {
                SwingUtilities.invokeLater(new CreateStore());
            } else if (e.getSource() == viewReviews) {
                SwingUtilities.invokeLater(new ViewReviews());
            } else if (e.getSource() == viewStats) {

            } else if (e.getSource() == viewCarts) {

            } else if (e.getSource() == signOut) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "You have been signed out");
                BookApp.signOut();
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

        JLabel title = new JLabel("Seller Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        createStore = new JButton("Create Store");
        createStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        createStore.addActionListener(actionListener);
        optionPanel.add(createStore);

        editStore = new JButton("Edit Store");
        editStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        editStore.addActionListener(actionListener);
        optionPanel.add(editStore);

        deleteStore = new JButton("Delete Store");
        deleteStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStore.addActionListener(actionListener);
        optionPanel.add(deleteStore);

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

        signOut = new JButton("Sign Out");
        signOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        signOut.addActionListener(actionListener);
        optionPanel.add(signOut);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}