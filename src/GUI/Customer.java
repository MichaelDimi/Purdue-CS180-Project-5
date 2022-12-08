package GUI;

import GUI.CustomerPages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Customer implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JButton purchase;
    JButton search;
    JButton stores;
    JButton reviews;
    JButton history;
    JButton cart;
    JButton account;
    JButton signOut;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Customer());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        //TODO: invoke other GUI's
            if (e.getSource() == purchase) {
                SwingUtilities.invokeLater(new PurchaseBook());
            } else if (e.getSource() == search) {

            } else if (e.getSource() == stores) {
               SwingUtilities.invokeLater(new ViewStores());
            } else if (e.getSource() == reviews) {
                SwingUtilities.invokeLater(new LeaveReview());
            } else if (e.getSource() == history) {
                
            } else if (e.getSource() == cart) {
                
            } else if (e.getSource() == account) {
                
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel title = new JLabel("Customer Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        purchase = new JButton("Purchase a Book");
        purchase.setAlignmentX(Component.CENTER_ALIGNMENT);
        purchase.addActionListener(actionListener);
        optionPanel.add(purchase);

        search = new JButton(" Search for a Book");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.addActionListener(actionListener);
        optionPanel.add(search);

        stores = new JButton("View Stores and Reviews");
        stores.setAlignmentX(Component.CENTER_ALIGNMENT);
        stores.addActionListener(actionListener);
        optionPanel.add(stores);

        reviews = new JButton("Leave a Review");
        reviews.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviews.addActionListener(actionListener);
        optionPanel.add(reviews);

        history = new JButton("View Purchase History");
        history.setAlignmentX(Component.CENTER_ALIGNMENT);
        history.addActionListener(actionListener);
        optionPanel.add(history);

        cart = new JButton("Your Shopping Cart");
        cart.setAlignmentX(Component.CENTER_ALIGNMENT);
        cart.addActionListener(actionListener);
        optionPanel.add(cart);
        
        account = new JButton("Edit Account");
        account.setAlignmentX(Component.CENTER_ALIGNMENT);
        account.addActionListener(actionListener);
        optionPanel.add(account);

        signOut = new JButton("Sign Out");
        signOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        signOut.addActionListener(actionListener);
        optionPanel.add(signOut);

        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
