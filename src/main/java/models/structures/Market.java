package models.structures;

import models.Position;
import models.blocks.Block;

public class Market extends Structure {
    private static final int[] GOLD_PRODUCTION_BY_LEVEL = {7, 10, 15};
    private static final int[] BUILDING_COST_BY_LEVEL = {5, 15, 20};
    private static final int[] DURABILITY_BY_LEVEL = {50, 75, 100};

    private int goldProduction;

    public Market( Position position, Block baseBlock, int kingdomId) {
        super(3,5, DURABILITY_BY_LEVEL[0], BUILDING_COST_BY_LEVEL[0], position, baseBlock, kingdomId);
        goldProduction=GOLD_PRODUCTION_BY_LEVEL[0];
    }

    public int getGoldProduction() {
        return goldProduction;
    }


    @Override
    public boolean canUpgrade() {
        return getLevel() < getMaxLevel();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade()) {
        throw new IllegalStateException("Market is already at max level");
    }

        setLevel(getLevel() + 1);
        setDurability(DURABILITY_BY_LEVEL[getLevel() - 1]);
        this.goldProduction = GOLD_PRODUCTION_BY_LEVEL[getLevel() - 1];
    }

    @Override
    public int getUpgradeCost() {
        return BUILDING_COST_BY_LEVEL[getLevel() - 1];
    }
    public static int getBuildingCost(int marketCount) {
        return 5 + (marketCount * 2);
    }

    public static int geGoldProductionByLevel(int level) {
        return GOLD_PRODUCTION_BY_LEVEL[level];
    }
    
    @Override
    public int getUpgradeCostByLevel(int level) {
        return BUILDING_COST_BY_LEVEL[level - 1];
    }
}
