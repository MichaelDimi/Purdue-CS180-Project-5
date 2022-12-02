import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements Runnable {

    ActionListener actionListener = new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Login Successful");
        }
    };

    @Override
    public void run() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        

        content.setLayout(new BorderLayout());

        JLabel uLabel = new JLabel("Username");
        panel.add(uLabel);
        JTextField uText = new JTextField(15);
        panel.add(uText);
        JLabel pLabel = new JLabel("Password");
        panel.add(pLabel);
        JTextField pText = new JTextField(15);
        panel.add(pText);
        JButton loginButton = new JButton("Log in");
        panel.add(loginButton);

        content.add(panel, BorderLayout.NORTH);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Login());
    }
}