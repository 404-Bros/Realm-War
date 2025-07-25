package views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel{
    private JButton newGameButton;
    private JButton loadGameButton;
    private JPanel centerPanel;
    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    private JCheckBox darkModeCheckBox;
    public MenuPanel(){
        setLayout(new BorderLayout());
        setBackground(new Color(0xEDEAE6));

        setPreferredSize(new Dimension(400,400));

        welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(0xEDEAE6));
        welcomePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        welcomeLabel = new JLabel("Welcome to Realm War Game");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Thoma", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(202, 141, 55));
        welcomeLabel.setBackground(new Color(255, 255, 255));

        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

        add(welcomePanel,BorderLayout.NORTH);




        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0xEDEAE6));
        centerPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        newGameButton = new JButton("New Game");
        newGameButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        newGameButton.setFocusable(false);
        newGameButton.setBackground(new Color(0xCA8D37));
        newGameButton.setForeground(Color.WHITE);
        //newGameButton.setOpaque(true);
        newGameButton.setPreferredSize(new Dimension(150, 50));
        newGameButton.setFont(new Font("Thoma", Font.PLAIN, 15));
        centerPanel.add(newGameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loadGameButton = new JButton("Load Game");
        loadGameButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        loadGameButton.setFocusable(false);
        loadGameButton.setBackground(new Color(0xCA8D37));
        loadGameButton.setForeground(Color.WHITE);
        //loadGameButton.setOpaque(true);
        loadGameButton.setPreferredSize(new Dimension(150, 50));
        loadGameButton.setFont(new Font("Thoma", Font.PLAIN, 15));
        centerPanel.add(loadGameButton, gbc);

        /// /////////////////////////////

        darkModeCheckBox = new JCheckBox("Dark Mode");
        darkModeCheckBox.setBackground(new Color(0xCA8D37));
        darkModeCheckBox.setFocusable(false);
        darkModeCheckBox.setForeground(Color.WHITE);
        gbc.gridx=1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(darkModeCheckBox, gbc);

        add(centerPanel, BorderLayout.CENTER);

    }
    public void addNewGameButtonAL(ActionListener al){
        newGameButton.addActionListener(al);
    }
    public void addLoadGameButtonAL(ActionListener al){
        loadGameButton.addActionListener(al);
    }
    public void addDarkModeCheckBoxAL(ActionListener al){
        darkModeCheckBox.addActionListener(al);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadGameButton() {
        return loadGameButton;
    }

    public JCheckBox getDarkModeCheckBox() {
        return darkModeCheckBox;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getWelcomePanel() {
        return welcomePanel;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

}