package models.blocks;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import models.Position;
import models.structures.Structure;
import models.units.Unit;
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = VoidBlock.class, name = "voidBlock"),
    @JsonSubTypes.Type(value = EmptyBlock.class, name = "emptyBlock"),
    @JsonSubTypes.Type(value = VoidBlock.class, name = "voidBlock")
})
public abstract class Block {
    private Position position;
    private boolean isAbsorbed;
    private int kingdomId; // -1 if not absorbed
    @JsonBackReference
    private Structure structure;
    private Unit unit;

    public Block(Position position) {
        this.position = position;
        this.isAbsorbed = false;
        this.kingdomId = -1;
    }

    public abstract boolean canBuildStructure();
    public abstract int getResourceYield(String resourceType);

    public Position getPosition() {
        return position;
    }
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

    public boolean hasStructure() {
        return structure != null;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public boolean hasUnit() {
        return unit != null;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}