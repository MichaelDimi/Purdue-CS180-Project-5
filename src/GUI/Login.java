package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Login extends JFrame implements Runnable {
    JLabel uLabel;
    JTextField uText;
    JLabel pLabel;
    JPasswordField pField;
    JButton loginButton;
    JButton signUpButton;
    JCheckBox showPassword;
    JPanel panel;
    JFrame frame;
    JLabel signup;
    ActionListener actionListener = new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == loginButton) {
                /*if user present (similar to loginmenu) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                  } else {
                    JOptionPane.showMessageDialog(null, "Login Unsuccessful");
                    }
                 */
                JOptionPane.showMessageDialog(null, "Login Successful");
                frame.dispose();

            } else if (e.getSource() == signUpButton) {
                SwingUtilities.invokeLater(new SignUp());
                frame.dispose();


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
        JLabel welcome = new JLabel("Welcome!");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcome);
        content.add(panel, BorderLayout.CENTER);

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
        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(actionListener);
        panel.add(loginButton);
        showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(actionListener);
        panel.add(showPassword);
        content.add(panel);

        panel = new JPanel();
        signup = new JLabel("New user? Sign up!");
        panel.add(signup);
        content.add(panel);

        signUpButton = new JButton("Sign Up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(actionListener);
        panel.add(signUpButton);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Login());
    }


}