package models.units;

public class Peasant extends Unit {
    public Peasant(int kingdomId) {
        super(30, 2, 5, 1, 2, 1, 1, kingdomId);
    }

    @Override
    public boolean canMerge(Unit other) {}

    @Override
    public Unit merge(Unit other) {}
}