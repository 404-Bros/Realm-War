package models.units;

import models.Position;

public class Peasant extends Unit {
    public Peasant(int kingdomId, Position position) {
        super(30, 2, 5, 1, 2, 1, 1, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Peasant && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Spearman(this.getKingdomId(), this.getPosition());
    }
}