package controllers;

import models.GameState;
import models.Player;
import models.Position;
import models.blocks.Block;
import models.structures.*;
import models.units.*;
import views.*;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import database.DatabaseHandler;
import database.GameData;
import log.LogHandler;

import java.awt.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

public class GameController {
    public static final LogHandler logHandler = new LogHandler();
    private GameState gameState;
    private GameFrame gameFrame;
    private GameData gameData = new GameData();
    private static Player player1;
    private static Player player2;
    private PauseFrame pauseFrame = new PauseFrame();
    private Thread infoThread;
    private boolean isDarkMode = false;
    private GamePanel gamePanel;
    private MainInfoPanel mainInfoPanel;
    private Thread timerThread;
    private int timeLeft = 31;
    private boolean paused = false;
    private boolean isTurnEnded = false;
    private StructureSelectionDialog structureSelectionDialog;
    private UnitSelectionDialog unitSelectionDialog;
    private BlockButton selectedButton;
    private BlockButton lastClickedButton;
    private boolean mergeUnitActived=false;
    private boolean moveUnitActived=false;
    private Unit selectedUnit;
    private boolean attackModeActived=false;
    public GameController(){
        DatabaseHandler.createTable();
        logHandler.setGameController(this);

        this.gameFrame = new GameFrame();
        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);


        structureSelectionDialog = new StructureSelectionDialog(gameFrame);
        unitSelectionDialog = new UnitSelectionDialog(gameFrame);
        // New Game Button Action Listener
        gameFrame.getMenuPanel().addNewGameButtonAL(e -> {
            logHandler.log("New game button clicked at MenuPanel");
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
                logHandler.log("Dark mode activated");
                isDarkMode = true;

                gameFrame.getMenuPanel().getCenterPanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMenuPanel().getWelcomePanel().setBackground(new Color(0x1A2B44));
                gameFrame.getMenuPanel().getWelcomePanel().setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
                gameFrame.getMenuPanel().getCenterPanel().setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));

                gameFrame.getGetPlayerNamePanel().setBackground(new Color(0x1A2B44));
                gameFrame.getGetPlayerNamePanel().getPlayer1Label().setForeground(Color.white);
                gameFrame.getGetPlayerNamePanel().getPlayer2Label().setForeground(Color.white);

                gameFrame.getActionPanel().setBackground(new Color(0x1A2B44));

                gameFrame.getLoadGamePanel().getComboLabel().setForeground(Color.white);
                gameFrame.getLoadGamePanel().setBackground(new Color(0x1A2B44));

