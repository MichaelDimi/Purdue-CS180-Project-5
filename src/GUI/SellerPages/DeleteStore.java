package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import GUI.*;

public class DeleteStore implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JComboBox<String> jComboBox;
    JLabel title;
    JButton deleteStore;
    JButton back;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DeleteStore());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteStore) {
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
        
        title = new JLabel("Delete Store");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        //TODO: array consisting of all stores
        String[] options = {};
        jComboBox = new JComboBox<>(options);
        jComboBox.setBounds(50, 150, 300, 20);
        optionPanel.add(jComboBox);
        
        deleteStore = new JButton("Delete Store");
        deleteStore.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStore.addActionListener(actionListener);
        deleteStore.setBounds(20, 20, 0, 0);
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
