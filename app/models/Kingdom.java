package models;

import models.blocks.Block;
import models.structures.*;
import models.units.Unit;

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
    private int farmCount;
    private int marketCount;
    private int towerCount;
    private int barrackCount;

    public Kingdom(int id, TownHall townHall) {
        this.id = id;
        this.townHall = townHall;
        this.structures = new ArrayList<>();
        this.units = new ArrayList<>();
        this.absorbedBlocks = new ArrayList<>();
        
        this.structures.add(townHall);
        this.totalUnitSpace = townHall.getUnitSpace();
        this.gold = 20;
        this.food = 20;
        farmCount = 0;
        marketCount = 0;
        towerCount = 0;
        barrackCount = 0;
    }

    public void startTurn() {

        for (Structure structure : structures) {
            if (structure instanceof TownHall) {
                this.gold += ((TownHall) structure).getGoldProduction();
                this.food += ((TownHall) structure).getFoodProduction();
            } else if (structure instanceof Farm) {
                this.food += ((Farm) structure).getFoodProduction();
            } else if (structure instanceof Market) {
                this.gold += ((Market) structure).getGoldProduction();
            }
        }


        for (Block block : absorbedBlocks) {
            this.gold += block.getResourceYield("GOLD");
            this.food += block.getResourceYield("FOOD");
        }


        for (Structure structure : structures) {
            this.gold -= structure.getMaintenanceCost();
        }


        for (Unit unit : units) {
            this.gold -= unit.getPaymentCost();
            this.food -= unit.getRationCost();
        }
    }

    public boolean canBuildStructure(Structure structure) {
        if (structure.getKingdomId() != this.id) {
            return false;
        }
        if (structure instanceof Farm) {
            if (gold<Farm.getBuildingCost(farmCount))
                return false;
            if (farmCount >= structure.getMaxCount())
                return false;
        }
        else {
            if (structure instanceof Market) {
                if (gold<Market.getBuildingCost(marketCount))
                    return false;
                if (marketCount >= structure.getMaxCount())
                    return false;
            } else {
                if (structure instanceof Tower) {
                    if (gold<Tower.getBuildingCost(towerCount))
                        return false;
                    if (towerCount >= structure.getMaxCount())
                        return false;
                }
                else{
                    if (structure instanceof Barrack) {
                        if (gold<Barrack.getBuildingCost(barrackCount))
                            return false;
                        if (barrackCount >= structure.getMaxCount())
                            return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void addStructure(Structure structure) {
        structures.add(structure);
        if (structure instanceof Farm)
            farmCount++;
        else if (structure instanceof Market)
            marketCount++;
            else if (structure instanceof Tower)
                towerCount++;
                else
                    if (structure instanceof Barrack)
                        barrackCount++;
    }
    
    public void addUnit(Unit unit) {
        if (usedUnitSpace + unit.getUnitSpace() > totalUnitSpace) {
            throw new IllegalStateException("Not enough unit space");
        }
        units.add(unit);
        usedUnitSpace += unit.getUnitSpace();
    }
    
    public void absorbBlock(Block block) {
        block.setAbsorbed(true, this.id);
        absorbedBlocks.add(block);
    }

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