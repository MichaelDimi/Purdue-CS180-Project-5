package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JFrame implements Runnable {
    JButton logInButton;
    JButton signUpButton;
    JPanel panel;
    JFrame frame;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == logInButton) {
                SwingUtilities.invokeLater(new Login());
                frame.dispose();
            } else {
                SwingUtilities.invokeLater(new SignUp());
                frame.dispose();
            }
        }
    };

    @Override
    public void run() {
        panel = new JPanel();
        frame = new JFrame();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // stacks buttons ontop of each other
        gbc.fill = GridBagConstraints.HORIZONTAL; // fills buttons so they have the same width

        JLabel welcome = new JLabel("Barnes & Novel", SwingConstants.CENTER);
        welcome.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // creates border spacing between title and buttons
        logInButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");

        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        logInButton.addActionListener(actionListener);
        signUpButton.addActionListener(actionListener);

        panel.add(welcome, gbc);
        panel.add(logInButton, gbc);
        panel.add(signUpButton, gbc);

        // Function to set the panel  of JFrame.
        frame.add(panel);
        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Start());
    }


}