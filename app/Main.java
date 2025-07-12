

import javax.swing.SwingUtilities;

import controllers.GameController;
import models.GameState;
import views.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameController();
        });
    }
}