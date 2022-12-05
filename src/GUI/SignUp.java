package GUI;

import Objects.Buyer;
import Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.UnknownServiceException;

public class SignUp extends JFrame implements Runnable {
    JComboBox buyerOrSellerBox;
    String buyerOrSeller;
    JLabel uLabel;
    JTextField uText;
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

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        frame.setResizable(false);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel("Welcome! Create your account");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcome);
        content.add(panel, BorderLayout.CENTER);
        buyerOrSellerBox = new JComboBox<>();
        buyerOrSellerBox.addItem("Buyer");
        buyerOrSellerBox.addItem("Seller");

        buyerOrSellerBox.addItemListener(listener -> {
            JComboBox getSelection = (JComboBox) listener.getSource();
            buyerOrSeller = (String) getSelection.getSelectedItem();

        });
        panel = new JPanel();
        uLabel = new JLabel("Username");
        panel.add(uLabel);
        uText = new JTextField(15);
        uText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(uText);
        content.add(panel, BorderLayout.CENTER);
        panel = new JPanel();
        pLabel = new JLabel("Password");
        panel.add(pLabel);

        pField = new JPasswordField(15);
        pField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pField);
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
        frame.setSize(400, 200);
        frame.setVisible(true);

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new SignUp());
//    }


}