package models.units;

import models.Position;

public class Spearman extends Unit {
    public Spearman(int kingdomId, Position position) {
        super(50, 2, 10, 1, 4, 2, 2, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Spearman && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Swordman(this.getKingdomId(), this.getPosition());
    }
}