package views;

import models.GameState;
import models.Kingdom;
import models.blocks.Block;
import models.blocks.EmptyBlock;
import models.blocks.ForestBlock;
import models.blocks.VoidBlock;
import models.structures.*;
import models.units.Peasant;
import models.units.Spearman;
import models.units.Swordman;
import models.units.Unit;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private GameState gameState;
    private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
    private List<BlockButton> blockButtons=new ArrayList<BlockButton>();
    private static final int BLOCK_SIZE = 50;

    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        ResourceLoader rl = new ResourceLoader();
        icons=rl.geticons();
        ///////////////////////////////////
        setPreferredSize(new Dimension(gameState.getCol()*BLOCK_SIZE, gameState.getRow()*BLOCK_SIZE));


        setBackground(new Color(76, 68, 57, 255));
        setLayout(new GridLayout(12,16,0,0));



        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        creatBlockButtons();
        initializePanel();
    }
    private void creatBlockButtons() {
        Block tH1 = null;
        Block tH2=null;
        BlockButton townHall1=null;
        BlockButton townHall2=null;
        for (Kingdom kingdom : gameState.getKingdoms()) {
             Block townHallBlock= kingdom.getTownHall().getBaseBlock();
             if (kingdom.getId()==1) {
                 townHall1 = new BlockButton(icons.get("townHall-p1"), townHallBlock);
                 tH1=townHallBlock;
             }
             else if (kingdom.getId()==2) {
                 townHall2 = new BlockButton(icons.get("townHall-p2"), townHallBlock);
                 tH2=townHallBlock;
             }
        }
        Block[][] map = gameState.getGameMap();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y]==tH1){
                    blockButtons.add(townHall1);
                    continue;
                }
                else if (map[x][y]==tH2){
                    blockButtons.add(townHall2);
                    continue;
                }
                if (map[x][y] instanceof EmptyBlock){
                    blockButtons.add(new BlockButton(icons.get("emptyBlock"), map[x][y]));
                }
                else{
                    if (map[x][y] instanceof VoidBlock){
                        blockButtons.add(new BlockButton(icons.get("voidBlock"), map[x][y]));
                    }
                    else{
                        if (map[x][y] instanceof ForestBlock){
                            blockButtons.add(new BlockButton(icons.get("forestBlock"), map[x][y]));
                        }
                    }
                }
            }
        }
    }
    private void initializePanel(){
        for (BlockButton blockButton : blockButtons) {
            add(blockButton);
        }
    }
    public void addButtonMouseListener(MouseListener mouseListener) {
        for (BlockButton blockButton : blockButtons) {
            blockButton.addMouseListener(mouseListener);
        }
    }
    public GameState getGameState() {
        return gameState;
    }
    public void addButtonAL(ActionListener actionListener) {
        for (BlockButton blockButton : blockButtons) {
            blockButton.addActionListener(actionListener);
        }
    }
    public void buildStructure(Structure structure,BlockButton blockButton) {
        blockButton.getBlock().setStructure(structure);
        gameState.getCurrentKingdom().addStructure(structure);
        blockButton.setBorder();
        if (!(blockButton.getBlock() instanceof EmptyBlock)){
            EmptyBlock emptyBlock = new EmptyBlock(blockButton.getPosition());
            emptyBlock.setStructure(structure);
            emptyBlock.setUnit(blockButton.getBlock().getUnit());
            emptyBlock.setAbsorbed(blockButton.getBlock().isAbsorbed(),blockButton.getBlock().getKingdomId());
            blockButton.setBlock(emptyBlock);
        }
        if (structure instanceof Market){
            blockButton.setIcon(icons.get("market"));
        }
        else {
            if (structure instanceof Farm){
                blockButton.setIcon(icons.get("farm"));
            }
            else {
                if (structure instanceof Barrack){
                    blockButton.setIcon(icons.get("barrack"));
                }
                else {
                    if (structure instanceof Tower){
                        blockButton.setIcon(icons.get("tower"));
                    }
                }
            }
        }

    }
    public void recruitUnit(Unit unit,BlockButton selectedButton) throws IllegalStateException {
        gameState.getCurrentKingdom().addUnit(unit);
        selectedButton.getBlock().setUnit(unit);
        selectedButton.setBorder();
        if (unit instanceof Peasant){
            selectedButton.setIcon(getPeasantIcon(selectedButton.getBlock()));
        }
        else {
            if (unit instanceof Swordman){
                selectedButton.setIcon(getSwordmanIcon(selectedButton.getBlock()));
            }
            else {
                if (unit instanceof Spearman){
                    selectedButton.setIcon(getSpearmanIcon(selectedButton.getBlock()));
                }
                else {
                    selectedButton.setIcon(getKnightIcon(selectedButton.getBlock()));
                }
            }
        }

    }
    private ImageIcon getPeasantIcon(Block block) {
        if (block instanceof EmptyBlock){
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("peasant-p1E");
            }
            else {
                return icons.get("peasant-p2E");
            }
        }
        else {
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("peasant-p1F");
            }
            else {
                return icons.get("peasant-p2F");
            }
        }
    }
    private ImageIcon getSwordmanIcon(Block block) {
        if (block instanceof EmptyBlock){
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("swordman-p1E");
            }
            else {
                return icons.get("swordman-p2E");
            }
        }
        else {
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("swordman-p1F");
            }
            else {
                return icons.get("swordman-p2F");
            }
        }
    }
    private ImageIcon getSpearmanIcon(Block block) {
        if (block instanceof EmptyBlock){
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("spearman-p1E");
            }
            else {
                return icons.get("spearman-p2E");
            }
        }
        else {
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("spearman-p1F");
            }
            else {
                return icons.get("spearman-p2F");
            }
        }
    }
    private ImageIcon getKnightIcon(Block block) {
        if (block instanceof EmptyBlock){
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("knight-p1E");
            }
            else {
                return icons.get("knight-p2E");
            }
        }
        else {
            if (gameState.getCurrentKingdom().getId()==1){
                return icons.get("knight-p1F");
            }
            else {
                return icons.get("knight-p2F");
            }
        }
    }
}
