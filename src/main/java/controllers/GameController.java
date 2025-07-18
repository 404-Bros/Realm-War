package controllers;

import models.GameState;
import models.Player;
import models.Position;
import models.structures.*;
import models.units.*;
import views.*;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.event.*;
import java.util.Objects;

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
    private Thread timerThread;
    private int timeLeft = 300;
    private boolean paused = false;
    private boolean isTurnEnded = false;
    private StructureSelectionDialog structureSelectionDialog;
    private UnitSelectionDialog unitSelectionDialog;
    private BlockButton selectedButton;
    private BlockButton lastClickedButton;
    private boolean mergeUnitActived=false;
    private boolean moveUnitActived=false;
    private Unit selectedUnit;
    public GameController(){

        this.gameFrame = new GameFrame();
        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);


        structureSelectionDialog = new StructureSelectionDialog(gameFrame);
        unitSelectionDialog = new UnitSelectionDialog(gameFrame);
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


            player1 = new Player(player1name,gameState.getKingdoms().get(0));
            player2 = new Player(player2name,gameState.getKingdoms().get(1));

            setDarkMode();
            addPauseButtonAL();
            setMLtoGamePanel();
            setGamePanelButtonsAl();

            mainInfoPanel.getInfoPanel().setPlayer1Name(player1name);
            mainInfoPanel.getInfoPanel().setPlayer2Name(player2name);

            gameState.getCurrentKingdom().startTurn();
            createTimerThread();
            mainInfoPanel.getInfoPanel().updateInfo();

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
            timerThread.start();
        });

        // End Turn Button AL
        gameFrame.getActionPanel().addEndTurnButtonAL(e -> {
            moveUnitActived=false;
            paused=true;
            isTurnEnded=true;
            mergeUnitActived=false;
            JOptionPane.showMessageDialog(gameFrame, "Your turn ended!", "Notice", JOptionPane.INFORMATION_MESSAGE);
            if (lastClickedButton != null) {
                lastClickedButton.setBorder();
                lastClickedButton = null;
            }
            selectedButton=null;
            gameState.nextTurn();
            timeLeft=31;
            isTurnEnded=false;
            paused=false;
            mainInfoPanel.getInfoPanel().updateInfo();
        });

        // Build Button AL
        gameFrame.getActionPanel().addBuildButtonAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().hasStructure()){
                JOptionPane.showMessageDialog(gameFrame, "This block has a structure!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paused=true;
            addStructureComboBoxAL();
            structureSelectionDialog.setVisible(true);

        });

        // Done Button in structure Selection Dialog AL
        structureSelectionDialog.getDoneButton().addActionListener(e -> {
                JComboBox<String> comboBox = structureSelectionDialog.getStructureComboBox();
                String selectedItem = comboBox.getSelectedItem().toString().trim();
                switch (selectedItem) {
                    case "Market":
                        Market market=new Market(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                        try{
                            gameState.getCurrentKingdom().canBuildStructure(market);
                            gamePanel.buildStructure(market,selectedButton);
                            mainInfoPanel.getInfoPanel().updateInfo();
                            gameFrame.revalidate();
                            gameFrame.repaint();
                        }
                        catch (IllegalStateException ex){
                            JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }finally {
                            break;
                        }
                    case "Farm":
                        Farm farm=new Farm(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                        try{
                            gameState.getCurrentKingdom().canBuildStructure(farm);
                            gamePanel.buildStructure(farm,selectedButton);
                            mainInfoPanel.getInfoPanel().updateInfo();
                            gameFrame.revalidate();
                            gameFrame.repaint();
                        }
                        catch (IllegalStateException ex){
                            JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }finally {
                            break;
                        }
                    case "Barrack":
                        Barrack barrack = new Barrack(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                        try{
                            gameState.getCurrentKingdom().canBuildStructure(barrack);
                            gamePanel.buildStructure(barrack,selectedButton);
                            mainInfoPanel.getInfoPanel().updateInfo();
                            gameFrame.revalidate();
                            gameFrame.repaint();
                        }
                        catch (IllegalStateException ex){
                            JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }finally {
                            break;
                        }
                    case "Tower":
                        if (gameState.canBuildTower(selectedButton.getBlock())){
                            Tower tower=new Tower(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId(),gameState.createTowerCoveredBlock(selectedButton.getBlock()));
                            try{
                                gameState.getCurrentKingdom().canBuildStructure(tower);
                                gamePanel.buildStructure(tower,selectedButton);
                                mainInfoPanel.getInfoPanel().updateInfo();
                                gameFrame.revalidate();
                                gameFrame.repaint();
                            }
                            catch (IllegalStateException ex){
                                JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }finally {
                                break;
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(gameFrame, "You can't build a Tower iin this block!", "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    case "None":
                        JOptionPane.showMessageDialog(gameFrame, "Please select a structure", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                selectedButton=null;
                lastClickedButton=null;
                structureSelectionDialog.dispose();
                paused=false;

        });

        // Cancel Button in structure Selection Dialog AL
        structureSelectionDialog.getCancelButton().addActionListener(e -> {
           paused=false;
           structureSelectionDialog.dispose();
        });

        //Recruit Button Al
        gameFrame.getActionPanel().addRecruitButtonAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().hasStructure()){
                JOptionPane.showMessageDialog(gameFrame, "This block has a structure!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paused=true;
            addUnitComboBoxAL();
            unitSelectionDialog.setVisible(true);
        });

        // Done Button in Unit Selection Dialog AL
        unitSelectionDialog.getDoneButton().addActionListener(e -> {
            JComboBox<String> comboBox = unitSelectionDialog.getUnitComboBox();
            String selectedItem = comboBox.getSelectedItem().toString().trim();
            switch (selectedItem){
                case "Peasant":
                    Peasant peasant=new Peasant(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(peasant,selectedButton);
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }finally {
                        break;
                    }
                case "Swordman":
                    Swordman swordman=new Swordman(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(swordman,selectedButton);
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }finally {
                        break;
                    }
                case "Spearman":
                    Spearman spearman=new Spearman(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(spearman,selectedButton);
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }finally {
                        break;
                    }
                case "Knight":
                    Knight knight=new Knight(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(knight,selectedButton);
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }finally {
                        break;
                    }
                case "None":
                    JOptionPane.showMessageDialog(gameFrame, "Please select a unit", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            selectedButton=null;
            lastClickedButton=null;
            unitSelectionDialog.dispose();
            paused=false;
        });

        // Cancel Button in Unit Selection Dialog AL
        unitSelectionDialog.getCancelButton().addActionListener(e -> {
           unitSelectionDialog.dispose();
           paused=false;
        });

        // Upgrade Structure Button AL
        gameFrame.getActionPanel().addUpdateStructureAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!selectedButton.getBlock().hasStructure()){
                JOptionPane.showMessageDialog(gameFrame, "Please select a structure", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().getStructure() instanceof TownHall){
                JOptionPane.showMessageDialog(gameFrame, "TownHall cannot be upgraded","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Structure structure=selectedButton.getBlock().getStructure();
            paused=true;
            int answer=JOptionPane.showConfirmDialog(gameFrame,"it cost "+structure.getUpgradeCost()+" gold to upgrade,do you want to upgrade?","Upgrade",JOptionPane.YES_NO_OPTION);
            if (answer==JOptionPane.YES_OPTION) {
                try {
                    gameState.getCurrentKingdom().upgradeStructure(structure);
                    JOptionPane.showMessageDialog(gameFrame, "Upgrade successful!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    mainInfoPanel.getInfoPanel().updateInfo();
                }catch (IllegalStateException exception){
                    JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    paused=false;
                }
            }
            paused=false;
            selectedButton=null;
            lastClickedButton=null;
        });

        // Merge Unit Button AL
        gameFrame.getActionPanel().addMergeUnitAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            if (mergeUnitActived){
                mergeUnitActived=false;
                return;
            }
            if (selectedButton == null) {
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!selectedButton.getBlock().hasUnit()){
                JOptionPane.showMessageDialog(gameFrame, "Pleas select an unit", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            lastClickedButton=selectedButton;
            selectedButton=null;
            JOptionPane.showMessageDialog(gameFrame, "select an unit that you want merge with it", "Notice", JOptionPane.INFORMATION_MESSAGE);
            mergeUnitActived=true;
        });

        // Move Unit Button AL
        gameFrame.getActionPanel().addMoveButtonAL(e -> {
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            selectedButton=null;
            lastClickedButton=null;
            if (moveUnitActived){
                moveUnitActived=false;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                moveUnitActived = true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Actived!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        });


    }
    public void startNewGame() {
        gameState=new GameState(12,16,2);
        gamePanel = new GamePanel(gameState);
        mainInfoPanel = new MainInfoPanel(gameState);
        gameFrame.remove(gameFrame.getMenuPanel());
        //structureSelectionDialog = new StructureSelectionDialog(gameFrame);
        addPauseButtonAL();
        setMLtoGamePanel();
        setGamePanelButtonsAl();
        //addStructureComboBoxAL();
        player1 = new Player("player1name",gameState.getKingdoms().get(0));
        player2 = new Player("player2name",gameState.getKingdoms().get(1));
        mainInfoPanel.getInfoPanel().setPlayer1Name("player1name");
        mainInfoPanel.getInfoPanel().setPlayer2Name("player2name");

        gameState.getCurrentKingdom().startTurn();
        createTimerThread();
        mainInfoPanel.getInfoPanel().updateInfo();

        gameFrame.setMainInfoPanel(mainInfoPanel);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(mainInfoPanel, BorderLayout.EAST);
        gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
        gameFrame.pack();
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setLocationRelativeTo(null);
        timerThread.start();
    }
    private void setMLtoGamePanel(){
        gamePanel.addButtonMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BlockButton blockButton = (BlockButton) e.getSource();
                //blockButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));

                if (infoThread != null && infoThread.isAlive()) {
                    infoThread.interrupt();
                }

                infoThread = new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        SwingUtilities.invokeLater(()->{
                            blockButton.getBlockPanel().updateBlockInfo(blockButton.getBlock(),isDarkMode);
                            mainInfoPanel.setBlockPanel(blockButton.getBlockPanel());
                            mainInfoPanel.revalidate();
                            mainInfoPanel.repaint();
                            gameFrame.pack();
                        });
                    } catch (InterruptedException ex) {

                    }
                });
                infoThread.start();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                BlockButton blockButton = (BlockButton) e.getSource();
                //blockButton.setBorder(blockButton.getOriginalBorder());

                if (infoThread != null && infoThread.isAlive()) {
                    infoThread.interrupt();
                }

                SwingUtilities.invokeLater(() ->{
                    mainInfoPanel.removeBlockPanel(blockButton.getBlockPanel());
                    mainInfoPanel.revalidate();
                    mainInfoPanel.repaint();
                    gameFrame.pack();
                });

            }

        });
    }
    private void setGamePanelButtonsAl(){
        gamePanel.addButtonAL(e -> {
            selectedButton = (BlockButton) e.getSource();

            Border defaultBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
            Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
            Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, defaultBorder);
            if (lastClickedButton == selectedButton) {
                selectedButton.setBorder();
                lastClickedButton = null;
                selectedButton=null;
            } else {
                if (lastClickedButton != null) {
                    lastClickedButton.setBorder();
                }

                if (moveUnitActived){
                    handleMove();
                    return;
                }

                if (mergeUnitActived){
                    if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                        JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                        selectedButton=null;
                        //mergeUnitActived=false;
                        return;
                    }
                    if (!selectedButton.getBlock().hasUnit()){
                        JOptionPane.showMessageDialog(gameFrame, "Pleas select an unit", "Error", JOptionPane.ERROR_MESSAGE);
                        selectedButton=null;
                        //mergeUnitActived=false;
                        return;
                    }

                    try {
                        gamePanel.mergeUnit(lastClickedButton, selectedButton);
                        lastClickedButton=null;
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gameFrame.pack();
                        mergeUnitActived=false;

                    }catch (IllegalStateException exception){
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        mergeUnitActived=false;
                        return;
                    }
                }


                selectedButton.setBorder(compoundBorder);
                lastClickedButton = selectedButton;

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
            paused = true;
            pauseFrame.setLocationRelativeTo(gameFrame);
            pauseFrame.setVisible(true);
        });
        // resume Button Action Listener
        pauseFrame.addResumeButtonAL(e -> {
            pauseFrame.dispose();
            paused = false;
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
    private void createTimerThread(){
        timerThread = new Thread(() -> {
            while (true){
                timeLeft=300;
                isTurnEnded = false;
                while (timeLeft > 0 && !isTurnEnded) {
                    try {
                        if (!paused) {
                            SwingUtilities.invokeLater(() -> {
                                mainInfoPanel.getInfoPanel().getTimeLabel().setText("Time Left: " + timeLeft);
                                mainInfoPanel.getInfoPanel().revalidate();
                                mainInfoPanel.getInfoPanel().repaint();
                            });
                            timeLeft--;
                            Thread.sleep(1000);
                        } else {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!isTurnEnded) {
                    JOptionPane.showMessageDialog(gameFrame, "Your turn ended!", "Notice", JOptionPane.INFORMATION_MESSAGE);
                    mergeUnitActived=false;
                    SwingUtilities.invokeLater(() -> {
                        if (lastClickedButton != null) {
                            lastClickedButton.setBorder();
                            lastClickedButton = null;
                        }
                        selectedButton=null;
                        gameState.nextTurn();
                        mainInfoPanel.getInfoPanel().updateInfo();
                    });
                }
            }
        });
    }
    private void addStructureComboBoxAL(){
        JComboBox<String> structureComboBox =structureSelectionDialog.getStructureComboBox();
        structureComboBox.addActionListener(e -> {
            if (structureComboBox.getSelectedItem().equals("Market")) {
                structureSelectionDialog.getDutyLabel().setText("Gold Production: "+ Market.geGoldProductionByLevel(0));
                structureSelectionDialog.getCostLabel().setText("Cost: "+Market.getBuildingCost(gameState.getCurrentKingdom().getMarketCount()));
            }
            else {
                if (structureComboBox.getSelectedItem().equals("Farm")){
                    structureSelectionDialog.getDutyLabel().setText("Food Production: "+ Farm.getFoodProductionByLevel(0));
                    structureSelectionDialog.getCostLabel().setText("Cost: "+Farm.getBuildingCost(gameState.getCurrentKingdom().getMarketCount()));
                }
                else {
                    if (structureComboBox.getSelectedItem().equals("Barrack")){
                        structureSelectionDialog.getDutyLabel().setText("Unit Space: "+ Barrack.getUnitSpaceByLevel(0));
                        structureSelectionDialog.getCostLabel().setText("Cost: "+Barrack.getBuildingCost(gameState.getCurrentKingdom().getMarketCount()));
                    }
                    else{
                        if (structureComboBox.getSelectedItem().equals("Tower")){
                            structureSelectionDialog.getDutyLabel().setText("Attack Power: "+ Tower.getAttackPowerByLevel(0));
                            structureSelectionDialog.getCostLabel().setText("Cost: "+Tower.getBuildingCost(gameState.getCurrentKingdom().getMarketCount()));
                        }
                        else {
                            structureSelectionDialog.getDutyLabel().setText("Duty:");
                            structureSelectionDialog.getCostLabel().setText("Cost:");
                        }
                    }
                }
            }
        });
    }
    public void addUnitComboBoxAL(){
        JComboBox<String> unitComboBox =unitSelectionDialog.getUnitComboBox();
        unitComboBox.addActionListener(e -> {
           String selectedUnit=unitComboBox.getSelectedItem().toString().trim();
           switch (selectedUnit){
               case "Peasant":
                   unitSelectionDialog.getUnitHPLabel().setText("HP: "+ Peasant.getStaticHitPoints());
                   unitSelectionDialog.getUnitAttackPower().setText("Attack Power: "+Peasant.getStaticAttackPower());
                   unitSelectionDialog.getUnitRationLabel().setText("Rotation: "+Peasant.getStaticRationCost());
                   unitSelectionDialog.getUnitePaymentCostLabel().setText("Payment Cost: "+Peasant.getStaticPaymentCost());
                   break;
               case "Swordman":
                   unitSelectionDialog.getUnitHPLabel().setText("HP: "+ Swordman.getStaticHitPoints());
                   unitSelectionDialog.getUnitAttackPower().setText("Attack Power: "+Swordman.getStaticAttackPower());
                   unitSelectionDialog.getUnitRationLabel().setText("Rotation: "+Swordman.getStaticRationCost());
                   unitSelectionDialog.getUnitePaymentCostLabel().setText("Payment Cost: "+Swordman.getStaticPaymentCost());
                   break;
               case "Spearman":
                   unitSelectionDialog.getUnitHPLabel().setText("HP: "+ Spearman.getStaticHitPoints());
                   unitSelectionDialog.getUnitAttackPower().setText("Attack Power: "+Spearman.getStaticAttackPower());
                   unitSelectionDialog.getUnitRationLabel().setText("Rotation: "+Spearman.getStaticRationCost());
                   unitSelectionDialog.getUnitePaymentCostLabel().setText("Payment Cost: "+Spearman.getStaticPaymentCost());
                   break;
               case "Knight":
                   unitSelectionDialog.getUnitHPLabel().setText("HP: "+ Knight.getStaticHitPoints());
                   unitSelectionDialog.getUnitAttackPower().setText("Attack Power: "+Knight.getStaticAttackPower());
                   unitSelectionDialog.getUnitRationLabel().setText("Rotation: "+Knight.getStaticRationCost());
                   unitSelectionDialog.getUnitePaymentCostLabel().setText("Payment Cost: "+Knight.getStaticPaymentCost());
                   break;
               case "None":
                   unitSelectionDialog.getUnitHPLabel().setText("HP: ");
                   unitSelectionDialog.getUnitAttackPower().setText("Attack Power: ");
                   unitSelectionDialog.getUnitRationLabel().setText("Rotation: ");
                   unitSelectionDialog.getUnitePaymentCostLabel().setText("Payment Cost: ");
                   break;
           }
        });

    }
    private void handleMove(){
        if (selectedUnit == null) {
            try {
                selectedUnit = gamePanel.findUnitAt(selectedButton);
                if (!selectedUnit.canMove()){
                    JOptionPane.showMessageDialog(gameFrame,"Unit Can't Move");
                    selectedUnit = null;
                    selectedButton.setBorder();
                    selectedButton = null;
                    return;
                }
                Border defaultBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
                Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
                Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, defaultBorder);

                selectedButton.setBorder(compoundBorder);
                lastClickedButton=selectedButton;
            }catch (IllegalStateException ex){
                JOptionPane.showMessageDialog(gameFrame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                selectedButton.setBorder();
                selectedButton = null;
            }

        } else {
            if (gamePanel.isValidMove(selectedUnit, selectedButton)) {
                gamePanel.moveUnit(lastClickedButton,selectedButton);
                selectedUnit = null;
                lastClickedButton.setBorder();
                selectedButton=null;
                lastClickedButton=null;
                gamePanel.revalidate();
                gamePanel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(gameFrame,"Invalid Move");
                selectedUnit = null;
                selectedButton.setBorder();
                selectedButton=null;
                lastClickedButton.setBorder();
                lastClickedButton=null;

            }
        }
    }

}
