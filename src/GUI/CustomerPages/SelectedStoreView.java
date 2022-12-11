package GUI.CustomerPages;

import Objects.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SelectedStoreView extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    JComboBox bookSelection;
    JButton selectBook;
    JTextField quantitySelection;
    JTextArea listOfBooks;
    JTextArea reviewsTextArea;
    JComboBox<Integer> reviewSelection;
    JButton selectReview;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
        }
    };

    @Override
    public void run() {

        panel = new JPanel();
//        String storeName = store.getName();
//        frame = new JFrame(storeName);
        frame = new JFrame("Selected Store");
        content = frame.getContentPane();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] options = {"Stocks", "Reviews"};
        String choice = (String) JOptionPane.showInputDialog(null,"Properties to view","Selected Store",JOptionPane.QUESTION_MESSAGE,null,options,0 );
        if (choice.equals("Stocks")) {
            panel.add(new JLabel("Select book from stocks:"));
            content.add(panel);
//            bookSelection = new JComboBox<>(bookNames);
            bookSelection = new JComboBox<>(new Integer[] {1,2,3,4}); //just for testing
//            selectedBookName = bookNames[0];
//            bookSelection.addItemListener(listener -> {
//                JComboBox getSelection = (JComboBox) listener.getSource();
//                selectedBookName = (String) getSelection.getSelectedItem();
//
//            });
            panel.add(bookSelection);
            JLabel quantity = new JLabel("Quantity:");
            panel.add(quantity);
            quantitySelection = new JTextField(3);
            panel.add(quantitySelection);

            selectBook = new JButton("Select");
            selectBook.addActionListener(actionListener);
            panel.add(selectBook);

            frame.add(panel, BorderLayout.NORTH);

            panel = new JPanel();
//            int j = 1;
//            for (Book book : stockArr) { //Printing list of books available for sale
//                book.printBookListItem(j, stock.get(book));
//                j++;
//            }
//            listOfBooks = new JTextArea(bookList.toString());
            listOfBooks = new JTextArea("Place holder for bookList.toString()");
            listOfBooks.setEditable(false);

            panel.add(listOfBooks);
            content.add(panel, BorderLayout.CENTER);

        } else {
//            panel.add(new JLabel("Reviews for:" + store.getName());
            panel.add(new JLabel("Reviews for: store name"));
            content.add(panel, BorderLayout.NORTH);
            panel = new JPanel();
            panel.add(new JLabel("Select a review to see more:"));

//            reviewSelection = new JComboBox<>(reviews);
            reviewSelection = new JComboBox<>(new Integer[] {1,2,3,4}); //for testing
            panel.add(reviewSelection);
            selectReview = new JButton("Select");
            selectReview.addActionListener(actionListener);
            panel.add(selectReview);
            content.add(panel);

            panel = new JPanel();
            String reviewsStr = "String of reviews placeholder";
            reviewsTextArea = new JTextArea(reviewsStr);
            panel.add(reviewsTextArea, BorderLayout.CENTER);
            content.add(panel);

        }


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SelectedStoreView());
    }
}
