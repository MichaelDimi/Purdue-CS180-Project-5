package GUI.SellerPages;

import Client.ClientQuery;
import Objects.Book;
import Objects.Review;
import Objects.Store;
import Query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewReviews implements Runnable {
    String store;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    ArrayList<Review> reviewsList;
    JTextArea listOfReviews;
    JButton selectReview;
    JComboBox<String> jComboBox;
    //TODO: Import reviews
    Review[] reviews; //Temporary Array
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewReviews(null));
    }
    public ViewReviews(String store) {
        this.store = store;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectReview) {
                if (reviews != null) {
                    //TODO: Add review details
                JOptionPane.showMessageDialog(null, "Insert review details: Title, Description, Owner");
                }
            }
        }
    };
    public void run() {
        Query storesQuery = new ClientQuery().getQuery(store, "reviews", "store");
        if (storesQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the reviews from the server");
        }

        reviewsList = (ArrayList<Review>) storesQuery.getObject();
        // Convert the arraylist to an array, since its easier to manipulate
        Review[] storesArr = new Review[reviewsList.size()];
        storesArr = reviewsList.toArray(storesArr);
        if (storesArr.length < 1) {
            JOptionPane.showMessageDialog(null, "You have no reviews");
            return;
        }

        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4,4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        int i = 1;

        StringBuilder bookList = new StringBuilder();
        for (Review review : reviewsList) { //Printing list of books available for sale

            bookList.append(String.format("%d. %s -- Rating: %d -- Buyer: %s\n%s",
                    i, review.getTitle(), review.getRating(), review.getBuyer().getName(), review.getDescription()));
            i++;
        }
        listOfReviews = new JTextArea(bookList.toString());
        listOfReviews.setEditable(false);

        panel.add(listOfReviews);
        content.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
