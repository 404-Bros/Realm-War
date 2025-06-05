package models.units;

public class Knight extends Unit {
    public Knight(int kingdomId) {
        super(120, 3, 25, 1, 12, 6, 4, kingdomId);
    }

    @Override
    public boolean canMerge(Unit other) {}

    @Override
    public Unit merge(Unit other) {}
}