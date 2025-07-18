package models;

import models.blocks.Block;
import models.blocks.VoidBlock;
import models.structures.*;
import models.units.Peasant;
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
    private List<Tower> towers;
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

        this.towers = new ArrayList<>();
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
            this.food -= unit.getRationCost();
        }

        if (!towers.isEmpty()) {
            for (Tower tower : towers) {
                for (Block block : tower.getCoveredBlock()){
                    if(block.hasUnit()){
                        if (block.getUnit().getKingdomId() != this.id) {
                            if (block.getUnit().isAttakedByTower()){
                                block.getUnit().setAttakedByTower(false);
                            }
                            else {
                                block.getUnit().setHitPoints(block.getUnit().getHitPoints()-tower.getAttackPower());
                            }
                        }
                    }
                }
            }
        }
    }

    public void canBuildStructure(Structure structure) throws IllegalStateException {
        if (structure instanceof Farm) {
            if (gold<Farm.getBuildingCost(farmCount))
                throw new IllegalStateException("Not enough gold to build this structure");
            if (farmCount >= structure.getMaxCount())
                throw new IllegalStateException("cannot build more than " + structure.getMaxCount() + "of this structure");
        }
        else {
            if (structure instanceof Market) {
                if (gold<Market.getBuildingCost(marketCount))
                    throw new IllegalStateException("Not enough gold to build this structure");
                if (marketCount >= structure.getMaxCount())
                    throw new IllegalStateException("cannot build more than " + structure.getMaxCount() + "of this structure");
            } else {
                if (structure instanceof Tower) {
                    if (gold<Tower.getBuildingCost(towerCount))
                        throw new IllegalStateException("Not enough gold to build this structure");
                    if (towerCount >= structure.getMaxCount())
                        throw new IllegalStateException("cannot build more than " + structure.getMaxCount() + "of this structure");
                }
                else{
                    if (structure instanceof Barrack) {
                        if (gold<Barrack.getBuildingCost(barrackCount))
                            throw new IllegalStateException("Not enough gold to build this structure");
                        if (barrackCount >= structure.getMaxCount())
                            throw new IllegalStateException("cannot build more than " + structure.getMaxCount() + "of this structure");
                    }
                }
            }
        }
    }
    
    public void addStructure(Structure structure) {
        structures.add(structure);
        if (structure instanceof Farm) {
            gold -= Farm.getBuildingCost(farmCount);
            farmCount++;
        }
        else {
            if (structure instanceof Market) {
                gold -= Market.getBuildingCost(marketCount);
                marketCount++;
            }
            else {
                if (structure instanceof Tower) {
                    gold -= Tower.getBuildingCost(towerCount);
                    towerCount++;
                    towers.add( (Tower) structure);
                }
                else {
                    if (structure instanceof Barrack) {
                        gold -= Barrack.getBuildingCost(barrackCount);
                        totalUnitSpace+=Barrack.getUnitSpaceByLevel(structure.getLevel()-1);
                        barrackCount++;
                    }
                }
            }
        }
    }
    public void addUnit(Unit unit) {
        if (gold<unit.getPaymentCost()){
            throw new IllegalStateException("Not enough gold to recruit");
        }
        if (usedUnitSpace + unit.getUnitSpace() > totalUnitSpace) {
            throw new IllegalStateException("Not enough unit space");
        }
        units.add(unit);
        usedUnitSpace += unit.getUnitSpace();
    }

    public void upgradeStructure(Structure structure) throws IllegalStateException {
         if (gold<structure.getUpgradeCost()){
             throw new IllegalStateException("Not enough gold to upgrade");
         }
         structure.upgrade();
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }



    public void absorbBlock(Block block) {
        block.setAbsorbed(true, this.id);
        absorbedBlocks.add(block);
    }

    public int getFarmCount() {
        return farmCount;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public int getTowerCount() {
        return towerCount;
    }

    public int getBarrackCount() {
        return barrackCount;
    }


    public List<Tower> getTowers() {
        return towers;
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