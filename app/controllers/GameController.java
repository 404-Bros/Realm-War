package controllers;

import models.GameState;
import models.Player;
import views.*;

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
    private GamePanel gamePanel;
    private MainInfoPanel mainInfoPanel;


    public GameController(){

        this.gameFrame = new GameFrame();
        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);







        // New Game Button Action Listener
        gameFrame.getMenuPanel().addNewGameButtonAL(e -> {
            GameState gameState=new GameState(12,16,2);
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

            gameState=new GameState(12,16,2);
            gamePanel = new GamePanel(gameState);
            mainInfoPanel = new MainInfoPanel(gameState);

            setDarkMode();
            addPauseButtonAL();
            setMLtoGamePanel();

            mainInfoPanel.getInfoPanel().setPlayer1Name(player1name);
            mainInfoPanel.getInfoPanel().setPlayer2Name(player2name);
            mainInfoPanel.getInfoPanel().updateInfo();

            player1 = new Player(player1name,gameState.getKingdoms().get(0));
            player2 = new Player(player2name,gameState.getKingdoms().get(1));


            gameFrame.setGamePanel(gamePanel);
            gameFrame.setMainInfoPanel(mainInfoPanel);

            gameFrame.remove(gameFrame.getGetPlayerNamePanel());
            gameFrame.add(gamePanel, BorderLayout.CENTER);
            gameFrame.add(mainInfoPanel, BorderLayout.EAST);
            gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
            gameFrame.setLocationRelativeTo(null);
        });

        // End Turn Button AL
        gameFrame.getActionPanel().addEndTurnButtonAL(e -> {

        });
    }
//    public void startNewGame() {
//        gameFrame.getContentPane().removeAll();
//        gameFrame.add(gameFrame.getGamePanel(), BorderLayout.CENTER);
//        gameFrame.add(gameFrame.getMainInfoPanel(), BorderLayout.EAST);
//        gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
//        gameFrame.pack();
//        gameFrame.revalidate();
//        gameFrame.repaint();
//    }
    private void setMLtoGamePanel(){
        gamePanel.addButtonMouseListener(new MouseAdapter() {
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
    }

    private void setDarkMode(){
        if (isDarkMode) {

            mainInfoPanel.setBackground(new Color(0x1A2B44));
            mainInfoPanel.getInfoPanel().setBackground(new Color(0x1A2B44));
            mainInfoPanel.getInfoPanel().getPlayerLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getFoodLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getGoldLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getTimeLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getUnitSpaceLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getTurnLabel().setForeground(Color.white);
            mainInfoPanel.getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("../resources/person-white.png")));

        }
        else {
            mainInfoPanel.setBackground(new Color(0xEDEAE6));
            mainInfoPanel.getInfoPanel().setBackground(new Color(0xEDEAE6));
            mainInfoPanel.getInfoPanel().getPlayerLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getFoodLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getGoldLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getTimeLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getUnitSpaceLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getTurnLabel().setForeground(Color.BLACK);
            mainInfoPanel.getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("../resources/person.png")));
        }
    }
    private void addPauseButtonAL(){
        mainInfoPanel.addpauseButtonAL(e -> {
            mainInfoPanel.getInfoPanel().getTimer().stop();
            pauseFrame.setLocationRelativeTo(gameFrame);
            pauseFrame.setVisible(true);
        });
        // resume Button Action Listener
        pauseFrame.addResumeButtonAL(e -> {
            pauseFrame.dispose();
            mainInfoPanel.getInfoPanel().getTimer().start();
        });

        pauseFrame.addSaveAndBackButtonAL(e -> {

            pauseFrame.dispose();

            gameFrame.remove(gameFrame.getMainInfoPanel());
            gameFrame.remove(gameFrame.getActionPanel());
            gameFrame.remove(gameFrame.getGamePanel());

            gameFrame.add(gameFrame.getMenuPanel());
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.revalidate();
            gameFrame.repaint();
        });
    }



}
