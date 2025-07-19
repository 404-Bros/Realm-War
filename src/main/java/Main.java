

import javax.swing.SwingUtilities;

import controllers.GameController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             new GameController();
        });
    }
}