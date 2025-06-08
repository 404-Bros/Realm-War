package models.units;

import models.Position;

public class Swordman extends Unit {
    public Swordman(int kingdomId, Position position) {
        super(80, 2, 15, 1, 8, 4, 3, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Swordman && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Knight(this.getKingdomId(), this.getPosition());
    }
}