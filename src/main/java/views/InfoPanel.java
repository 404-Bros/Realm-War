package views;

import controllers.GameController;
import models.GameState;
import models.Kingdom;
import models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {
    private JLabel playerLabel;
    private JLabel goldLabel;
    private JLabel foodLabel;
    private JLabel unitSpaceLabel;
    private JLabel turnLabel;
    private JLabel timeLabel;
    private String player1Name;
    private String player2Name;
    private GameState gameState;

    public InfoPanel(GameState gameState) {
        setBackground(new Color(0xEDEAE6));

        this.gameState = gameState;

        //setPreferredSize(new Dimension(200, 568));
        setPreferredSize(new Dimension(200, 150));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        playerLabel = new JLabel("Player: ");
        playerLabel.setIcon(new ImageIcon(getClass().getResource("/person.png")));
        /// //////////
        goldLabel = new JLabel("Gold: ");
        goldLabel.setIcon(new ImageIcon(getClass().getResource("/Gold.png")));

        foodLabel = new JLabel("Food: ");
        foodLabel.setIcon(new ImageIcon(getClass().getResource("/Chicken.png")));

        unitSpaceLabel = new JLabel("Unit Space: ");
        unitSpaceLabel.setIcon(new ImageIcon(getClass().getResource("/tent.png")));

        turnLabel = new JLabel("Turn: ");
        turnLabel.setIcon(new ImageIcon(getClass().getResource("/Turn.png")));

        timeLabel = new JLabel("Time Left: ");
        timeLabel.setIcon(new ImageIcon(getClass().getResource("/Time.png")));

        add(timeLabel);
        add(playerLabel);
        add(goldLabel);
        add(foodLabel);
        add(unitSpaceLabel);
        add(turnLabel);
        
        add(Box.createVerticalGlue());
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }
    
    public void updateInfo() {
        Kingdom currentKingdom = gameState.getCurrentKingdom();
        
        playerLabel.setText("Player: " + gameState.getCurrentPlayerTurn());
        if (gameState.getCurrentPlayerTurn()==0){
            playerLabel.setText("Player: " + player1Name);
        }
        else {
            playerLabel.setText("Player: " + player2Name);
        }

        goldLabel.setText("Gold: " + currentKingdom.getGold());
        foodLabel.setText("Food: " + currentKingdom.getFood());
        unitSpaceLabel.setText("Unit Space: " + currentKingdom.getUsedUnitSpace() + 
                              "/" + currentKingdom.getTotalUnitSpace());
        turnLabel.setText("Turn: " + gameState.getTurnNumber());
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public JLabel getGoldLabel() {
        return goldLabel;
    }

    public JLabel getFoodLabel() {
        return foodLabel;
    }

    public JLabel getUnitSpaceLabel() {
        return unitSpaceLabel;
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getPlayerLabel() {
        return playerLabel;
    }
}