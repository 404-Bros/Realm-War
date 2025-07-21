package models.structures;

import models.Position;
import models.blocks.Block;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Farm extends Structure {
    @JsonIgnore
    private static final int[] FOOD_PRODUCTION_BY_LEVEL = {5, 8, 12};
    @JsonIgnore
    private static final int[] BUILDING_COST_BY_LEVEL = {3, 10, 15};
    @JsonIgnore
    private static final int[] DURABILITY_BY_LEVEL = {50, 75, 100};
    
    private int foodProduction;

    public Farm(Position position, Block baseBlock, int kingdomId) {
        super(3,5, DURABILITY_BY_LEVEL[0], 5, position, baseBlock, kingdomId);
        this.foodProduction = FOOD_PRODUCTION_BY_LEVEL[0];
    }

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
        this.foodProduction = FOOD_PRODUCTION_BY_LEVEL[getLevel() - 1];
    }

    
    public int getFoodProduction() {
        return foodProduction;
    }
    @Override
    public int getUpgradeCost() {
        return BUILDING_COST_BY_LEVEL[getLevel() - 1];
    }
    
    public static int getBuildingCost(int farmsCount) {
        return 3 + (farmsCount * 2);
    }

    public static int getFoodProductionByLevel(int level) {
        return FOOD_PRODUCTION_BY_LEVEL[level];
    }

    @Override
    public void setBaseBlock(Block baseBlock) {
        // TODO Auto-generated method stub
        super.setBaseBlock(baseBlock);
    }
}