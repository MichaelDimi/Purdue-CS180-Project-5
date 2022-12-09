package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import GUI.*;
public class ManageStore implements Runnable {
    String selectedStore;
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JComboBox<String> jComboBox;
    JLabel title;
    JButton changeName;
    JButton addBooks;
    JButton removeBooks;
    JButton editBooks;
    JButton deleteStore;
    JButton back;
    //TODO: Import books
    String[] books = {"a", "b", "c"}; //Temporary Array
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ManageStore(null));
    }
    public ManageStore(String selectedStore) {
        this.selectedStore = selectedStore;
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == changeName) {
                selectedStore = JOptionPane.showInputDialog(null, "Type new store name", "Manage Store",
                JOptionPane.QUESTION_MESSAGE);
                if (selectedStore != null) {
                //TODO: Update store name in database using selectedStore field
                JOptionPane.showMessageDialog(null, "Store Name Changed");
                SwingUtilities.invokeLater(new SellerMenu());
                frame.dispose();
                }
            } else if (e.getSource() == addBooks) {
                SwingUtilities.invokeLater(new AddBook(selectedStore));
                frame.dispose();   
            } else if (e.getSource() == removeBooks) {
                //TODO: Remove the specified amount of a book from a database
                String book = (String) JOptionPane.showInputDialog(null, "Select book", "Manage store",
                JOptionPane.QUESTION_MESSAGE, null, books, null);
                if (book != null) {
                    try {
                        int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Select amount to remove", "Manage Store",
                        JOptionPane.QUESTION_MESSAGE));
                        JOptionPane.showMessageDialog(null, "Book successfully removed");
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null, "Invalid Amount", "Manage store",
                                JOptionPane.INFORMATION_MESSAGE, null);
                        }
                    }
            } else if (e.getSource() == editBooks) {
                String book = (String) JOptionPane.showInputDialog(null, "Select book", "Manage store",
                JOptionPane.QUESTION_MESSAGE, null, books, null);
                if (book != null) {
                    SwingUtilities.invokeLater(new EditBook(selectedStore, book));
                    frame.dispose();
                }
            } else if (e.getSource() == deleteStore) {
                //TODO: Delete selected store and update database
                JOptionPane.showMessageDialog(null, "Store Successfully Deleted");
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
        optionPanel = new JPanel(new GridLayout(4,4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title = new JLabel("Selected store: " + selectedStore);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        /* 
        String[] options = {};
        jComboBox = new JComboBox<>(options);
        jComboBox.setBounds(50, 150, 300, 20);
        optionPanel.add(jComboBox);
        */
        
        changeName = new JButton("Change Name");
        changeName.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeName.addActionListener(actionListener);
        optionPanel.add(changeName);

        addBooks = new JButton("Add Books");
        addBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBooks.addActionListener(actionListener);
        optionPanel.add(addBooks);

        removeBooks = new JButton("Remove Books");
        removeBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBooks.addActionListener(actionListener);
        optionPanel.add(removeBooks);

        editBooks = new JButton("Edit Books");
        editBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        editBooks.addActionListener(actionListener);
        optionPanel.add(editBooks);

        deleteStore = new JButton("Delete Store");
        deleteStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStore.addActionListener(actionListener);
        // deleteStore.setBounds(20, 20, 0, 0);
        optionPanel.add(deleteStore);

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
