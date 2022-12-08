package GUI;

import Objects.Buyer;
import Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.UnknownServiceException;

public class SignUp extends JFrame implements Runnable {
    JComboBox buyerOrSellerBox;
    String buyerOrSeller = "Buyer";
    JLabel uLabel;
    JTextField uText;
    JLabel eLabel;
    JTextField eText;
    JLabel pLabel;
    JPasswordField pField;
    JButton signUpButton;
    JCheckBox showPassword;
    JPanel panel;
    JFrame frame;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == signUpButton) {
                if (buyerOrSeller.equals("Buyer")) {
                    //create buyer
                } else {
                    // create seller
                }

            } else if (e.getSource() == showPassword) {
                if (showPassword.isSelected()) {
                    pField.setEchoChar((char) 0);
                } else {
                    pField.setEchoChar('*');
                }
            }

        }
    };

    @Override
    public void run() {
        panel = new JPanel();
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        frame.setResizable(false);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel("Welcome! Create your account");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcome);
        content.add(panel, BorderLayout.CENTER);

        // username
        panel = new JPanel();
        uLabel = new JLabel("Username");
        panel.add(uLabel);
        uText = new JTextField(15);
        uText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(uText);
        content.add(panel, BorderLayout.CENTER);

        // email
        panel = new JPanel();
        eLabel = new JLabel("    Email"); // really stupid way to line up text fields
        panel.add(eLabel);
        eText = new JTextField(15);
        eText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(eText);
        content.add(panel, BorderLayout.CENTER);

        // password
        panel = new JPanel();
        pLabel = new JLabel("Password");
        panel.add(pLabel);
        pField = new JPasswordField(15);
        pField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pField);
        content.add(panel, BorderLayout.CENTER);

        // buyer seller drop down select
        panel = new JPanel();
        JLabel userTypeLabel = new JLabel("Role");
        panel.add(userTypeLabel);

        buyerOrSellerBox = new JComboBox<>();
        buyerOrSellerBox.addItem(buyerOrSeller); // DEFAULT VALUE IS BUYER
        buyerOrSellerBox.addItem("Seller"); // make sure buyerOrSeller is updated

        buyerOrSellerBox.addItemListener(listener -> {
            JComboBox getSelection = (JComboBox) listener.getSource();
            buyerOrSeller = (String) getSelection.getSelectedItem();

        });
        panel.add(buyerOrSellerBox);
        content.add(panel, BorderLayout.CENTER);

        panel = new JPanel();
        showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(actionListener);
        panel.add(showPassword);
        content.add(panel);

        signUpButton = new JButton("Sign Up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(actionListener);
        panel.add(signUpButton);

        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new SignUp());
//    }


}