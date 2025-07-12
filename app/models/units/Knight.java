package models.units;

import models.Position;

public class Knight extends Unit {
    public Knight(int kingdomId, Position position) {
        super(120, 3, 25, 2, 12, 6, 4, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return false;
    }

    @Override
    public Unit merge(Unit other) {
        throw new UnsupportedOperationException("Knights cannot be merged further");
    }
}