

import javax.swing.SwingUtilities;

import controllers.GameController;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
//        SwingUtilities.invokeLater(() -> {
//            GameController controller = new GameController();
//            controller.startNewGame();
//        });
=======
>>>>>>> 909b2af050df690293ea427bbb73d6f978540a51
        SwingUtilities.invokeLater(() -> {
             new GameController();
        });
    }
}