package GUI.CustomerPages;

import Client.BookApp;
import Client.ClientQuery;
import Objects.Buyer;
import Objects.Review;
import Objects.Seller;
import Objects.Store;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LeaveReview extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    ArrayList<Store> stores;
    JComboBox storeSelection;
    String selectedStoreName;
    JTextArea reviewHeadline;
    JTextArea review;
    JComboBox<Integer> ratingSelect;
    int rating;
    JButton submit;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                // extracts the name of the store from the drop down text
                selectedStoreName = stores.get(storeSelection.getSelectedIndex()).getName();
                //System.out.println(selectedStoreName);

                // handles edge cases
                String errorMessage = "Whoops: ";
                if (reviewHeadline.getText().equals("*Enter headline") || reviewHeadline.getText().isEmpty()) {
                    errorMessage += "Please include a headline";
                }

                try {
                    rating = (Integer) ratingSelect.getSelectedItem();
                    if (rating < 0 || rating > 5) {
                        errorMessage += "\nYour rating must be an integer 0 through 5";
                    }
                } catch (NumberFormatException numberFormatException) {
                    errorMessage += "\nYour rating must be an integer 0 through 5";
                }
                if (!errorMessage.equals("Whoops: ")) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Review Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (review.getText().equals("Leave review here.")) {
                        review.setText("");
                    }
                }

                // review functionality
                Query storeQuery = new ClientQuery().getQuery(selectedStoreName, "stores", "name");
                if (!(storeQuery.getObject() instanceof Store) || storeQuery.getObject().equals(false)) {
                    JOptionPane.showMessageDialog(null, "Whoops: We couldn't get that store from the server",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Store store = (Store) storeQuery.getObject();

                Query sellerQuery = new ClientQuery().getQuery(store.getSellerName(), "users", "name");
                if (!(sellerQuery.getObject() instanceof Seller)) {
                    JOptionPane.showMessageDialog(null, "Whoops: Couldn't get the owner of this store. Please go back and try again",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    //System.out.println("Please go back and try again");
                    return;
                }
                Seller seller = (Seller) sellerQuery.getObject();
                if (store.getReviews() == null) {
                    store.setReviews(new ArrayList<>());
                }
                store.getReviews().add(new Review(rating, (Buyer) BookApp.currentUser, seller.getName(), reviewHeadline.getText(), review.getText()));

                Query setReviewsQuery = new ClientQuery().updateQuery(store, "stores", "reviews", store.getReviews());
                if (setReviewsQuery.getObject().equals(false)) {
                    JOptionPane.showMessageDialog(null, "Whoops: Couldn't set the reviews",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    return;
                }

//                    try {
//                        Thread.sleep(1000); // For dramatic effect
//                    } catch (InterruptedException e) {
//                        System.out.println("Error: Program interruption");
//                    }
                JOptionPane.showMessageDialog(null, "Review Added Successfully!");
                frame.dispose();
            }
        }
    };



    public void run() {
        Query storesQuery = new ClientQuery().getQuery(null, "stores", "*");
        if (storesQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the stores from the server");
            return;
        }

        stores = (ArrayList<Store>) storesQuery.getObject();
        // Convert the arraylist to an array, since its easier to manipulate
        Store[] storesArr = new Store[stores.size()];
        storesArr = stores.toArray(storesArr);
        if (storesArr.length < 1) {
            JOptionPane.showMessageDialog(null, "There are no stores in the market yet\nYou can create an new account and become a seller to open a store");
            return;
        }

        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        storeSelection = new JComboBox<>();
        panel.add(new JLabel("Select a store:"));
        int i = 1;
        for (Store store : storesArr) { //Printing list of books available for sale
            storeSelection.addItem(i + ". " + store.getName() +
                    " -- Owner: " + store.getSellerName() +
                    " -- Rating:" + " " + Review.starDisplay(store.getAverageRating()));
            i++;
        }
        storeSelection.addItemListener(listener -> {
            JComboBox getSelection = (JComboBox) listener.getSource();
            rating = (Integer) getSelection.getSelectedItem();
            // extracts the name of the store from the drop down text
            selectedStoreName = ((String) getSelection.getSelectedItem()).split(" ")[1];
        });
        panel.add(storeSelection);
        content.add(panel,BorderLayout.NORTH);

        panel = new JPanel();
        reviewHeadline = new JTextArea("*Enter headline",1,50);
        reviewHeadline.setLineWrap(true);
        panel.add(reviewHeadline, BorderLayout.CENTER);
        content.add(panel);
        panel = new JPanel();
        review = new JTextArea("Leave review here.",3,50);
        review.setLineWrap(true);
        panel.add(review);
        content.add(panel);

        panel = new JPanel();

        ratingSelect = new JComboBox<>(new Integer[] {1,2,3,4,5});
        ratingSelect.addItemListener(listener -> {
            JComboBox getSelection = (JComboBox) listener.getSource();
            rating = (Integer) getSelection.getSelectedItem();
        });
        ratingSelect.addActionListener(actionListener);
        panel.add(ratingSelect);
        content.add(panel, BorderLayout.SOUTH);

        panel = new JPanel();
        submit = new JButton("Submit");
        submit.addActionListener(actionListener);
        panel.add(submit);
        content.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}