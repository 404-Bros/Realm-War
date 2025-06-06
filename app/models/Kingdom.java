package models;

import models.blocks.Block;
import models.structures.Structure;
import models.units.Unit;
import models.structures.TownHall;
import models.structures.Farm;
import models.structures.Market;

import java.util.ArrayList;
import java.util.List;

public class Kingdom {
    private int id;
    private int gold;
    private int food;
    private int totalUnitSpace;
    private int usedUnitSpace;
    private TownHall townHall;
    private List<Structure> structures;
    private List<Unit> units;
    private List<Block> absorbedBlocks;

    public Kingdom(int id, TownHall townHall) {
        this.id = id;
        this.townHall = townHall;
        this.structures = new ArrayList<>();
        this.units = new ArrayList<>();
        this.absorbedBlocks = new ArrayList<>();
        
        this.structures.add(townHall);
        this.totalUnitSpace = townHall.getUnitSpace();
        this.gold = 20; // Starting gold
        this.food = 20; // Starting food
    }



    public boolean canBuildStructure(Structure structure) {
        return true;
    }
    
    public void addStructure(Structure structure) {
        structures.add(structure);
    }
    
    public void addUnit(Unit unit) {

    }
    
    public void absorbBlock(Block block) {
        block.setAbsorbed(true, this.id);
        absorbedBlocks.add(block);
    }
    
    public void updateResources() {

    }
    
    // Getters and setters
    public int getId() { return id; }
    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
    public int getFood() { return food; }
    public void setFood(int food) { this.food = food; }
    public int getTotalUnitSpace() { return totalUnitSpace; }
    public int getUsedUnitSpace() { return usedUnitSpace; }
    public TownHall getTownHall() { return townHall; }
    public List<Structure> getStructures() { return structures; }
    public List<Unit> getUnits() { return units; }
    public List<Block> getAbsorbedBlocks() { return absorbedBlocks; }
}