package models.structures;

import models.Position;
import models.blocks.Block;

public class Barrack extends Structure {
    private static final int[] UNIT_SPACE_BY_LEVEL = {5, 8, 12};
    private static final int[] BUILDING_COST_BY_LEVEL = {5, 10, 15};
    private static final int[] DURABILITY_BY_LEVEL = {50, 75, 100};
    
    private int unitSpace;

    public Barrack(Position position, Block baseBlock, int kingdomId) {
        super(3,4, DURABILITY_BY_LEVEL[0], 5, position, baseBlock, kingdomId);
        this.unitSpace = UNIT_SPACE_BY_LEVEL[0];
    }

    @Override
    public boolean canUpgrade() {
        return getLevel() < getMaxLevel();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade()) {
            throw new IllegalStateException("Barrack is already at max level");
        }

        setLevel(getLevel() + 1);
        setDurability(DURABILITY_BY_LEVEL[getLevel() - 1]);
        this.unitSpace = UNIT_SPACE_BY_LEVEL[getLevel() - 1];
    }

    
    public int getUnitSpace() {
        return unitSpace;
    }
    @Override
    public int getUpgradeCost() {
        return BUILDING_COST_BY_LEVEL[getLevel() - 1];
    }

    public static int getBuildingCost(int barracksCount) {
        return 5 + (barracksCount * 5);
    }
    public static int getUnitSpaceByLevel(int level) {
        return UNIT_SPACE_BY_LEVEL[level];
    }

    @Override
    public int getUpgradeCostByLevel(int level) {
        return BUILDING_COST_BY_LEVEL[level - 1];
    }
}