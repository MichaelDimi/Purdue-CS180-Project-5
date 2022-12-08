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
            //TODO: display message if cart is empty
           /* HashMap<Book, Integer> cart = buyer.getCart();
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Your cart is empty");
            }*/
        
            if (e.getSource() == remove) {
                JOptionPane.showMessageDialog(null, "Book Removed");
            } else if (e.getSource() == clear) {
                JOptionPane.showMessageDialog(null, "Cart Cleared");
            } else if (e.getSource() == checkout) {
                JOptionPane.showMessageDialog(null, "Book Checked Out");
            }
        }
    };
    public void run() {
        panel = new JPanel();
        optionPanel = new JPanel(new GridLayout(4, 4));
        frame = new JFrame("Your Cart");
        Container content = frame.getContentPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