                pauseFrame.setBackground(new Color(0x1A2B44));
            }
            else{
                logHandler.log("Light mode activated");
                isDarkMode = false;

                gameFrame.getMenuPanel().getCenterPanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getMenuPanel().getWelcomePanel().setBackground(new Color(0xEDEAE6));

                gameFrame.getGetPlayerNamePanel().setBackground(new Color(0xEDEAE6));
                gameFrame.getGetPlayerNamePanel().getPlayer1Label().setForeground(Color.BLACK);
                gameFrame.getGetPlayerNamePanel().getPlayer2Label().setForeground(Color.BLACK);

                gameFrame.getActionPanel().setBackground(new Color(0xEDEAE6));

                gameFrame.getLoadGamePanel().getComboLabel().setForeground(Color.BLACK);
                gameFrame.getLoadGamePanel().setBackground(new Color(0xEDEAE6));

                pauseFrame.setBackground(new Color(0xEDEAE6));

            }
        });

        // Back Button Action Listener
        gameFrame.getGetPlayerNamePanel().addBackButtonActionListener(e -> {
            logHandler.log("Back button clicked at GetPlayerNamePanel");
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
                logHandler.log("Game start failed: Player name can't be empty");
                JOptionPane.showMessageDialog(gameFrame, "Name can't be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if (player1name.equals(player2name)) {
                    logHandler.log("Game start failed: Player names can't be the same");
                    JOptionPane.showMessageDialog(gameFrame, "Name can't be same!!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            logHandler.log("Game begins: " + player1name + " vs " + player2name);
            gameState=new GameState(12,16,2);
            gamePanel = new GamePanel(gameState);
            gamePanel.setLogHandler(logHandler);
            gamePanel.creatBlockButtons();
            gamePanel.initializePanel();
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

            mainInfoPanel.getInfoPanel().getTimeLabel().setText("Time Left: 30");

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
            attackModeActived=false;
            selectedUnit=null;
            paused=true;
            isTurnEnded=true;
            mergeUnitActived=false;
            JOptionPane.showMessageDialog(gameFrame, "Your turn ended!", "Notice", JOptionPane.INFORMATION_MESSAGE);
            if (lastClickedButton != null) {
                lastClickedButton.setBorder();
                lastClickedButton = null;
            }
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            selectedButton=null;

            logDependsOnPlayer("Clicked End Turn Button");

            gameState.nextTurn();

            logDependsOnPlayer("turn begins");

            timeLeft=31;
            isTurnEnded=false;
            paused=false;
            checkingStrAndUnit();
            
            mainInfoPanel.getInfoPanel().updateInfo();
            gamePanel.revalidate();
            gamePanel.repaint();
        });

        // Build Button AL
        gameFrame.getActionPanel().addBuildButtonAL(e -> {
            JComboBox<String> comboBox = structureSelectionDialog.getStructureComboBox();
            comboBox.setSelectedIndex(0);
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                logDependsOnPlayer("Build Button clicked without selecting a block");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }

            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                logDependsOnPlayer("Build Button clicked on a block not absorbed by current kingdom");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().hasStructure()){
                logDependsOnPlayer("Build Button clicked on a block with existing structure");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block has a structure!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                logDependsOnPlayer("Build Button clicked on a block with existing unit");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            paused=true;
            addStructureComboBoxAL();
            logDependsOnPlayer("Build Button clicked, oppening structure selection");
            structureSelectionDialog.setVisible(true);

        });

        // Done Button in structure Selection Dialog AL
        structureSelectionDialog.getDoneButton().addActionListener(e -> {
            JComboBox<String> comboBox = structureSelectionDialog.getStructureComboBox();
            String selectedItem = comboBox.getSelectedItem().toString().trim();
            if (selectedItem.equals("None")) {
                logDependsOnPlayer("Done Button clicked at selection dialog without selecting a structure");
            }
            switch (selectedItem) {
                case "Market":
                    Market market=new Market(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                    try{
                        gameState.getCurrentKingdom().canBuildStructure(market);
                        gamePanel.buildStructure(market,selectedButton);
                        logDependsOnPlayer(selectedItem + " built at position X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        structureSelectionDialog.dispose();
                        paused=false;
                        mainInfoPanel.getInfoPanel().updateInfo();
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }
                    catch (IllegalStateException ex){
                        logDependsOnPlayer("not enough money to build " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Farm":
                    Farm farm=new Farm(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                    try{
                        gameState.getCurrentKingdom().canBuildStructure(farm);
                        gamePanel.buildStructure(farm,selectedButton);
                        logDependsOnPlayer(selectedItem + " built at position X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        structureSelectionDialog.dispose();
                        paused=false;
                        mainInfoPanel.getInfoPanel().updateInfo();
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }
                    catch (IllegalStateException ex){
                        logDependsOnPlayer("not enough money to build " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Barrack":
                    Barrack barrack = new Barrack(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId());
                    try{
                        gameState.getCurrentKingdom().canBuildStructure(barrack);
                        gamePanel.buildStructure(barrack,selectedButton);
                        logDependsOnPlayer(selectedItem + " built at position X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        structureSelectionDialog.dispose();
                        paused=false;
                        mainInfoPanel.getInfoPanel().updateInfo();
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }
                    catch (IllegalStateException ex){
                        logDependsOnPlayer("not enough money to build " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Tower":
                    if (gameState.canBuildTower(selectedButton.getBlock())){
                        Tower tower=new Tower(selectedButton.getPosition(),selectedButton.getBlock(),gameState.getCurrentKingdom().getId(),gameState.createTowerCoveredBlock(selectedButton.getBlock()));
                        try{
                            gameState.getCurrentKingdom().canBuildStructure(tower);
                            gamePanel.buildStructure(tower,selectedButton);
                            logDependsOnPlayer(selectedItem + " built at position X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                            if (selectedButton != null) {
                                selectedButton.setBorder();
                            }
                            selectedButton=null;
                            lastClickedButton=null;
                            structureSelectionDialog.dispose();
                            paused=false;
                            mainInfoPanel.getInfoPanel().updateInfo();
                            gameFrame.revalidate();
                            gameFrame.repaint();
                        }
                        catch (IllegalStateException ex){
                            logDependsOnPlayer("not enough money to build " + selectedItem);
                            paused=true;
                            JOptionPane.showMessageDialog(gameFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            paused=false;
                        }finally {
                            break;
                        }
                    }
                    else {
                        logDependsOnPlayer(selectedItem + " build failed at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, "You can't build a Tower in this block!", "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                        break;
                    }
                case "None":
                    paused=true;
                    JOptionPane.showMessageDialog(gameFrame, "Please select a structure", "Error", JOptionPane.ERROR_MESSAGE);
                    paused=false;
                    break;
            }
        });

        // Cancel Button in structure Selection Dialog AL
        structureSelectionDialog.getCancelButton().addActionListener(e -> {
           logDependsOnPlayer("Structure Selection cancelled");
           paused=false;
           structureSelectionDialog.dispose();
        });

        //Recruit Button Al
        gameFrame.getActionPanel().addRecruitButtonAL(e -> {
            JComboBox<String> comboBox = unitSelectionDialog.getUnitComboBox();
            comboBox.setSelectedIndex(0);
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                logDependsOnPlayer("Recruit Button clicked without selecting a block");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                logDependsOnPlayer("Recruit Button clicked on a block not absorbed by current kingdom");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().hasStructure()){
                logDependsOnPlayer("Recruit Button clicked on a block with existing structure");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block has a structure!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                logDependsOnPlayer("Recruit Button clicked on a block with existing unit");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            paused=true;
            addUnitComboBoxAL();
            logDependsOnPlayer("Recruit Button clicked, opening unit selection");
            unitSelectionDialog.setVisible(true);
        });

        // Done Button in Unit Selection Dialog AL
        unitSelectionDialog.getDoneButton().addActionListener(e -> {
            JComboBox<String> comboBox = unitSelectionDialog.getUnitComboBox();
            String selectedItem = comboBox.getSelectedItem().toString().trim();
            if(selectedItem.equals("None")){
                logDependsOnPlayer("Done Button clicked without selecting a unit");
            }
            switch (selectedItem){
                case "Peasant":
                    Peasant peasant=new Peasant(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(peasant,selectedButton);
                        logDependsOnPlayer(selectedItem + " recruited at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        unitSelectionDialog.dispose();
                        paused=false;
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        logDependsOnPlayer("not enough money to recruit " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Swordman":
                    Swordman swordman=new Swordman(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(swordman,selectedButton);
                        logDependsOnPlayer(selectedItem + " recruited at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        unitSelectionDialog.dispose();
                        paused=false;
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        logDependsOnPlayer("not enough money to recruit " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Spearman":
                    Spearman spearman=new Spearman(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(spearman,selectedButton);
                        logDependsOnPlayer(selectedItem + " recruited at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        unitSelectionDialog.dispose();
                        paused=false;
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        logDependsOnPlayer("not enough money to recruit " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "Knight":
                    Knight knight=new Knight(gameState.getCurrentKingdom().getId(),selectedButton.getPosition());
                    try {
                        gamePanel.recruitUnit(knight,selectedButton);
                        logDependsOnPlayer(selectedItem + " recruited at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        lastClickedButton=null;
                        unitSelectionDialog.dispose();
                        paused=false;
                        gameFrame.revalidate();
                        gameFrame.repaint();
                    }catch (IllegalStateException exception){
                        logDependsOnPlayer("not enough money to recruit " + selectedItem);
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        paused=false;
                    }finally {
                        break;
                    }
                case "None":
                    paused=true;
                    JOptionPane.showMessageDialog(gameFrame, "Please select a unit", "Error", JOptionPane.ERROR_MESSAGE);
                    paused=false;
            }

        });

        // Cancel Button in Unit Selection Dialog AL
        unitSelectionDialog.getCancelButton().addActionListener(e -> {
           logDependsOnPlayer("Unit Selection cancelled");
           unitSelectionDialog.dispose();
           paused=false;
        });

        // Upgrade Structure Button AL
        gameFrame.getActionPanel().addUpdateStructureAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            mergeUnitActived=false;
            if (selectedButton == null) {
                logDependsOnPlayer("Upgrade Structure Button clicked without selecting a block");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                logDependsOnPlayer("Upgrade Structure Button clicked on a block not absorbed by current kingdom");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().hasUnit()){
                logDependsOnPlayer("Upgrade Structure Button clicked on a block with existing unit");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block has a unit!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (!selectedButton.getBlock().hasStructure()){
                logDependsOnPlayer("Upgrade Structure Button clicked on a block without existing structure");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Please select a structure", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().getStructure() instanceof TownHall){
                logDependsOnPlayer("Upgrade Structure Button clicked on a TownHall while TownHall cannot be upgraded");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "TownHall cannot be upgraded","Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            Structure structure=selectedButton.getBlock().getStructure();
            paused=true;
            int answer=JOptionPane.showConfirmDialog(gameFrame,"it cost "+structure.getUpgradeCost()+" gold to upgrade,do you want to upgrade?","Upgrade",JOptionPane.YES_NO_OPTION);
            if (answer==JOptionPane.YES_OPTION) {
                try {
                    gameState.getCurrentKingdom().upgradeStructure(structure);
                    logDependsOnPlayer("Upgraded " + structure.getClass().getSimpleName() + " at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY() + " with " + structure.getUpgradeCost() + " golds");
                    JOptionPane.showMessageDialog(gameFrame, "Upgrade successful!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    mainInfoPanel.getInfoPanel().updateInfo();
                }catch (IllegalStateException exception){
                    logDependsOnPlayer("not enough money for upgrade " + structure.getClass().getSimpleName());
                    JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    paused=false;
                }
            }
            paused=false;
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            selectedButton=null;
            lastClickedButton=null;
        });

        // Merge Unit Button AL
        gameFrame.getActionPanel().addMergeUnitAL(e -> {
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (mergeUnitActived){
                mergeUnitActived=false;
                return;
            }
            if (selectedButton == null) {
                logDependsOnPlayer("Merge Unit Button clicked without selecting a block");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Please first select a block", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                logDependsOnPlayer("Merge Unit Button clicked on a block that not absorbed by current kingdom");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            if (!selectedButton.getBlock().hasUnit()){
                logDependsOnPlayer("Merge Unit Button clicked on a block without existing unit");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Pleas select an unit", "Error", JOptionPane.ERROR_MESSAGE);
                paused=false;
                return;
            }
            logDependsOnPlayer("Merge Unit Button clicked at Block at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY());
            lastClickedButton=selectedButton;
            selectedButton=null;
            paused=true;
            JOptionPane.showMessageDialog(gameFrame, "select an unit that you want merge with it", "Notice", JOptionPane.INFORMATION_MESSAGE);
            paused=false;
            mergeUnitActived=true;
        });

        // Move Unit Button AL
        gameFrame.getActionPanel().addMoveButtonAL(e -> {
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            if (mergeUnitActived){
                mergeUnitActived=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            selectedButton=null;
            lastClickedButton=null;
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed!");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            else {
                moveUnitActived = true;
                logDependsOnPlayer("Move Mode Activated");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Actived!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }

        });

        // Load Game Button AL In Menu Panel
        gameFrame.getMenuPanel().addLoadGameButtonAL(e -> {
            logHandler.log("Load Game button clicked at MenuPanel");
            gameFrame.remove(gameFrame.getMenuPanel());
            gameFrame.add(gameFrame.getLoadGamePanel());
            gameFrame.getLoadGamePanel().setGameSavesComboBox();
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        // Back Button AL In Laod Game Panel
        gameFrame.getLoadGamePanel().addBackButtonActionListener(e -> {
            logHandler.log("Back button clicked at LoadGamePanel");
            gameFrame.remove(gameFrame.getLoadGamePanel());
            gameFrame.add(gameFrame.getMenuPanel());
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        // Delete Button AL In Laod Game Panel
        gameFrame.getLoadGamePanel().addDeleteButtonActionListener(e -> {

            String selected = (String) gameFrame.getLoadGamePanel().getSavesComboBox().getSelectedItem();
            String saveName = selected.split(":")[0].trim();
            DatabaseHandler.deleteBySaveName(saveName);
            logHandler.log("Deleted save: " + saveName);
            gameFrame.getLoadGamePanel().setGameSavesComboBox();
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        // Load Game Button AL In Laod Game Panel
        gameFrame.getLoadGamePanel().addLoadButtonActionListener(e -> {
            String selected = (String) gameFrame.getLoadGamePanel().getSavesComboBox().getSelectedItem();
            String saveName = selected.split(":")[0].trim();

//            try {
//                DatabaseHandler.loadGame(saveName);
//            } catch (JsonMappingException e1) {
//                e1.printStackTrace();
//            } catch (JsonProcessingException e1) {
//                e1.printStackTrace();
//            }
            DatabaseHandler.loadGame(saveName, this);


            gamePanel = new GamePanel(gameState);
            gamePanel.setLogHandler(logHandler);
            mainInfoPanel = new MainInfoPanel(gameState);
            setDarkMode();
            addPauseButtonAL();
            
            
            gameState.getCurrentKingdom().startTurn();
            createTimerThread();
            mainInfoPanel.getInfoPanel().updateInfo();

            gamePanel.loadGamePanel(gameState);
            gameFrame.setGamePanel(gamePanel);
            gameFrame.setMainInfoPanel(mainInfoPanel);
            setMLtoGamePanel();
            setGamePanelButtonsAl();

            mainInfoPanel.getInfoPanel().setPlayer1Name(player1.getName());
            mainInfoPanel.getInfoPanel().setPlayer2Name(player2.getName());

            logHandler.log("Loaded game: " + saveName + ": " + player1.getName() + " vs " + player2.getName());

            gameFrame.remove(gameFrame.getLoadGamePanel());
            gameFrame.add(gamePanel, BorderLayout.CENTER);
            gameFrame.add(mainInfoPanel, BorderLayout.EAST);
            gameFrame.add(gameFrame.getActionPanel(), BorderLayout.SOUTH);
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
            gameFrame.setLocationRelativeTo(null);

            mainInfoPanel.getInfoPanel().getTimeLabel().setText("Time Left: "+timeLeft);
            logDependsOnPlayer("start turn with time: " + timeLeft);
            paused=true;
            JOptionPane.showMessageDialog(gameFrame, "Click on OK whenever you're ready!", "Info", JOptionPane.INFORMATION_MESSAGE);
            paused=false;
            timerThread.start();
        });

        // Save And Back Button AL
        pauseFrame.addSaveAndBackButtonAL(e -> {
            gameData.setLogHandler(logHandler);
            gameData.saveJoption(gameFrame, this);
        });


        // Attack Button AL
        gameFrame.getActionPanel().addAttackButtonAL(e -> {
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            if (mergeUnitActived){
                mergeUnitActived=false;
            }
            selectedButton=null;
            lastClickedButton=null;
            if (moveUnitActived){
                moveUnitActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Move Mode Closed");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Move Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            if (attackModeActived){
                attackModeActived=false;
                selectedUnit=null;
                logDependsOnPlayer("Attack Mode Closed");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame, "Attack Mode Closed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
            else {
                attackModeActived=true;
                logDependsOnPlayer("Attack Mode Activated");
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,"Attack Mode Actived!", "Info", JOptionPane.INFORMATION_MESSAGE);
                paused=false;
            }
        });


    }
    private void setMLtoGamePanel(){
        gamePanel.addButtonMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BlockButton blockButton = (BlockButton) e.getSource();

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
            logHandler.logDependsOnPlayer("Selected button at this Position: X="+selectedButton.getPosition().getX()+", Y="+selectedButton.getPosition().getY());

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

                if (attackModeActived){
                    handleAttack();
                    return;
                }


                if (moveUnitActived){
                    handleMove();
                    return;
                }

                if (mergeUnitActived){
                    if (selectedButton.getBlock().getKingdomId()!=gameState.getCurrentKingdom().getId()) {
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, "This block is not absorbed by your kingdom!", "Error", JOptionPane.ERROR_MESSAGE);
                        selectedButton.setBorder();
                        selectedButton=null;
                        paused=false;
                        return;
                    }
                    if (!selectedButton.getBlock().hasUnit()){
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, "Pleas select an unit", "Error", JOptionPane.ERROR_MESSAGE);
                        selectedButton.setBorder();
                        selectedButton=null;
                        paused=false;
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
                        paused=true;
                        JOptionPane.showMessageDialog(gameFrame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        mergeUnitActived=false;
                        paused=false;
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
            mainInfoPanel.getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("/person-white.png")));

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
            mainInfoPanel.getInfoPanel().getPlayerLabel().setIcon(new ImageIcon(getClass().getResource("/person.png")));
        }
    }
    private void addPauseButtonAL(){
        mainInfoPanel.addpauseButtonAL(e -> {
            selectedUnit=null;
            if (selectedButton != null) {
                selectedButton.setBorder();
            }
            selectedButton=null;
            lastClickedButton=null;
            moveUnitActived=false;
            mergeUnitActived=false;
            attackModeActived=false;
            paused = true;
            pauseFrame.setLocationRelativeTo(gameFrame);
            pauseFrame.setVisible(true);
        });
        // resume Button Action Listener
        pauseFrame.addResumeButtonAL(e -> {
            pauseFrame.dispose();
            paused = false;
        });

    }
    private void createTimerThread(){
        timerThread = new Thread(() -> {
            while (true){
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
                    structureSelectionDialog.dispose();
                    unitSelectionDialog.dispose();
                    JOptionPane.showMessageDialog(gameFrame, "Your turn ended!", "Notice", JOptionPane.INFORMATION_MESSAGE);
                    logDependsOnPlayer("timer reached 0, turn ended");
                    mergeUnitActived=false;
                    SwingUtilities.invokeLater(() -> {
                        if (lastClickedButton != null) {
                            lastClickedButton.setBorder();
                            lastClickedButton = null;
                        }
                        if (selectedButton != null) {
                            selectedButton.setBorder();
                        }
                        selectedButton=null;
                        moveUnitActived=false;
                        attackModeActived=false;
                        selectedUnit=null;
                        mergeUnitActived=false;
                        gameState.nextTurn();
                        checkingStrAndUnit();
                        mainInfoPanel.getInfoPanel().updateInfo();
                        logDependsOnPlayer("turn begins");
                        gamePanel.repaint();
                        gameFrame.revalidate();
                    });
                }
                timeLeft=31;
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
                    logDependsOnPlayer("unit at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY() + " can't move");
                    paused=true;
                    JOptionPane.showMessageDialog(gameFrame,"Unit Can't Move");
                    paused=false;
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
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                paused=false;
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
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,"Invalid Move");
                paused=false;
                logDependsOnPlayer("Invalid " + selectedUnit.getClass().getSimpleName() + " Move from X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY() + " to X=" + lastClickedButton.getPosition().getX() + ", Y=" + lastClickedButton.getPosition().getY());
                selectedUnit = null;
                selectedButton.setBorder();
                selectedButton=null;
                if (lastClickedButton!=null) {
                    lastClickedButton.setBorder();
                    lastClickedButton=null;
                }

            }
        }
    }
    private void handleAttack(){
        if (selectedUnit == null) {
            try {
                selectedUnit = gamePanel.findUnitAt(selectedButton);
                if (!selectedUnit.isCanAttack()){
                    logDependsOnPlayer("unit at X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY() + " can't attack");
                    paused=true;
                    JOptionPane.showMessageDialog(gameFrame,"Unit Can't Attack","Error",JOptionPane.ERROR_MESSAGE);
                    paused=false;
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
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                paused=false;
                selectedButton.setBorder();
                selectedButton = null;
            }
        }
        else {
            if (gamePanel.isValidAttack(selectedUnit,selectedButton.getBlock())){
                if (selectedButton.getBlock().hasUnit()){
                    gamePanel.attackToUnit(lastClickedButton.getBlock(),selectedButton);
                }
                else {
                    gamePanel.attackToStructure(lastClickedButton.getBlock(),selectedButton);
                    if (selectedButton.getBlock().getStructure() instanceof TownHall){
                        if (gameState.getEnemyTownHall().getDurability()<=0){
                            handleEndGame();
                            return;
                        }
                    }
                }
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,"Attacked successfully","Info",JOptionPane.INFORMATION_MESSAGE);
                paused=false;
                selectedUnit.setCanAttack(false);
                selectedUnit = null;
                lastClickedButton.setBorder();
                selectedButton=null;
                lastClickedButton=null;
                gamePanel.revalidate();
                gamePanel.repaint();
            }
            else {
                logDependsOnPlayer("Invalid " + selectedUnit.getClass().getSimpleName() + " Attack from X=" + selectedButton.getPosition().getX() + ", Y=" + selectedButton.getPosition().getY() + " to X=" + lastClickedButton.getPosition().getX() + ", Y=" + lastClickedButton.getPosition().getY());
                paused=true;
                JOptionPane.showMessageDialog(gameFrame,"Invalid Attack");
                paused=false;
                selectedUnit = null;
                selectedButton.setBorder();
                selectedButton=null;
                if (lastClickedButton!=null) {
                    lastClickedButton.setBorder();
                    lastClickedButton=null;
                }
            }
        }
    }
    private void checkingStrAndUnit(){
        List<Structure> structures = gameState.getCurrentKingdom().getMustRemoveStructures();
        List<Unit> units = gameState.getCurrentKingdom().getMustRemoveUnits();

        if (!structures.isEmpty()){
            for (Structure structure : structures) {
                for (BlockButton blockButton : gamePanel.getBlockButtons()) {
                    if (blockButton.getBlock().getStructure()==structure) {
                        gamePanel.removeStructure(blockButton);
                    }
                }
            }
        }
        if (!units.isEmpty()){
            for (Unit unit : units) {
                for (BlockButton blockButton : gamePanel.getBlockButtons()) {
                    if (blockButton.getBlock().getUnit()==unit) {
                        gamePanel.removeUnit(blockButton);
                    }
                }
            }
        }
    }

    private void handleEndGame(){
        logHandler.log("Game Ended: " + player1.getName() + " vs " + player2.getName());

        if (gameState.getCurrentPlayerTurn() == 0) {
            logHandler.log(player1.getName() + " won the game!");
        } else {
            logHandler.log(player2.getName() + " won the game!");
        }

        paused=true;
        JOptionPane.showMessageDialog(gameFrame,"Great !! You Won!","Info",JOptionPane.INFORMATION_MESSAGE);
        logHandler.log("going back to main menu");
        gameFrame.remove(gamePanel);
        gameFrame.remove(mainInfoPanel);
        gameFrame.remove(gameFrame.getActionPanel());

        gameFrame.add(gameFrame.getMenuPanel());
        gameFrame.pack();
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setLocationRelativeTo(null);
    }

    public PauseFrame getPauseFrame() {
        return pauseFrame;
    }
    public GameState getGameState() {
        return gameState;
    }
    public static Player getPlayer1() {
        return player1;
    }
    public static Player getPlayer2() {
        return player2;
    }
    public int getTimeLeft() {
        return timeLeft;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public static void setPlayer1(Player player1) {
        GameController.player1 = player1;
    }
    public static void setPlayer2(Player player2) {
        GameController.player2 = player2;
    }
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void logDependsOnPlayer(String message) {
        if (gameState.getCurrentPlayerTurn() == 0) {
            logHandler.log(player1.getName() + ": " + message);
        } else {
            logHandler.log(player2.getName() + ": " + message);
        }
    }

}
