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
    private LoadGamePanel loadGamePanel;

    public GameFrame() {
        setIconImage(new ImageIcon(getClass().getResource("/gameIcon.jpg")).getImage());
        this.gameState = gameState;
        setTitle("Realm War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuPanel = new MenuPanel();
        getPlayerNamePanel = new GetPlayerNamePanel();
        actionPanel = new ActionPanel();
        loadGamePanel = new LoadGamePanel();

        setLocationRelativeTo(null);
        setResizable(false);
    }
    @Override
    public void repaint() {
        super.repaint();
        if (mainInfoPanel != null) {
            mainInfoPanel.getInfoPanel().updateInfo();
        }
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

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setMainInfoPanel(MainInfoPanel mainInfoPanel) {
        this.mainInfoPanel = mainInfoPanel;
    }

    public void updateGameState() {
        gamePanel.repaint();
        mainInfoPanel.getInfoPanel().updateInfo();
    }
    
    public LoadGamePanel getLoadGamePanel() {
        return loadGamePanel;
    }
}