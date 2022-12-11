package GUI.CustomerPages;

import Client.BookApp;
import Client.ClientQuery;
import Objects.Book;
import Objects.User;
import Query.Query;

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
    JButton deleteAccount;
    JLabel title;
    JTextArea info;

    // takes in homepage so that delete account can dispose of it and sign out
    JFrame userHomepage;

    // TODO: Add frames to an array that does frame.dispose on contents?
    // takes in homepage so that delete account can dispose of it and sign out
    public EditAccount(JFrame userHomepage) {
        this.userHomepage = userHomepage;
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new EditAccount());
    }

    ActionListener actionListener = new ActionListener() {
        //TODO: implement error messages for invalid inputs
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == resetUser) {
                String newUsername = JOptionPane.showInputDialog(null, "New Username:");

                // checks if user presses cancel
                if (newUsername == null) {
                    return;
                }

                // checks if username is not empty
                if (newUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled: Please enter a new username",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // checks if username is valid or is not already taken
                Query validateNameQuery = new ClientQuery().computeQuery(new String[]{newUsername}, "users", "validate name");
                boolean isValidUsername = !(boolean) validateNameQuery.getObject();
                if (!isValidUsername) {
                    // updates server with new username
                    Query setNameQuery = new ClientQuery().updateQuery(BookApp.currentUser, "users", "name", newUsername);
                    if (setNameQuery.getObject().equals(false)) {
                        System.out.println("Whoops: Couldn't set your new username");
                        return;
                    }

                    // updates current user username
                    BookApp.currentUser.setName(newUsername);

                    // "refreshes" GUI
                    frame.dispose();
                    run();
                } else {
                    JOptionPane.showMessageDialog(null, "Whoops: Couldn't set your new email\nEmail is either taken, or is invalid",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == resetEmail) {
                String newEmail = JOptionPane.showInputDialog(null, "New Email:");

                // checks if user presses cancel
                if (newEmail == null) {
                    return;
                }

                // checks if username is not empty
                if (newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled: Please enter a new email",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // checks if username is valid or is not already taken
                Query validateEmailQuery = new ClientQuery().computeQuery(new String[]{newEmail}, "users", "validate " +
                        "email");
                Boolean isValidEmail = !(boolean) validateEmailQuery.getObject();
                if (!isValidEmail) {
                    // updates email on server
                    Query setEmailQuery = new ClientQuery().updateQuery(BookApp.currentUser, "users", "email", newEmail);
                    if (setEmailQuery.getObject().equals(false)) {
                        System.out.println("Whoops: Couldn't set your new email");
                        return;
                    }

                    // updates current email
                    BookApp.currentUser.setEmail(newEmail);

                    // "refreshes" GUI
                    frame.dispose();
                    run();
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't set your new email\nEmail is either taken, or is not in a\nvalid format (missing @ or email domain)",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == resetPass) {
                String currentPassword = JOptionPane.showInputDialog(null, "Confirm by entering your current password:");

                // checks if user presses cancel
                if (currentPassword == null) {
                    return;
                }

                // hashes password
                String hashedPassword = User.hashPassword(currentPassword);
                if (hashedPassword == null) {
                    return;
                }

                // checks that password is correct
                if (hashedPassword.equals(BookApp.currentUser.getPassword())) {
                    String newPassword = JOptionPane.showInputDialog(null, "Enter a new password:");

                    // checks if username is not empty
                    if (!newPassword.isEmpty()) {
                        hashedPassword = User.hashPassword(newPassword);
                        if (hashedPassword == null) {
                            return;
                        }
                        // updates password on server
                        Query setPassQuery = new ClientQuery().updateQuery(BookApp.currentUser, "users", "password",
                                new String[]{hashedPassword, newPassword});
                        if (setPassQuery.getObject().equals(false)) {
                            JOptionPane.showMessageDialog(null, "Whoops: Couldn't set your new password",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // updates current user password
                        BookApp.currentUser.setPassword(hashedPassword, newPassword);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // "refreshes" GUI
                    frame.dispose();
                    run();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == deleteAccount) {
                String currentPassword = JOptionPane.showInputDialog(null, "Confirm by entering your current password:");

                // checks if user presses cancel
                if (currentPassword == null) {
                    return;
                }

                // hashes password
                String hashedPassword = User.hashPassword(currentPassword);
                if (hashedPassword == null) {
                    return;
                }

                // checks that password is correct
                if (hashedPassword.equals(BookApp.currentUser.getPassword())) {
                    // Remove from marketplace and sign out
                    Query deleteUser = new ClientQuery().deleteQuery(BookApp.currentUser, "users");
                    if (deleteUser.getObject().equals(false)) {
                        JOptionPane.showMessageDialog(null, "Whoops: Couldn't delete your account. Please try again",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        frame.dispose();
                        userHomepage.dispose();
                        JOptionPane.showMessageDialog(null, "You account has been deleted and you have been signed out");
                        BookApp.signOut();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    public void run() {
        // Fetch the user again
        Query updateUserQuery = new ClientQuery().getQuery(BookApp.currentUser, "users", "currentUser");
        if (updateUserQuery.getObject().equals(false)) {
            return ;
        }
        User updatedCurrentUser = (User) updateUserQuery.getObject();

        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame();
        content = frame.getContentPane();

        frame.setLocationRelativeTo(null);

        title = new JLabel("Edit Account");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);

        //TODO: change text of label to match user's info
        panel2 = new JPanel();
        info = new JTextArea(String.format("Username: %s\nEmail: %s\nPassword: %s", updatedCurrentUser.getName(), updatedCurrentUser.getEmail(), updatedCurrentUser.getDisplayPassword()));
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

        deleteAccount = new JButton("Delete Account");
        deleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteAccount.addActionListener(actionListener);
        optionPanel.add(deleteAccount);

        content.add(optionPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
