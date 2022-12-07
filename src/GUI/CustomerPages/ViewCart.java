package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ViewCart implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JButton remove;
    JButton clear;
    JButton checkout;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ViewCart());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        //TODO: invoke other GUI's
            if (e.getSource() == remove) {

            } else if (e.getSource() == clear) {

            } else if (e.getSource() == checkout) {
                
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
        
        JLabel title = new JLabel("Your Cart");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        remove = new JButton("Remove Items");
        remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        remove.addActionListener(actionListener);
        optionPanel.add(remove);

        clear = new JButton("Clear Cart");
        clear.setAlignmentX(Component.CENTER_ALIGNMENT);
        clear.addActionListener(actionListener);
        optionPanel.add(clear);

        checkout = new JButton("Checkout");
        checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkout.addActionListener(actionListener);
        optionPanel.add(checkout);

        

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
