package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditAccount extends JFrame implements Runnable {
    JPanel panel;
    JPanel panel2;
    JPanel optionPanel;
    JFrame frame;
    Container content;
    JLabel user;
    JLabel email;
    JLabel pass;
    JButton resetUser;
    JButton resetEmail;
    JButton resetPass;
    JButton signOut;
    JLabel title;
    JTextArea info;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new EditAccount());
    }

    ActionListener actionListener = new ActionListener() {
        //TODO: implement error messages for invalid inputs
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == resetUser) {
                JOptionPane.showInputDialog(null, "New Username: (Cancel by entering your current name)");
            } else if (e.getSource() == resetEmail) {
                JOptionPane.showInputDialog(null, "New Email: (Cancel by entering your current email)");
            } else if (e.getSource() == resetPass) {
                JOptionPane.showInputDialog(null, "Confirm by entering your password...");
            } else if (e.getSource() == signOut) {
                JOptionPane.showInputDialog(null, "Confirm by entering your password...");
            }
        }
    };

    public void run() {
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame();
        content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title = new JLabel("Edit Account");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);

        //TODO: change text of label to match user's info
        panel2 = new JPanel();
        info = new JTextArea("Username: \nEmail: \nPassword: ");
        info.setEditable(false);
        info.setOpaque(false);
        panel2.add(info);
        content.add(panel2, BorderLayout.CENTER);

        resetUser = new JButton("Reset Username");
        resetUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetUser.addActionListener(actionListener);
        optionPanel.add(resetUser);

        resetEmail = new JButton("Reset Email");
        resetEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetEmail.addActionListener(actionListener);
        optionPanel.add(resetEmail);

        resetPass = new JButton("Reset Password");
        resetPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPass.addActionListener(actionListener);
        optionPanel.add(resetPass);

        signOut = new JButton("Sign Out");
        signOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        signOut.addActionListener(actionListener);
        optionPanel.add(signOut);

        content.add(optionPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
