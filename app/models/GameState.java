package models;

import java.util.ArrayList;
import java.util.List;
import models.blocks.Block;
import models.blocks.VoidBlock;
import models.blocks.ForestBlock;
import models.blocks.EmptyBlock;
import models.structures.TownHall;


public class GameState {
    private int currentPlayerTurn;
    private List<Kingdom> kingdoms;
    private Block[][] gameMap;
    private int turnNumber;
    private boolean running;

    public GameState(int mapWidth, int mapHeight, int playerCount) {
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
            Kingdom kingdom = new Kingdom(i + 1, townHall);
            kingdoms.add(kingdom);


            absorbSurroundingBlocks(kingdom, townHallPos);
        }
    }

    private void absorbSurroundingBlocks(Kingdom kingdom, Position center) {

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = center.getX() + dx;
                int y = center.getY() + dy;

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

    // Getters and setters
    public int getCurrentPlayerTurn() { return currentPlayerTurn; }
    public List<Kingdom> getKingdoms() { return kingdoms; }
    public Block[][] getGameMap() { return gameMap; }
    public int getTurnNumber() { return turnNumber; }
    public Kingdom getCurrentKingdom() { return kingdoms.get(currentPlayerTurn); }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}