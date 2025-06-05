package models.structures;

import models.blocks.Block;

public class Farm extends Structure {
    private static final int[] FOOD_PRODUCTION_BY_LEVEL = {5, 8, 12};
    private static final int[] BUILDING_COST_BY_LEVEL = {5, 10, 15};
    private static final int[] DURABILITY_BY_LEVEL = {50, 75, 100};
    
    private int foodProduction;

    public Farm(Block baseBlock, int kingdomId) {
        super(3, DURABILITY_BY_LEVEL[0], 5, baseBlock, kingdomId);
        this.foodProduction = FOOD_PRODUCTION_BY_LEVEL[0];
    }

    @Override
    public boolean canUpgrade() {
        return getLevel() < getMaxLevel();
    }

    @Override
    public void upgrade() {}

    @Override
    public void performTurnAction() {}
    
    public int getFoodProduction() {
        return foodProduction;
    }
    
    public int getUpgradeCost() {
        return BUILDING_COST_BY_LEVEL[getLevel() - 1];
    }
    
    public static int getBuildingCost() {}
}