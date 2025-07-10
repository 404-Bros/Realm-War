package views;

import javax.swing.*;
import java.awt.*;
import models.GameState;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private ActionPanel actionPanel;
    private GameState gameState;
    private MenuPanel menuPanel;
    private GetPlayerNamePanel getPlayerNamePanel;
    private MainInfoPanel mainInfoPanel;

    public GameFrame(GameState gameState) {
        setIconImage(new ImageIcon(getClass().getResource("../resources/gameIcon.jpg")).getImage());
        this.gameState = gameState;
        setTitle("Realm War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuPanel = new MenuPanel();
        getPlayerNamePanel = new GetPlayerNamePanel();
        gamePanel = new GamePanel(gameState);


        mainInfoPanel = new MainInfoPanel(gameState);
        actionPanel = new ActionPanel();

        setLocationRelativeTo(null);
        setResizable(false);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
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

    public MainInfoPanel getMainInfoPanel() {
        return mainInfoPanel;
    }

    public void updateGameState() {
        gamePanel.repaint();
        mainInfoPanel.getInfoPanel().updateInfo();
    }
}