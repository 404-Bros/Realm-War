package models.structures;

import models.Position;
import models.blocks.Block;

public class Market extends Structure {
    private static final int[] GOLD_PRODUCTION_BY_LEVEL = {5, 8, 12};
    private static final int[] BUILDING_COST_BY_LEVEL = {5, 10, 15};
    private static final int[] DURABILITY_BY_LEVEL = {50, 75, 100};

    private int goldProduction;

    public Market(int goldProduction, int maxLevel, int initialDurability, int maintenanceCost, Position position, Block baseBlock, int kingdomId) {
        super(maxLevel,5, DURABILITY_BY_LEVEL[0], BUILDING_COST_BY_LEVEL[0], position, baseBlock, kingdomId);

    }

    public int getGoldProduction() {
        return goldProduction;
    }

    @Override
    public int getMaintenanceCost() {
        return 0;
    }

    @Override
    public void performTurnAction() {}

    @Override
    public boolean canUpgrade() {
        return getLevel() < getMaxLevel();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade()) {
        throw new IllegalStateException("Farm is already at max level");
    }

        setLevel(getLevel() + 1);
        setDurability(DURABILITY_BY_LEVEL[getLevel() - 1]);
        this.goldProduction = GOLD_PRODUCTION_BY_LEVEL[getLevel() - 1];
    }

    public static int getBuildingCost(int marketCount) {
        return 5 + (marketCount * 2);
    }
}
