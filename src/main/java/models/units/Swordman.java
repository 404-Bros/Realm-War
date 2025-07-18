package main.java.models.units;

import main.java.models.Position;

public class Swordman extends Unit {
    private static int hitPoints=80;
    private static int attackPower=15;
    private static int paymentCost=8;
    private static int rationCost=4;
    public Swordman(int kingdomId, Position position) {
        super(80, 2, 15, 1, 8, 4, 3, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Swordman && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (this.getHitPoints() != 80 || other.getHitPoints() !=80) {
            throw new IllegalStateException("Cannot merge these units");
        }
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Knight(this.getKingdomId(), this.getPosition());
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