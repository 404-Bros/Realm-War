package controllers;

import models.GameState;
import models.Player;
import views.GameFrame;

import javax.swing.*;
import java.awt.*;

public class GameController {
    private GameState gameState;
    private GameFrame gameFrame;
    private static Player player1;
    private static Player player2;


    public GameController(GameState gameState, GameFrame gameFrame){
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        gameFrame.getMenuPanel().addNewGameButtonAL(e -> {
            gameFrame.remove(gameFrame.getMenuPanel());
            gameFrame.add(gameFrame.getGetPlayerNamePanel());
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });
        gameFrame.getGetPlayerNamePanel().addBackButtonActionListener(e -> {
            gameFrame.remove(gameFrame.getGetPlayerNamePanel());
            gameFrame.add(gameFrame.getMenuPanel());
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });
        gameFrame.getGetPlayerNamePanel().addStartButtonActionListener(e -> {
            String player1name;
            String player2name;


            player1name=gameFrame.getGetPlayerNamePanel().getPlayer1Name();
            player2name=gameFrame.getGetPlayerNamePanel().getPlayer2Name();
            if(player2name.isBlank() || player1name.isBlank()){
                JOptionPane.showMessageDialog(gameFrame, "Name can't be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if (player1name.equals(player2name)) {
                    JOptionPane.showMessageDialog(gameFrame, "Name can't be same!!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            gameFrame.getInfoPanel().setPlayer1Name(player1name);
            gameFrame.getInfoPanel().setPlayer2Name(player2name);
            gameFrame.getInfoPanel().updateInfo();

            player1 = new Player(player1name,gameState.getKingdoms().get(0));
            player2 = new Player(player2name,gameState.getKingdoms().get(1));

            gameFrame.remove(gameFrame.getGetPlayerNamePanel());
            gameFrame.add(gameFrame.getGamePanel(), BorderLayout.CENTER);
            gameFrame.add(gameFrame.getInfoPanel(), BorderLayout.EAST);
            gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
            gameFrame.setLocationRelativeTo(null);
        });

    }

}
