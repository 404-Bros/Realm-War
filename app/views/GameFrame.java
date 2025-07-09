package views;

import javax.swing.*;
import java.awt.*;
import models.GameState;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private GameState gameState;
    private MenuPanel menuPanel;
    private GetPlayerNamePanel getPlayerNamePanel;

    public GameFrame(GameState gameState) {
        setIconImage(new ImageIcon(getClass().getResource("../resources/gameIcon.jpg")).getImage());
        this.gameState = gameState;
        setTitle("Realm War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuPanel = new MenuPanel();
        getPlayerNamePanel = new GetPlayerNamePanel();
        gamePanel = new GamePanel(gameState);


        infoPanel = new InfoPanel(gameState);
        actionPanel = new ActionPanel();

//        add(gamePanel, BorderLayout.CENTER);
//        add(infoPanel, BorderLayout.EAST);
//        add(actionPanel, BorderLayout.SOUTH);
//        add(menuPanel);
//        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public GetPlayerNamePanel getGetPlayerNamePanel() {
        return getPlayerNamePanel;
    }

    public void updateGameState() {
        gamePanel.repaint();
        infoPanel.updateInfo();
    }
}