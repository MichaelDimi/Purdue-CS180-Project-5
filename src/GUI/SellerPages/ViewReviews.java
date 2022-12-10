package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewReviews implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JButton selectReview;
    JComboBox<String> jComboBox;
    //TODO: Import reviews
    String[] reviews = {"a", "b", "c"}; //Temporary Array
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewReviews());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectReview) {
                if (reviews != null) {
                    //TODO: Add review details
                JOptionPane.showMessageDialog(null, "Insert review details: Title, Description, Owner");
                }
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
        
        title = new JLabel("Selected store: " + "storename");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        jComboBox = new JComboBox<>(reviews);
        jComboBox.setBounds(50, 150, 300, 20);
        optionPanel.add(jComboBox);

        selectReview = new JButton("Select Review");
        selectReview.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectReview.addActionListener(actionListener);
        optionPanel.add(selectReview);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
