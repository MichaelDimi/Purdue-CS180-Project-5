package GUI;

import GUI.CustomerPages.PurchaseBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Customer extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    JLabel welcome;
    JComboBox<String> jComboBox;
    Container content;
    JButton jButton;
    JLabel jLabel;


    @Override
    public void run() {

        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();

        welcome = new JLabel("Welcome! What would you like to do?");
        welcome.setBounds(80, 80, 340, 20);
        String[] options = {"1. Purchase a Book" , "2. Search for a Book", "3. View List of Stores / Store's Inventory or Reviews", "4. Leave a Review", "5. View / Export Purchase History", "6. Your Shopping Cart", "7. Edit Account", "8. SIGN OUT"};

        jComboBox = new JComboBox<>(options);
        jComboBox.setBounds(50, 150, 300, 20);

        jButton = new JButton("Done");
        jButton.setBounds(140, 210, 90, 20);

        jLabel = new JLabel();
        jLabel.setBounds(90, 220, 400, 100);


        frame.add(welcome);
        frame.add(jComboBox);
        frame.add(jButton);

        frame.add(jLabel);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //TODO: put name of screen inside invokeLater to move to selected dropdown screen

                if (jComboBox.getSelectedIndex() == 0) {
                    SwingUtilities.invokeLater(new PurchaseBook());
                } /*else if (jComboBox.getSelectedIndex() == 1) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 2) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 3) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 4) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 5) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 6) {
                    SwingUtilities.invokeLater(new ___);
                } else if (jComboBox.getSelectedIndex() == 7) {
                    SwingUtilities.invokeLater(new ___);
                } else {
                }*/

            }
        });


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Customer());
    }
}