package models.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;

import models.Position;
import models.structures.Structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoidBlock extends Block {
    public VoidBlock(@JsonProperty("position") Position position) {
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
