package models.structures;

import models.blocks.Block;

public class TownHall extends Structure {
    private static final int GOLD_PRODUCTION = 5;
    private static final int FOOD_PRODUCTION = 5;
    private static final int UNIT_SPACE = 5;

    public TownHall(Block baseBlock, int kingdomId) {
        super(1, 50, 0, baseBlock, kingdomId);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {}

    @Override
    public void performTurnAction() {}
    
    public int getGoldProduction() {
        return GOLD_PRODUCTION;
    }
    
    public int getFoodProduction() {
        return FOOD_PRODUCTION;
    }
    
    public int getUnitSpace() {
        return UNIT_SPACE;
    }
}