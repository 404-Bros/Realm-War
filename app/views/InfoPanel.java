package views;

import models.GameState;
import models.Kingdom;

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
    private int timeLeft=30;
    
    public InfoPanel() {
        setPreferredSize(new Dimension(200, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        playerLabel = new JLabel("Player: ");
        playerLabel.setIcon(new ImageIcon(getClass().getResource("../resources/person.png")));
        /// //////////
        goldLabel = new JLabel("Gold: ");
        goldLabel.setIcon(new ImageIcon(getClass().getResource("../resources/Gold.png")));

        foodLabel = new JLabel("Food: ");
        foodLabel.setIcon(new ImageIcon(getClass().getResource("../resources/Chicken.png")));

        unitSpaceLabel = new JLabel("Unit Space: ");
        unitSpaceLabel.setIcon(new ImageIcon(getClass().getResource("../resources/tent.png")));

        turnLabel = new JLabel("Turn: ");
        turnLabel.setIcon(new ImageIcon(getClass().getResource("../resources/Turn.png")));

        timeLabel = new JLabel("Time Left: 30s");
        timeLabel.setIcon(new ImageIcon(getClass().getResource("../resources/Time.png")));

        add(timeLabel);
        add(playerLabel);
        add(goldLabel);
        add(foodLabel);
        add(unitSpaceLabel);
        add(turnLabel);
        
        add(Box.createVerticalGlue());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    public void updateInfo(GameState gameState) {
        Kingdom currentKingdom = gameState.getCurrentKingdom();
        
        playerLabel.setText("Player: " + gameState.getCurrentPlayerTurn());
        goldLabel.setText("Gold: " + currentKingdom.getGold());
        foodLabel.setText("Food: " + currentKingdom.getFood());
        unitSpaceLabel.setText("Unit Space: " + currentKingdom.getUsedUnitSpace() + 
                              "/" + currentKingdom.getTotalUnitSpace());
        turnLabel.setText("Turn: " + gameState.getTurnNumber());
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("Time Left: " + timeLeft + "s");

                if (timeLeft <= 0) {
                    ((Timer) e.getSource()).stop();

                }
            }
        });

        timer.start();
    }
}