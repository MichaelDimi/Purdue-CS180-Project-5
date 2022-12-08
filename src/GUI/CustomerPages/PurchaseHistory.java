package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PurchaseHistory implements Runnable {
    JFrame frame;
    JPanel panel;
    JTextField yn;
    JLabel export;
    JButton yes;
    JButton no;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PurchaseHistory());
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //TODO: implement exporting when yes is pressed
            if (e.getSource() == yes) {
                
            } if (e.getSource() == no) {
            }
        }
    };

    public void run() {
        panel = new JPanel();
        frame = new JFrame("View and Export Purchase History");
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        export = new JLabel("Would you like to export your purchase history? (Y/N)");
        panel.add(export);
        yes = new JButton("Yes");
        no = new JButton("No");
        panel.add(yes);
        panel.add(no);
        content.add(panel, BorderLayout.CENTER);


        

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
