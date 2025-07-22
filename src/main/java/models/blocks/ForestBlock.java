package models.blocks;

import models.Position;

public class ForestBlock extends Block {
    public ForestBlock(Position position) {
        super(position);
    }

    @Override
    public boolean canBuildStructure() {
        return isAbsorbed();
    }

    @Override
    public int getResourceYield(String resourceType) {
        if (!isAbsorbed()) return 0;

        int resourceYield;
        switch (resourceType) {
            case "GOLD":
                resourceYield = 0;
                break;
            case "FOOD":
                resourceYield = 2;
                break;
            default:
                resourceYield = 0;
                break;
        }
        return resourceYield;

    }
    public static double getAttackBonus() {
        return 1.2;
    }
}