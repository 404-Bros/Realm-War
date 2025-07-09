

import javax.swing.SwingUtilities;

import controllers.GameController;
import models.GameState;
import views.GameFrame;

public class Main {
    public static void main(String[] args) {
        GameState gameState=new GameState(12,16,2);
        GameFrame gameFrame = new GameFrame(gameState);
        SwingUtilities.invokeLater(() -> {
            new GameController(gameState, gameFrame);
        });
    }
}