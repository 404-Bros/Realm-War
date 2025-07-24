package models.structures;

import models.Position;
import models.blocks.Block;

import java.util.List;

public class Tower extends Structure {
    private static final int[] ATTACK_POWER_BY_LEVEL = {10, 15, 20};
    private static final int[] BUILDING_COST_BY_LEVEL = {10, 20, 30};
    private static final int[] DURABILITY_BY_LEVEL = {100, 150, 200};
    private List<Block> coveredBlock;
    private int attackPower;

    public Tower(Position position, Block baseBlock, int kingdomId,List<Block> coveredBlock) {
        super(3,4, DURABILITY_BY_LEVEL[0], 10, position, baseBlock, kingdomId);
        this.attackPower = ATTACK_POWER_BY_LEVEL[0];
        this.coveredBlock = coveredBlock;
    }

    @Override
    public boolean canUpgrade() {
        return getLevel() < getMaxLevel();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade()) {
            throw new IllegalStateException("Tower is already at max level");
        }

        setLevel(getLevel() + 1);
        setDurability(DURABILITY_BY_LEVEL[getLevel() - 1]);
        this.attackPower = ATTACK_POWER_BY_LEVEL[getLevel() - 1];
    }

    
    public int getAttackPower() {
        return attackPower;
    }
    @Override
    public int getUpgradeCost() {
        return BUILDING_COST_BY_LEVEL[getLevel() - 1];
    }

    public static int getBuildingCost(int towersCount) {
        return 10 + (towersCount * 10);
    }

    public List<Block> getCoveredBlock() {
        return coveredBlock;
    }

    public static int getAttackPowerByLevel(int level){
        return ATTACK_POWER_BY_LEVEL[level];
    }

    @Override
    public int getUpgradeCostByLevel(int level) {
        return BUILDING_COST_BY_LEVEL[level - 1];
    }
}
