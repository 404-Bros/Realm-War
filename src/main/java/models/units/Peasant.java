package main.java.models.units;

import main.java.models.Position;

public class Peasant extends Unit {
    private static int hitPoints=30;
    private static int attackPower=1;
    private static int paymentCost=2;
    private static int rationCost=1;
    public Peasant(int kingdomId, Position position) {
        super(30, 2, 5, 1, 2, 1, 1, kingdomId, position);

    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Peasant && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (this.getHitPoints() != 30 || other.getHitPoints() !=30) {
            throw new IllegalStateException("Cannot merge these units");
        }
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Spearman(this.getKingdomId(), this.getPosition());
    }
    public static int getStaticHitPoints() {
        return hitPoints;
    }


    public static int getStaticAttackPower() {
        return attackPower;
    }


    public static int getStaticPaymentCost() {
        return paymentCost;
    }


    public static int getStaticRationCost() {
        return rationCost;
    }
}