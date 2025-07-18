package main.java.models.blocks;

import main.java.models.Position;

public class VoidBlock extends Block {
    public VoidBlock(Position position) {
        super(position);
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