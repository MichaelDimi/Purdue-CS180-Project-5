package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Customer extends JFrame implements Runnable {
   
    JPanel panel;
    JFrame frame;
 
    // ActionListener actionListener = new ActionListener() {
    //     @Override
        
    //     public void actionPerformed(ActionEvent e) {

            

    //     }
    // };

    @Override
    public void run() {
        panel = new JPanel();
        frame = new JFrame();
        Container content = frame.getContentPane();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel("Welcome! What would you like to do?");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcome);

        String[] c = {"1. Purchase a Book," , "2. Search for a Book\n", "3. View List of Stores / Store's Inventory or Reviews", "4. Leave a Review", "5. View / Export Purchase History", "6. Your Shopping Cart", "7. Edit Account", "8. SIGN OUT"};
        JComboBox<String> cb = new JComboBox<String>(c);
        panel.add(cb);

        content.add(panel, BorderLayout.CENTER);


        

        

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Customer());
    }


}