package views;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import database.DatabaseHandler;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoadGamePanel extends JPanel {
    private JComboBox<String> savesComboBox;
    private JButton loadButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel comboLabel;

    public LoadGamePanel() {
        setLayout(null);
        setBackground(new Color(0xEDEAE6));
        setPreferredSize(new Dimension(400, 355));

        comboLabel = new JLabel("Your Saved Games:");
        comboLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        comboLabel.setBounds(50, 30, 300, 30);
        add(comboLabel);

        savesComboBox = new JComboBox<>();
        savesComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        savesComboBox.setBounds(50, 70, 300, 40);
        add(savesComboBox);

        backButton = new JButton("Back");
        backButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        backButton.setFocusable(false);
        backButton.setBackground(new Color(0xCA8D37));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setBounds(30, 250, 100, 50);
        add(backButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        deleteButton.setFocusable(false);
        deleteButton.setBackground(new Color(0xCA8D37));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        deleteButton.setBounds(140, 250, 120, 50);
        add(deleteButton);

        loadButton = new JButton("Load Game");
        loadButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        loadButton.setFocusable(false);
        loadButton.setBackground(new Color(0xCA8D37));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        loadButton.setBounds(270, 250, 100, 50);
        add(loadButton);
    }

    public void setGameSavesComboBox(){
        savesComboBox.removeAllItems();
        ArrayList<String> saves = DatabaseHandler.getGameSaves();
        for(String save : saves){
            savesComboBox.addItem(save);
        }
    }

    public JLabel getComboLabel(){
        return comboLabel;
    }

    public JComboBox<String> getSavesComboBox() {
        return savesComboBox;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void addLoadButtonActionListener(ActionListener l) {
        loadButton.addActionListener(l);
    }

    public void addDeleteButtonActionListener(ActionListener l) {
        deleteButton.addActionListener(l);
    }

    public void addBackButtonActionListener(ActionListener l) {
        backButton.addActionListener(l);
    }
}

