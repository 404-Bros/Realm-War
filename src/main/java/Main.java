package main.java;

import javax.swing.SwingUtilities;

import main.java.controllers.GameController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            controller.startNewGame();
        });
//        SwingUtilities.invokeLater(() -> {
//             new GameController();
//        });
    }
}