package views;

import models.GameState;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainInfoPanel extends JPanel {
    private InfoPanel infoPanel;
    private JButton pauseButton;
    private GameState gameState;
    private BlockPanel blockPanel;
    public MainInfoPanel(GameState gameState) {
        setLayout(null);
        setBackground(new Color(0xEDEAE6));

        blockPanel = new BlockPanel();
        this.gameState = gameState;
        infoPanel = new InfoPanel(gameState);
        infoPanel.setBounds(0, 40,200,150);
        add(infoPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(200,600));

        pauseButton = new JButton();
        pauseButton.setBackground(new Color(0xCA8D37));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setPreferredSize(new Dimension(30, 30));
        pauseButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pauseButton.setFocusable(false);
        pauseButton.setIcon(new ImageIcon(getClass().getResource("/pause.png")));
        pauseButton.setBounds(168, 2, 30, 30);
        add(pauseButton);




    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
    public void addpauseButtonAL(ActionListener actionListener) {
        pauseButton.addActionListener(actionListener);
    }

    public BlockPanel getBlockPanel() {
        return blockPanel;
    }

    public void setBlockPanel(BlockPanel blockPanel) {
        this.blockPanel = blockPanel;
        this.add(blockPanel);
    }
    public void removeBlockPanel(BlockPanel blockPanel) {
        this.remove(blockPanel);
    }
}
