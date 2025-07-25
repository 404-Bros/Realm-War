package models.structures;

import models.Position;
import models.blocks.Block;

public class TownHall extends Structure {
    private static final int GOLD_PRODUCTION = 5;
    private static final int FOOD_PRODUCTION = 5;
    private static final int UNIT_SPACE = 5;

    
    public TownHall(Position position, int kingdomId) {
        super(1,1, 50, 0, position, kingdomId);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
        throw new UnsupportedOperationException("TownHall cannot be upgraded");
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }
    
    public int getGoldProduction() {
        return GOLD_PRODUCTION;
    }
    
    public int getFoodProduction() {
        return FOOD_PRODUCTION;
    }
    
    public int getUnitSpace() {
        return UNIT_SPACE;
    }

    @Override
    public int getUpgradeCostByLevel(int level) {
        return 0;
    }
}