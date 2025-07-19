package models.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;

import models.Position;
import models.structures.Structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForestBlock extends Block {
    public ForestBlock(@JsonProperty("position") Position position) {
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

}