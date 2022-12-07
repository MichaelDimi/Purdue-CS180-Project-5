package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseHistory extends JFrame implements Runnable {
    JPanel panel;
    JPanel panel2;
    JPanel panel3;
    JFrame frame;
    Container content;
    JLabel search;
    JLabel enter;
    JTextField searchText;
    JTextArea note;
    JLabel note2;
    JPanel optionPanel;

    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
        }
    };

    public void run() {

        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content.setLayout(new BorderLayout(content,BoxLayout.Y_AXIS));
        search = new JLabel("Your Purchase History");
        panel.add(search);

        panel2 = new JPanel();
        enter = new JLabel("Would you like to export your purchase history? (Y/N)");
        panel2.add(enter);

        searchText = new JTextField(5);
        panel2.add(searchText);

        content.add(panel, BorderLayout.NORTH);
        content.add(panel2, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseHistory());
    }
}
