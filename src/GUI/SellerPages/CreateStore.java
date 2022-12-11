package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Client.BookApp;
import Client.ClientQuery;
import Exceptions.IdenticalStoreException;
import GUI.*;
import Objects.Seller;
import Query.Query;

public class CreateStore implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JTextField storeName;
    JButton createStore;
    JButton back;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CreateStore());
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createStore) {
                //TODO: Create Store and add it to the database
                try {
                    ((Seller) BookApp.currentUser).createNewStore(storeName.getText());
                    JOptionPane.showMessageDialog(null, "Store Successfully Created");
                } catch (IdenticalStoreException error) {
                    JOptionPane.showMessageDialog(null, "Whoops: A name with this store already exists",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                SwingUtilities.invokeLater(new SellerMenu());
                frame.dispose();
            } else if (e.getSource() == back) {
                SwingUtilities.invokeLater(new SellerMenu());
                frame.dispose();
            }
    }
    };

    public void run() {
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title = new JLabel("Create Store");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        storeName = new JTextField("Enter store name");
        storeName.setAlignmentX(Component.CENTER_ALIGNMENT);
        storeName.addActionListener(actionListener);
        optionPanel.add(storeName);

        createStore = new JButton("Create Store");
        createStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        createStore.addActionListener(actionListener);
        optionPanel.add(createStore);
        
        back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(actionListener);
        optionPanel.add(back);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
