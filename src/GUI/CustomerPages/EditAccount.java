package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAccount extends JFrame implements Runnable {
    JPanel panel;
    JFrame frame;
    Container content;
    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
        }
    };
    public void run() {

        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
