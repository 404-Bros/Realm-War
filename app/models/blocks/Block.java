package models.blocks;

public abstract class Block {
    private boolean isAbsorbed;
    private int kingdomId; // -1 if not absorbed

    public Block() {
        this.isAbsorbed = false;
        this.kingdomId = -1;
    }

    public abstract boolean canBuildStructure();
    public abstract int getResourceYield(String resourceType);

    public boolean isAbsorbed() {
        return isAbsorbed;
    }
    public int getKingdomId() {
        return kingdomId;
    }
    public void setAbsorbed(boolean absorbed, int kingdomId) {
        this.isAbsorbed = absorbed;
        this.kingdomId = kingdomId;
    }
}