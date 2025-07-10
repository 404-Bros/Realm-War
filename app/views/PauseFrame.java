package views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PauseFrame extends JDialog {

    private JButton resumeButton;
    private JButton saveAndBackButton;

    public PauseFrame() {
        setModal(true);
        setTitle("Pause");
        getContentPane().setBackground(new Color(225, 212, 193));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(400, 400);




        resumeButton = new JButton("Resume Game");
        resumeButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        resumeButton.setFocusable(false);
        resumeButton.setBackground(new Color(0xCA8D37));
        resumeButton.setForeground(Color.WHITE);
        resumeButton.setPreferredSize(new Dimension(130, 50));
        resumeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        
        
        saveAndBackButton = new JButton("Save and Back to Main Menu");
        saveAndBackButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        saveAndBackButton.setFocusable(false);
        saveAndBackButton.setBackground(new Color(0xCA8D37));
        saveAndBackButton.setForeground(Color.WHITE);
        saveAndBackButton.setPreferredSize(new Dimension(210, 50));
        saveAndBackButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        add(resumeButton, gbc);

        gbc.gridy = 1;
        add(saveAndBackButton, gbc);
        
    }

    public void addResumeButtonAL(ActionListener actionListener) {
        resumeButton.addActionListener(actionListener);
    }
    public void addSaveAndBackButtonAL(ActionListener actionListener) {
        saveAndBackButton.addActionListener(actionListener);
    }
}

