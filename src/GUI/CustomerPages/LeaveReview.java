package GUI.CustomerPages;

import Client.ClientQuery;
import Objects.Review;
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
    JComboBox storeSelection;
    JTextArea reviewHeadline;
    JTextArea review;
    JTextField rating;
    JButton submit;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                String errorMessage = "Whoops: ";
                if (reviewHeadline.getText().isEmpty()) {
                    errorMessage += "Please include a headline";
                }
                try {
                    int r = Integer.parseInt(rating.getText());
                    if (r < 0 || r > 5) {
                        errorMessage += "\nYour rating must be an integer 0 through 5";
                    }
                } catch (NumberFormatException numberFormatException) {
                    errorMessage += "\nYour rating must be an integer 0 through 5";
                }
                if (!errorMessage.equals("Whoops: ")) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Review Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };



    public void run() {
        Query storesQuery = new ClientQuery().getQuery(null, "stores", "*");
        if (storesQuery.getObject().equals(false)) {
            JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the stores from the server");
        }
        @SuppressWarnings("unchecked")
        ArrayList<Store> stores = (ArrayList<Store>) storesQuery.getObject();
        // Convert the arraylist to an array, since its easier to manipulate
        Store[] storesArr = new Store[stores.size()];
        storesArr = stores.toArray(storesArr);
        if (storesArr.length < 1) {
            JOptionPane.showMessageDialog(null, "There are no stores in the market yet\nYou can create an new account and become a seller to open a store");
        }

        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        panel.add(storeSelection);
        content.add(panel,BorderLayout.NORTH);

        panel = new JPanel();
        reviewHeadline = new JTextArea("*Enter headline",150,1);
        reviewHeadline.setLineWrap(true);
        panel.add(reviewHeadline);
        review = new JTextArea("Leave review here.",150,3);
        review.setLineWrap(true);
        panel.add(review);
        content.add(panel, BorderLayout.CENTER);
        panel = new JPanel();
        rating = new JTextField("*Enter a rating out of 5",10);
        panel.add(rating);
        content.add(panel);

        panel = new JPanel();
        submit = new JButton("Submit");
        submit.addActionListener(actionListener);
        panel.add(rating);
        content.add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);


    }
}
