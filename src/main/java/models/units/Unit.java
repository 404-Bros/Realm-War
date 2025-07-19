package models.units;

import models.Position;

public abstract class Unit {
    private int hitPoints;
    private int maxHitPoints;
    private int movementRange;
    private int attackPower;
    private int attackRange;
    private int paymentCost;
    private int rationCost;
    private int unitSpace;
    private int kingdomId;
    private Position position;
    private int moveCount;
    private boolean attakedByTower;
    private boolean canAttack;


    public Unit(int hitPoints, int movementRange, int attackPower, int attackRange, 
                int paymentCost, int rationCost, int unitSpace, int kingdomId, Position position) {
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.movementRange = movementRange;
        this.attackPower = attackPower;
        this.attackRange = attackRange;
        this.paymentCost = paymentCost;
        this.rationCost = rationCost;
        this.unitSpace = unitSpace;
        this.kingdomId = kingdomId;
        this.position = position;
        this.moveCount = 0;
        attakedByTower = false;
        canAttack=true;
    }

    public abstract boolean canMerge(Unit other);
    public abstract Unit merge(Unit other);

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public int getMaxHitPoints() { return maxHitPoints; }
    public int getMovementRange() { return movementRange; }
    public int getAttackPower() { return attackPower; }
    public int getAttackRange() { return attackRange; }
    public int getPaymentCost() { return paymentCost; }
    public int getRationCost() { return rationCost; }
    public int getUnitSpace() { return unitSpace; }
    public int getKingdomId() { return kingdomId; }
    public Position getPosition() { return position; }
    public boolean canMove() {
        return moveCount < movementRange;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isAttakedByTower() {
        return attakedByTower;
    }

    public void setAttakedByTower(boolean attakedByTower) {
        this.attakedByTower = attakedByTower;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}