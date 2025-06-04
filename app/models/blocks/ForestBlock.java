package models.blocks;

public class ForestBlock extends Block {
    public ForestBlock() {
        super();
    }

    @Override
    public boolean canBuildStructure() {
        return isAbsorbed();
    }

    @Override
    public int getResourceYield(String resourceType) {
        return 0;
    }


}