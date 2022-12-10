package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ViewStats implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JButton salesByStore;
    JButton buyersByStore;
    JButton allSales;
    JButton allBuyers;
    JButton totalRevenue;
    JButton mostPopularGenre;
    //TODO: Import stores, buyers and sales
    String[] stores = {"a", "b", "c"}; //Temporary String
    String[] buyers = {"a", "b", "c"}; //Temporary String

    String[] sales = {"a", "b", "c"}; //Temporary String
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewStats());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == salesByStore) {
                String store = (String) JOptionPane.showInputDialog(null, "Select store", "View Stats",
                JOptionPane.QUESTION_MESSAGE, null, stores, null);
                //TODO: sort sales array to find the ones from selected store
                String[] sortedSalesArray = sales;
                if (sortedSalesArray != null) {
                    JOptionPane.showInputDialog(null, "Sales from: " + store, "View Stats",
                    JOptionPane.QUESTION_MESSAGE, null, sortedSalesArray, null);
                } else {
                    JOptionPane.showMessageDialog(null, "There are no sales from this store :(", "View Stats",
                    JOptionPane.INFORMATION_MESSAGE, null);
                }         
            } else if (e.getSource() == buyersByStore) {
                String store = (String) JOptionPane.showInputDialog(null, "Select store", "View Stats",
                JOptionPane.QUESTION_MESSAGE, null, stores, null);
                //TODO: sort buyers array to find the ones from selected store
                String[] sortedBuyersArray = buyers;
                if (sortedBuyersArray != null) {
                    JOptionPane.showInputDialog(null, "Buyers from: " + store, "View Stats",
                    JOptionPane.QUESTION_MESSAGE, null, sortedBuyersArray, null);
                } else {
                    JOptionPane.showMessageDialog(null, "There are no buyers from this store :(", "View Stats",
                    JOptionPane.INFORMATION_MESSAGE, null);
                }
            } else if (e.getSource() == allSales) {
                if (sales != null) {
                    JOptionPane.showInputDialog(null, "All Sales", "View Stats",
                    JOptionPane.QUESTION_MESSAGE, null, sales, null);
                } else {
                    JOptionPane.showMessageDialog(null, "There are no sales :(", "View Stats",
                    JOptionPane.INFORMATION_MESSAGE, null);
                }
            } else if (e.getSource() == allBuyers) {
                if (buyers != null) {
                    JOptionPane.showInputDialog(null, "All Buyers", "View Stats",
                    JOptionPane.QUESTION_MESSAGE, null, buyers, null);
                } else {
                    JOptionPane.showMessageDialog(null, "There are no buyers :(", "View Stats",
                    JOptionPane.INFORMATION_MESSAGE, null);
                }
            } else if (e.getSource() == totalRevenue) {
                //TODO: implement method to find total revenue
                double revenue = 0;
                JOptionPane.showMessageDialog(null, "Total Revenue: " + revenue, "View Stats",
                JOptionPane.INFORMATION_MESSAGE, null);
            } else if (e.getSource() == mostPopularGenre) {
                //TODO: implement method to find most popular genre
                String mostPopularGenre = null;
                JOptionPane.showMessageDialog(null, "Most Popular Genre: " + mostPopularGenre, "View Stats",
                JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
    };

    public void run() {
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4,4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title = new JLabel("View Stats");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        salesByStore = new JButton("Sales by Store");
        salesByStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        salesByStore.addActionListener(actionListener);
        optionPanel.add(salesByStore);

        buyersByStore = new JButton("Buyers by Store");
        buyersByStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyersByStore.addActionListener(actionListener);
        optionPanel.add(buyersByStore);

        allSales = new JButton("All Sales");
        allSales.setAlignmentX(Component.CENTER_ALIGNMENT);
        allSales.addActionListener(actionListener);
        optionPanel.add(allSales);

        allBuyers = new JButton("All Buyers");
        allBuyers.setAlignmentX(Component.CENTER_ALIGNMENT);
        allBuyers.addActionListener(actionListener);
        optionPanel.add(allBuyers);

        totalRevenue = new JButton("Total Revenue");
        totalRevenue.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalRevenue.addActionListener(actionListener);
        optionPanel.add(totalRevenue);

        mostPopularGenre = new JButton("Most Popular Genre");
        mostPopularGenre.setAlignmentX(Component.CENTER_ALIGNMENT);
        mostPopularGenre.addActionListener(actionListener);
        optionPanel.add(mostPopularGenre);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

}
