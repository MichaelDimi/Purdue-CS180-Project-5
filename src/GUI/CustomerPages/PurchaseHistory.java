package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PurchaseHistory implements Runnable {
    JFrame frame;
    JPanel panel;
    JTextField yn;
    JLabel export;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseHistory());
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        
        }
    };

    public void run() {
        panel = new JPanel();
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel title = new JLabel("View and Export Purchase History");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);

        export = new JLabel("Would you like to export your purchase history? (Y/N)");
        panel.add(export);
        content.add(panel, BorderLayout.CENTER);

        yn = new JTextField(3);
        panel.add(yn);
        content.add(panel, BorderLayout.CENTER);

        

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
