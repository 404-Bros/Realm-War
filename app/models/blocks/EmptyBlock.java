package models.blocks;

public class EmptyBlock extends Block {
    public EmptyBlock() {
        super();
    }

    @Override
    public boolean canBuildStructure() {
        return isAbsorbed();
    }

    @Override
    public int getResourceYield(String resourceType) { //mean : daryaft manabe
        return 0;
    }
}