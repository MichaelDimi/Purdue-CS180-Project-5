package GUI.CustomerPages;

import Client.BookApp;
import Client.ClientQuery;
import Objects.Book;
import Objects.Review;
import Objects.Store;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewStores extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    JComboBox storeSelection;
    JTextArea listOfStores;
    Store[] storesArr;
    String storesList;
    JButton sortNumberProdLtH; //Lowest to Highest
    JButton sortNumberProdHtL; //Highest to Lowest
    JButton sortFreqShopLtH;
    JButton sortFreqShopHtL;
    JButton selectStore;

    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectStore) {
                SwingUtilities.invokeLater(new SelectedStoreView());

            }
            if (e.getSource() == sortNumberProdHtL) {
                storesArr = BookApp.sortStoresByVarietyOfProducts(storesArr);

                // refreshes GUI
                int i = 1;
                storesList = "";

                for (Store store : storesArr) { //Printing list of books available for sale
                    storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                            + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                            + " -- Products Offered: " + store.getStock().size() + "\n";
                    i++;
                }
                listOfStores.setText(storesList);
            }
            if (e.getSource() == sortNumberProdLtH) {
                storesArr = BookApp.sortStoresByVarietyOfProducts(storesArr);
                Store[] revereArr = new Store[storesArr.length];
                for (int k = storesArr.length - 1; k >= 0; k--) {
                    // updates store array with reversed sorted array
                    revereArr[storesArr.length - k - 1] = storesArr[k];
                }

                storesArr = revereArr;

                // refreshes GUI
                int i = 1;
                storesList = "";

                for (Store store : storesArr) { //Printing list of books available for sale
                    storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                            + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                            + " -- Products Offered: " + store.getStock().size() + "\n";
                    i++;
                }
                listOfStores.setText(storesList);
            }
            if (e.getSource() == sortFreqShopHtL) {
                storesArr = BookApp.sortStoreByMostFrequentPurchases(storesArr);

                // refreshes GUI
                int i = 1;
                storesList = "";

                for (Store store : storesArr) { //Printing list of books available for sale
                    storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                            + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                            + " -- Products Offered: " + store.getStock().size() + "\n";
                    i++;
                }
                listOfStores.setText(storesList);
            }
            if (e.getSource() == sortFreqShopLtH) {
                storesArr = BookApp.sortStoreByMostFrequentPurchases(storesArr);
                Store[] revereArr = new Store[storesArr.length];
                for (int k = storesArr.length - 1; k >= 0; k--) {
                    // updates store array with reversed sorted array
                    revereArr[storesArr.length - k - 1] = storesArr[k];
                }

                storesArr = revereArr;

                // refreshes GUI
                int i = 1;
                storesList = "";

                for (Store store : storesArr) { //Printing list of books available for sale
                    storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                            + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                            + " -- Products Offered: " + store.getStock().size() + "\n";
                    i++;
                }
                listOfStores.setText(storesList);
            }
        }
    };
    public void run() {
        Query storesQuery = new ClientQuery().getQuery(null, "stores", "*");
        if (storesQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the stores from the server", "Error", JOptionPane.ERROR_MESSAGE);
        }
        @SuppressWarnings("unchecked")
        ArrayList<Store> stores = (ArrayList<Store>) storesQuery.getObject();
        // Convert the arraylist to an array, since its easier to manipulate
        storesArr = new Store[stores.size()];
        storesArr = stores.toArray(storesArr);

        if (storesArr.length < 1) {
            JOptionPane.showMessageDialog(null, "There are no stores in the market yet\nCreate an new account and become a seller to start a store");

        }

        // GUI
        panel = new JPanel();
        frame = new JFrame("View Stores");
        content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //content.setLayout(new BorderLayout());
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        // TODO: Implement store select
//        JLabel select = new JLabel("Select a store to see their books or reviews (enter number):");
//        panel.add(select);
//        storeSelection = new JTextField(10);
//        panel.add(storeSelection);
//        selectStore = new JButton("Select");
//        selectStore.addActionListener(actionListener);
//        panel.add(selectStore);
//        frame.add(panel, BorderLayout.NORTH);

        int i = 1;
        storesList = "";

        for (Store store : storesArr) { //Printing list of books available for sale
            storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                    + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                    + " -- Products Offered: " + store.getStock().size() + "\n";
            i++;
        }

        panel = new JPanel();
        listOfStores = new JTextArea(storesList);
        listOfStores.setEditable(false);
        panel.add(listOfStores);
        content.add(panel, BorderLayout.CENTER);


        panel = new JPanel();
        panel.add(new JLabel("Sort by Number of Products"));
        sortNumberProdLtH = new JButton("Lowest to Highest");
        sortNumberProdLtH.addActionListener(actionListener);
        panel.add(sortNumberProdLtH);
        sortNumberProdHtL = new JButton("Highest to Lowest");
        sortNumberProdHtL.addActionListener(actionListener);
        panel.add(sortNumberProdHtL);
        content.add(panel, BorderLayout.SOUTH);

        panel = new JPanel();
        panel.add(new JLabel("Sort by Frequently Shopped at"));
        sortFreqShopLtH = new JButton("Lowest to Highest");
        sortFreqShopLtH.addActionListener(actionListener);
        panel.add(sortFreqShopLtH);
        sortFreqShopHtL = new JButton("Highest to Lowest");
        sortFreqShopHtL.addActionListener(actionListener);
        panel.add(sortFreqShopHtL);
        content.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewStores());
    }

    public String SelectedStore() {
        String[] options = {"Stocks", "Reviews"};
        return (String) JOptionPane.showInputDialog(null,"Properties to view","Selected Store",JOptionPane.QUESTION_MESSAGE,null,options,0 );
    }

    public void updateList() {
        int i = 1;
        storesList = "";

        for (Store store : storesArr) { //Printing list of books available for sale
            storesList += i + ". " + store.getName() + " -- Owner: " + store.getSellerName()
                    + " -- Rating: " + Review.starDisplay(store.getAverageRating())
                    + " -- Products Offered: " + store.getStock().size() + "\n";
            i++;
        }
        listOfStores.setText(storesList);
    }
}
