package main.java.views;

import main.java.models.structures.Market;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StructureSelectionDialog extends JDialog {
    private JLabel chooseLabel;
    private JComboBox<String> structureComboBox;
    private JLabel dutyLabel;
    private JLabel costLabel;
    private JPanel buttonPanel;
    private JButton doneButton;
    private JButton cancelButton;
    public StructureSelectionDialog(JFrame parent) {
        super(parent, "Select Structure", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setPreferredSize(new Dimension(400, 250));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBackground(new Color(0xEDEAE6));
        // ساخت لیبل Choose Structure
        chooseLabel = new JLabel("Choose Structure:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 5);
        add(chooseLabel, gbc);

        // ساخت کومبو باکس
        structureComboBox = new JComboBox<>(new String[]{"None", "Market", "Farm", "Barrack", "Tower"});
        structureComboBox.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 5, 10);
        add(structureComboBox, gbc);

        // ساخت لیبل Duty
        dutyLabel = new JLabel("Duty:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        //gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(15, 10, 5, 5);
        add(dutyLabel, gbc);

        // ساخت لیبل Cost
        costLabel = new JLabel("Cost:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 10);
        add(costLabel, gbc);

        // ساخت دکمه‌ها
        doneButton = new JButton("Done");

        doneButton.setFocusable(false);
        doneButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        doneButton.setBackground(new Color(0xCA8D37));
        doneButton.setForeground(Color.WHITE);
        doneButton.setPreferredSize(new Dimension(80, 40));
        doneButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        
        
        cancelButton = new JButton("Cancel");

        cancelButton.setFocusable(false);
        cancelButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        cancelButton.setBackground(new Color(0xCA8D37));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(80, 40));
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(doneButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public JComboBox<String> getStructureComboBox() {
        return structureComboBox;
    }

    public JLabel getChooseLabel() {
        return chooseLabel;
    }

    public JLabel getDutyLabel() {
        return dutyLabel;
    }

    public JLabel getCostLabel() {
        return costLabel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
//
//    public static void main(String[] args) {
//        JFrame dummyFrame = new JFrame(); // فقط برای تست
//        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        dummyFrame.setSize(400, 200);
//        StructureSelectionDialog dialog = new StructureSelectionDialog(dummyFrame);
//        dialog.setVisible(true);
//    }
}
