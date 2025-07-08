package views;

import models.GameState;
import models.Kingdom;
import models.blocks.Block;
import models.blocks.EmptyBlock;
import models.blocks.ForestBlock;
import models.blocks.VoidBlock;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private GameState gameState;
    private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
    private List<BlockButton> blockButtons=new ArrayList<BlockButton>();
    private static final int BLOCK_SIZE = 50;

    public GamePanel() {
        ResourceLoader rl = new ResourceLoader();
        icons=rl.geticons();
        ///////////////////////////////////
        setPreferredSize(new Dimension(800, 600));


        setBackground(new Color(76, 68, 57, 255));
        setLayout(new GridLayout(12,16,0,0));


        this.gameState = new GameState(12, 16, 2);
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
                    blockButtons.add(new BlockButton(icons.get("emptyBlock"), map[x][y],BorderFactory.createLineBorder(new Color(234, 218, 183, 255),7)));
                }
                else{
                    if (map[x][y] instanceof VoidBlock){
                        blockButtons.add(new BlockButton(icons.get("voidBlock"), map[x][y],BorderFactory.createLineBorder(new Color(171, 183, 183, 255),7)));
                    }
                    else{
                        if (map[x][y] instanceof ForestBlock){
                            blockButtons.add(new BlockButton(icons.get("forestBlock"), map[x][y],BorderFactory.createLineBorder(new Color(39, 173, 95, 255),7)));
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

    public GameState getGameState() {
        return gameState;
    }

}
