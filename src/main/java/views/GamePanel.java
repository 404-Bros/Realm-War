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
    }
    public void creatBlockButtons() {
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
    public void initializePanel(){
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

    public void mergeUnit(BlockButton lastClickedButton,BlockButton selectedButton) throws IllegalStateException {
        Unit secondUnit = selectedButton.getBlock().getUnit();
        Unit firstUnit = lastClickedButton.getBlock().getUnit();
        Unit finallyUnit=secondUnit.merge(firstUnit);
        gameState.getCurrentKingdom().removeUnit(selectedButton.getBlock().getUnit());
        gameState.getCurrentKingdom().removeUnit(lastClickedButton.getBlock().getUnit());
        selectedButton.getBlock().setUnit(null);
        lastClickedButton.getBlock().setUnit(null);
        if (lastClickedButton.getBlock() instanceof EmptyBlock){
            lastClickedButton.setIcon(icons.get("emptyBlock"));
            lastClickedButton.setBorder();
        }
        else {
            lastClickedButton.setIcon(icons.get("forestBlock"));
            lastClickedButton.setBorder();
        }
        recruitUnit(finallyUnit,selectedButton);
    }

    public Unit findUnitAt(BlockButton selectedBlock) {
        Unit unit = selectedBlock.getBlock().getUnit();
        if (unit==null){
            throw new IllegalStateException("Block does not have a unit!!");
        }
        return unit;
    }

    public boolean isValidMove(Unit selectedUnit,BlockButton selectedButton) {
        Block selectedBlock = selectedButton.getBlock();
        if (selectedBlock instanceof VoidBlock){
            return false;
        }
        if (selectedBlock.hasStructure()){
            return false;
        }
        if (selectedBlock.hasUnit()){
            return false;
        }

        int courentX = selectedUnit.getPosition().getX();
        int courentY = selectedUnit.getPosition().getY();
        int finalX = selectedButton.getPosition().getX();
        int finalY = selectedButton.getPosition().getY();
        int distance = Math.abs(finalX - courentX)+Math.abs(finalY - courentY);

        if (distance!=1){
            return false;
        }

        if (gameState.getEnemyTower() != null) {
            for (Tower tower : gameState.getEnemyTower()){
                for (Block block : tower.getCoveredBlock()){
                    if (selectedBlock == block){
                        if (tower.getLevel()==1 && selectedUnit instanceof Peasant){
                            return false;
                        }
                        else {
                            if (tower.getLevel()==2 && (selectedUnit instanceof Spearman || selectedUnit instanceof Peasant)){
                                return false;
                            }
                            else {
                                if ((tower.getLevel() == 3) && (selectedUnit instanceof Swordman || selectedUnit instanceof Peasant || selectedUnit instanceof Spearman)){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public void moveUnit(BlockButton unitCourentButton,BlockButton selectedButton) {
        Unit selectedUnit =unitCourentButton.getBlock().getUnit();
        unitCourentButton.getBlock().setUnit(null);
        if (unitCourentButton.getBlock() instanceof EmptyBlock){
            unitCourentButton.setIcon(icons.get("emptyBlock"));
        }
        else {
            unitCourentButton.setIcon(icons.get("forestBlock"));
        }
        selectedButton.getBlock().setUnit(selectedUnit);
        selectedUnit.setPosition(selectedButton.getPosition());
        selectedUnit.setMoveCount(selectedUnit.getMoveCount()+1);


        if (selectedUnit instanceof Peasant){
            selectedButton.setIcon(getPeasantIcon(selectedButton.getBlock()));
        }
        else {
            if (selectedUnit instanceof Swordman){
                selectedButton.setIcon(getSwordmanIcon(selectedButton.getBlock()));
            }
            else {
                if (selectedUnit instanceof Spearman){
                    selectedButton.setIcon(getSpearmanIcon(selectedButton.getBlock()));
                }
                else {
                    selectedButton.setIcon(getKnightIcon(selectedButton.getBlock()));
                }
            }
        }
        if (selectedButton.getBlock().getKingdomId() != selectedUnit.getKingdomId()){
            gameState.getCurrentKingdom().absorbBlock(selectedButton.getBlock());
        }

        if (gameState.getEnemyTower() != null) {
            for (Tower tower : gameState.getEnemyTower()){
                for (Block block : tower.getCoveredBlock()){
                    if (selectedButton.getBlock() == block){
                        selectedUnit.setHitPoints(selectedUnit.getHitPoints()-tower.getAttackPower());
                        selectedUnit.setAttakedByTower(true);
                    }
                }
            }
        }
        selectedButton.setBorder();
    }

    public void removeUnit(BlockButton blockButton) {
        gameState.getCurrentKingdom().removeUnit(blockButton.getBlock().getUnit());
        blockButton.getBlock().setUnit(null);
        if (blockButton.getBlock() instanceof EmptyBlock){
            blockButton.setIcon(icons.get("emptyBlock"));
        }
        else {
            blockButton.setIcon(icons.get("forestBlock"));
        }
        blockButton.setBorder();
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

    public boolean isValidAttack(Unit unit,Block block) {
        if (!block.hasUnit() && !block.hasStructure() ){
            return false;
        }
        if (block.getKingdomId()==unit.getKingdomId()){
            return false;
        }
        int unitX = unit.getPosition().getX();
        int unitY = unit.getPosition().getY();
        int targetX = block.getPosition().getX();
        int targetY = block.getPosition().getY();
        int distance = Math.abs(unitX - targetX) + Math.abs(unitY - targetY);
        return distance <= unit.getAttackRange();

    }

    public void attackToUnit(Block attackerBlock,BlockButton attackedBlockButton) {
        Unit attacker= attackerBlock.getUnit();
        Unit attacked= attackedBlockButton.getBlock().getUnit();
        if (attackerBlock instanceof EmptyBlock){
            attacked.setHitPoints((int) (attacked.getHitPoints()-(attacker.getAttackPower()*ForestBlock.getAttackBonus())));
        }
        else {
            attacked.setHitPoints(attacked.getHitPoints()-attacker.getAttackPower());
        }
        if (attacked.getHitPoints() <= 0){
            removeUnit(attackedBlockButton);
        }
    }
    public void attackToStructure(Block attackerBlock,BlockButton attackedBlockButton) {
        Unit attacker= attackerBlock.getUnit();
        Structure attacked= attackedBlockButton.getBlock().getStructure();
        if (attackerBlock instanceof EmptyBlock){
            attacked.setDurability((int) (attacked.getDurability()-(attacker.getAttackPower()*ForestBlock.getAttackBonus())));
        }
        else {
            attacked.setDurability((attacked.getDurability()-attacker.getAttackPower()));
        }
        if (attacked.getDurability() <= 0){
            if (!(attacked instanceof TownHall)){
                removeStructure(attackedBlockButton);
            }
        }
    }
    public void removeStructure(BlockButton blockButton) {
        getGameState().getCurrentKingdom().removeStructure(blockButton.getBlock().getStructure());
        blockButton.getBlock().setStructure(null);
        if (blockButton.getBlock() instanceof EmptyBlock){
            blockButton.setIcon(icons.get("emptyBlock"));
        }
        else {
            blockButton.setIcon(icons.get("forestBlock"));
        }
        blockButton.setBorder();
    }

    public List<BlockButton> getBlockButtons() {
        return blockButtons;
    }
}
