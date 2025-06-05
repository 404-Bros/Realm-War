package models.units;

public class Spearman extends Unit {
    public Spearman(int kingdomId) {
        super(50, 2, 10, 1, 4, 2, 2, kingdomId);
    }

    @Override
    public boolean canMerge(Unit other) {}

    @Override
    public Unit merge(Unit other) {}
}