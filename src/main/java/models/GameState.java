package main.java.models;

import java.util.ArrayList;
import java.util.List;
import main.java.models.blocks.Block;
import main.java.models.blocks.VoidBlock;
import main.java.models.blocks.ForestBlock;
import main.java.models.blocks.EmptyBlock;
import main.java.models.structures.Tower;
import main.java.models.structures.TownHall;


public class GameState {
    private int currentPlayerTurn;
    private List<Kingdom> kingdoms;
    private Block[][] gameMap;
    private int turnNumber;
    private boolean running;
    private int row;
    private int col;

    public GameState(int mapWidth, int mapHeight, int playerCount) {
        this.row=mapWidth;
        this.col=mapHeight;
        this.kingdoms = new ArrayList<>();
        this.gameMap = new Block[mapWidth][mapHeight];
        this.currentPlayerTurn = 0;
        this.turnNumber = 1;

        initializeMap(mapWidth, mapHeight);
        initializeKingdoms(playerCount);
    }

    private void initializeMap(int width, int height) {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    gameMap[x][y] = new VoidBlock(new Position(x, y));
                } else if (Math.random() < 0.2) {
                    gameMap[x][y] = new ForestBlock(new Position(x, y));
                } else {
                    gameMap[x][y] = new EmptyBlock(new Position(x, y));
                }
            }
        }
    }

    private void initializeKingdoms(int playerCount) {

        int[][] startingPositions = {
                {1, 1},
                {gameMap.length - 2, gameMap[0].length - 2},
                {1, gameMap[0].length - 2},
                {gameMap.length - 2, 1}
        };

        for (int i = 0; i < playerCount; i++) {
            Position townHallPos = new Position(startingPositions[i][0], startingPositions[i][1]);
            Block baseBlock = gameMap[townHallPos.getX()][townHallPos.getY()];
            TownHall townHall = new TownHall(townHallPos, baseBlock, i + 1);
            baseBlock.setStructure(townHall);
            Kingdom kingdom = new Kingdom(i + 1, townHall);
            kingdoms.add(kingdom);


            absorbSurroundingBlocks(kingdom, townHallPos);
        }
    }
    public boolean canBuildTower(Block centerBlock) {
        Position center = centerBlock.getPosition();
        for (int k = -1; k <= 1; k++) {
            for (int t = -1; t <= 1; t++) {
                int x = center.getX() + k;
                int y = center.getY() + t;

                if (x >= 0 && x < gameMap.length && y >= 0 && y < gameMap[0].length) {
                    Block block = gameMap[x][y];
                    if (block.getKingdomId() != getCurrentKingdom().getId()) {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }
    public List<Block> createTowerCoveredBlock(Block centerBlock) {
        List<Block> coveredBlock = new ArrayList<Block>();
        Position center = centerBlock.getPosition();
        for (int k = -1; k <= 1; k++) {
            for (int t = -1; t <= 1; t++) {
                int x = center.getX() + k;
                int y = center.getY() + t;
                coveredBlock.add(gameMap[x][y]);
            }
        }
        return coveredBlock;
    }
    private void absorbSurroundingBlocks(Kingdom kingdom, Position center) {

        for (int k = -1; k <= 1; k++) {
            for (int t = -1; t <= 1; t++) {
                int x = center.getX() + k;
                int y = center.getY() + t;

                if (x >= 0 && x < gameMap.length && y >= 0 && y < gameMap[0].length) {
                    Block block = gameMap[x][y];
                    if (!(block instanceof VoidBlock)) {
                        kingdom.absorbBlock(block);
                    }
                }
            }
        }
    }

    public void nextTurn() {


        currentPlayerTurn = (currentPlayerTurn + 1) % kingdoms.size();
        if (currentPlayerTurn == 0) {
            turnNumber++;
        }


        kingdoms.get(currentPlayerTurn).startTurn();
    }


    public List<Tower> getEnemyTower(){
        if (currentPlayerTurn == 0) {
            return kingdoms.get(1).getTowers();
        }
        else {
            return kingdoms.get(0).getTowers();
        }
    }
    // Getters and setters
    public int getCurrentPlayerTurn() { return currentPlayerTurn; }
    public List<Kingdom> getKingdoms() { return kingdoms; }
    public Block[][] getGameMap() { return gameMap; }
    public int getTurnNumber() { return turnNumber; }
    public Kingdom getCurrentKingdom() { return kingdoms.get(currentPlayerTurn); }

    public boolean isRunning() {
        return running;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}