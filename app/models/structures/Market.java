package models.structures;

import models.Position;
import models.blocks.Block;

public class Market extends Structure {
    private int goldProduction;

    public Market(int goldProduction, int maxLevel, int initialDurability, int maintenanceCost, Position position, Block baseBlock, int kingdomId) {
        super(maxLevel, initialDurability, maintenanceCost, position, baseBlock, kingdomId);
        this.goldProduction = goldProduction;
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
    public void upgrade() {}
}
