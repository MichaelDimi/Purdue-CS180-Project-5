package GUI.CustomerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBook extends JFrame implements Runnable {
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

    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
        }
    };

    public void run() {

        panel = new JPanel();
        frame = new JFrame();
        content = frame.getContentPane();

        content.setLayout(new BorderLayout());
        search = new JLabel("Search For A Book");
        panel.add(search);

        panel2 = new JPanel();
        enter = new JLabel("Enter a search query:");
        panel2.add(enter);

        searchText = new JTextField(15);
        searchText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.add(searchText);

        panel3 = new JPanel();
        note = new JTextArea("Note: The query will be used to search for \n name, genre, and description.");
        note.setEditable(false);
        panel3.add(note);

        content.add(panel, BorderLayout.NORTH);
        content.add(panel2, BorderLayout.CENTER);
        content.add(panel3, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SearchBook());
    }
}
