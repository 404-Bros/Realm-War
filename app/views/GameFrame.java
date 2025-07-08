package views;

import javax.swing.*;
import java.awt.*;
import models.GameState;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;

    public GameFrame() {
        setTitle("Realm War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        gamePanel = new GamePanel();
        infoPanel = new InfoPanel();
        actionPanel = new ActionPanel();
        infoPanel.updateInfo(gamePanel.getGameState());

        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        add(actionPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void updateGameState(GameState gameState) {
        gamePanel.repaint();
        infoPanel.updateInfo(gameState);
    }
}