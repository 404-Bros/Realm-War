package models.structures;

import com.fasterxml.jackson.annotation.JsonProperty;

import models.Position;
import models.blocks.Block;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TownHall extends Structure {
    @JsonIgnore
    private static final int GOLD_PRODUCTION = 5;
    @JsonIgnore
    private static final int FOOD_PRODUCTION = 5;
    @JsonIgnore
    private static final int UNIT_SPACE = 5;

    
    public TownHall(@JsonProperty("position") Position position,@JsonProperty("kingdomId") int kingdomId) {
        super(1,1, 50, 0, position, kingdomId);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
        throw new UnsupportedOperationException("TownHall cannot be upgraded");
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }
    
    public int getGoldProduction() {
        return GOLD_PRODUCTION;
    }
    
    public int getFoodProduction() {
        return FOOD_PRODUCTION;
    }
    
    public int getUnitSpace() {
        return UNIT_SPACE;
    }

    @Override
    public void setBaseBlock(Block baseBlock) {
        // TODO Auto-generated method stub
        super.setBaseBlock(baseBlock);
    }

    
}