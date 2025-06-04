package models.blocks;

public class VoidBlock extends Block {
    public VoidBlock() {
        super();
    }

    @Override
    public boolean canBuildStructure() {
        return false;
    }

    @Override
    public int getResourceYield(String resourceType) {
        return 0;
    }
}