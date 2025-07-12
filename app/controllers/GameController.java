package controllers;

import models.GameState;
import models.Player;
import views.BlockButton;
import views.GameFrame;
import views.PauseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.event.*;

public class GameController {
    private GameState gameState;
    private GameFrame gameFrame;
    private static Player player1;
    private static Player player2;
    private PauseFrame pauseFrame = new PauseFrame();
    private Thread infoThread;
    private boolean isDarkMode = false;


    public GameController(GameState gameState, GameFrame gameFrame){
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);



        gameFrame.getGamePanel().addButtonMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                BlockButton blockButton = (BlockButton) e.getSource();
//                blockButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
//                blockButton.getBlockPanel().updateBlockInfo(blockButton.getBlock(),isDarkMode);
//
//                gameFrame.getMainInfoPanel().setBlockPanel(blockButton.getBlockPanel());
//                gameFrame.pack();
//                gameFrame.revalidate();
//                gameFrame.repaint();
//
//            }
//            @Override
//            public void mouseExited(MouseEvent e) {
//                BlockButton blockButton = (BlockButton) e.getSource();
//                blockButton.setBorder(blockButton.getOriginalBorder());
//                gameFrame.getMainInfoPanel().removeBlockPanel(blockButton.getBlockPanel());
//                gameFrame.pack();
//                gameFrame.revalidate();
//                gameFrame.repaint();
//            }
                @Override
                public void mouseEntered(MouseEvent e) {
                    BlockButton blockButton = (BlockButton) e.getSource();
                    blockButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));

                    if (infoThread != null && infoThread.isAlive()) {
                        infoThread.interrupt();
                    }

                    infoThread = new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            SwingUtilities.invokeLater(()->{
                                blockButton.getBlockPanel().updateBlockInfo(blockButton.getBlock(),isDarkMode);
                                gameFrame.getMainInfoPanel().setBlockPanel(blockButton.getBlockPanel());
                                gameFrame.pack();
                                gameFrame.revalidate();
                                gameFrame.repaint();
                            });
                        } catch (InterruptedException ex) {

                        }
                    });
                    infoThread.start();

                }
                @Override
                public void mouseExited(MouseEvent e) {
                    BlockButton blockButton = (BlockButton) e.getSource();
                    blockButton.setBorder(blockButton.getOriginalBorder());

                    if (infoThread != null && infoThread.isAlive()) {
                        infoThread.interrupt();
                    }

                    SwingUtilities.invokeLater(() ->{
                        gameFrame.getMainInfoPanel().removeBlockPanel(blockButton.getBlockPanel());
                        gameFrame.pack();
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    });

                }

        });



        // New Game Button Action Listener
        gameFrame.getMenuPanel().addNewGameButtonAL(e -> {
            gameFrame.remove(gameFrame.getMenuPanel());
            gameFrame.add(gameFrame.getGetPlayerNamePanel());
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        // Dark Mode AL
        gameFrame.getMenuPanel().addDarkModeCheckBoxAL(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox.isSelected()) {
                isDarkMode = true;

                gameFrame.getMenuPanel().getCenterPanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMenuPanel().getWelcomePanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMenuPanel().getWelcomePanel().setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
                gameFrame.getMenuPanel().getCenterPanel().setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));

                gameFrame.getGetPlayerNamePanel().setBackground(new Color(0x1A2B44));
                gameFrame.getGetPlayerNamePanel().getPlayer1Label().setForeground(Color.white);
                gameFrame.getGetPlayerNamePanel().getPlayer2Label().setForeground(Color.white);

                gameFrame.getActionPanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMainInfoPanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMainInfoPanel().getInfoPanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMainInfoPanel().getInfoPanel().getPlayerLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getFoodLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getGoldLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getTimeLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getUnitSpaceLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getTurnLabel().setForeground(Color.white);
                gameFrame.getMainInfoPanel().getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("../resources/person-white.png")));


//                gameFrame.getMainInfoPanel().getBlockPanel().getBlockTypeLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getDurabilityLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getStructureLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getResourceLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getMaintenanceCostLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getStructureLevelLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getTowerAttackPower().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitAttackPower().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitAttackRangeLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitePaymentCostLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitHPLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitMovementRangeLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitRotationLabel().setForeground(Color.white);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitSpaceLabel().setForeground(Color.white);



                pauseFrame.setBackground(new Color(0x1A2B44));
            }
            else{
                isDarkMode = false;

                gameFrame.getMenuPanel().getCenterPanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getMenuPanel().getWelcomePanel().setBackground(new Color(0xEDEAE6));

                gameFrame.getGetPlayerNamePanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getGetPlayerNamePanel().getPlayer1Label().setForeground(Color.BLACK);
                gameFrame.getGetPlayerNamePanel().getPlayer2Label().setForeground(Color.BLACK);

                gameFrame.getActionPanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getMainInfoPanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getMainInfoPanel().getInfoPanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getMainInfoPanel().getInfoPanel().getPlayerLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getFoodLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getGoldLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getTimeLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getUnitSpaceLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getTurnLabel().setForeground(Color.BLACK);
                gameFrame.getMainInfoPanel().getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("../resources/person.png")));

//                gameFrame.getMainInfoPanel().getBlockPanel().getBlockTypeLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getDurabilityLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getStructureLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getResourceLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getMaintenanceCostLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getStructureLevelLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getTowerAttackPower().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitAttackPower().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitAttackRangeLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitePaymentCostLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitHPLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitMovementRangeLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitRotationLabel().setForeground(Color.BLACK);
//                gameFrame.getMainInfoPanel().getBlockPanel().getUnitSpaceLabel().setForeground(Color.BLACK);


                pauseFrame.setBackground(new Color(0xEDEAE6));

            }
        });

        // Back Button Action Listener
        gameFrame.getGetPlayerNamePanel().addBackButtonActionListener(e -> {
            gameFrame.remove(gameFrame.getGetPlayerNamePanel());
            gameFrame.add(gameFrame.getMenuPanel());
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        // start Button Action Listener
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

            gameFrame.getMainInfoPanel().getInfoPanel().setPlayer1Name(player1name);
            gameFrame.getMainInfoPanel().getInfoPanel().setPlayer2Name(player2name);
            gameFrame.getMainInfoPanel().getInfoPanel().updateInfo();

            player1 = new Player(player1name,gameState.getKingdoms().get(0));
            player2 = new Player(player2name,gameState.getKingdoms().get(1));

            gameFrame.remove(gameFrame.getGetPlayerNamePanel());
            gameFrame.add(gameFrame.getGamePanel(), BorderLayout.CENTER);
            gameFrame.add(gameFrame.getMainInfoPanel(), BorderLayout.EAST);
            gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
            gameFrame.setLocationRelativeTo(null);
        });

        // pause Button Action Listener
        gameFrame.getMainInfoPanel().addpauseButtonAL(e -> {
            gameFrame.getMainInfoPanel().getInfoPanel().getTimer().stop();
            pauseFrame.setLocationRelativeTo(gameFrame);
            pauseFrame.setVisible(true);
        });

        // resume Button Action Listener
        pauseFrame.addResumeButtonAL(e -> {
            pauseFrame.dispose();
            gameFrame.getMainInfoPanel().getInfoPanel().getTimer().start();
        });

        // End Turn Button AL
        gameFrame.getActionPanel().addEndTurnButtonAL(e -> {

        });
    }

}
