package models.blocks;

import models.Position;
import models.structures.Structure;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmptyBlock extends Block {
    public EmptyBlock(@JsonProperty("position") Position position) {
        super(position);
    }

    @Override
    public boolean canBuildStructure() {
        return isAbsorbed();
    }

    @Override
    public int getResourceYield(String resourceType) { //mean : daryaft manabe
        if (!isAbsorbed() || hasStructure())
            return 0;

        int resourceYield;
        switch (resourceType) {
            case "GOLD":
                resourceYield = 1;
                break;
            case "FOOD":
                resourceYield = 0;
                break;
            default:
                resourceYield = 0;
                break;
        }
        return resourceYield;
    }

}