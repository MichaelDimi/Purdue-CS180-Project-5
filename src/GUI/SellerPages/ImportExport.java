package GUI.SellerPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImportExport implements Runnable {
    JFrame frame;
    JPanel panel;
    JPanel optionPanel;
    JLabel title;
    JButton export;
    JButton Import;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ImportExport());
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == export) {
                //TODO: Export seller data as a csv file
                try {
                String file = JOptionPane.showInputDialog(null, "File to be written to:", "Import/Export",
                JOptionPane.QUESTION_MESSAGE);
                if (!(file.isEmpty()) && file != null) {
                JOptionPane.showMessageDialog(null, "Data Succesfully Exported", "Import/Export",
                JOptionPane.INFORMATION_MESSAGE, null);
                } else {
                    throw new Exception();
                }
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null, "Failed to Export Data", "Import/Export",
                    JOptionPane.ERROR_MESSAGE, null);
                }
            } else if (e.getSource() == Import) {
                //TODO: Import seller data using a csv file
                try {
                    String file = JOptionPane.showInputDialog(null, "File to be read:", "Import/Export",
                    JOptionPane.QUESTION_MESSAGE);
                    if (!(file.isEmpty()) && file != null) {
                        JOptionPane.showMessageDialog(null, "Data Succesfully Imported", "Import/Export",
                        JOptionPane.INFORMATION_MESSAGE, null);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null, "Failed to Import Data", "Import/Export",
                    JOptionPane.ERROR_MESSAGE, null);
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
        
        title = new JLabel("Import/Export");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        panel.add(title);
        content.add(panel, BorderLayout.NORTH);
        content.add(optionPanel, BorderLayout.CENTER);

        export = new JButton("Export");
        export.setAlignmentX(Component.CENTER_ALIGNMENT);
        export.addActionListener(actionListener);
        optionPanel.add(export);

        Import = new JButton("Import");
        Import.setAlignmentX(Component.CENTER_ALIGNMENT);
        Import.addActionListener(actionListener);
        optionPanel.add(Import);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
