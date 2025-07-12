package views;

import javax.swing.*;
import java.awt.*;
import models.blocks.Block;
import models.structures.*;

public class BlockPanel extends JPanel {
    private JLabel blockTypeLabel;
    private JLabel resourceLabel;
    private JLabel structureLabel;
    private JLabel unitLabel;
    private JLabel durabilityLabel;
    private JLabel maintenanceCostLabel;
    private JLabel structureLevelLabel;
    private JLabel towerAttackPower;
    private JLabel unitHPLabel;
    private JLabel unitMovementRangeLabel;
    private JLabel unitAttackPower;
    private JLabel unitePaymentCostLabel;
    private JLabel unitAttackRangeLabel;
    private JLabel unitRotationLabel;
    private JLabel unitSpaceLabel;
    private boolean isDarkMode = false;
    private JLabel isAbsorbedLabel;
    private JLabel kingdomIDLabel;

    public BlockPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 200));
        setBackground(new Color(0xEDEAE6));





        blockTypeLabel = new JLabel("Block Type: ");
        //blockTypeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        resourceLabel = new JLabel("Resources: ");
        //resourceLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        structureLabel = new JLabel("Structure: ");
        //structureLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        unitLabel = new JLabel("Unit: ");
        //unitLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));



        add(blockTypeLabel);
        add(resourceLabel);
        add(structureLabel);
        add(unitLabel);

        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }

    public void updateBlockInfo(Block block,boolean isDarkMode) {


        blockTypeLabel.setText("Block Type: " + block.getClass().getSimpleName());


        if (isDarkMode){
            blockTypeLabel.setForeground(Color.white);
            structureLabel.setForeground(Color.white);
            unitLabel.setForeground(Color.white);
            resourceLabel.setForeground(Color.white);
            structureLabel.setForeground(Color.white);
            setBackground(new Color(0x1A2B44));
        }



        if (block.hasStructure()) {

            if (block.getStructure() instanceof TownHall){
                resourceLabel.setText("Resources: "+((TownHall) block.getStructure()).getGoldProduction()+" Gold"+","+((TownHall) block.getStructure()).getFoodProduction()+" Food");
            }
            else {
                if (block.getStructure() instanceof Market){
                    resourceLabel.setText("Resources: "+((Market) block.getStructure()).getGoldProduction()+"Gold");
                }
                else if (block.getStructure() instanceof Farm){
                    resourceLabel.setText("Resources: "+((Farm) block.getStructure()).getFoodProduction()+"Food");
                }
            }





            if (structureLevelLabel == null) {
                durabilityLabel = new JLabel();
                maintenanceCostLabel = new JLabel();
                structureLevelLabel = new JLabel();
                towerAttackPower=new JLabel();
            }

            structureLabel.setText("Structure: " + block.getStructure().getClass().getSimpleName());
            structureLevelLabel.setText("Structure level: " + block.getStructure().getLevel());
            durabilityLabel.setText("Structure HP: " + block.getStructure().getDurability());
            maintenanceCostLabel.setText("Maintenance cost: " + block.getStructure().getMaintenanceCost());

            if (isDarkMode){
                structureLevelLabel.setForeground(Color.white);
                durabilityLabel.setForeground(Color.white);
                maintenanceCostLabel.setForeground(Color.white);
            }

            add(structureLevelLabel);
            add(durabilityLabel);
            add(maintenanceCostLabel);
            if (block.getStructure() instanceof Tower){
                towerAttackPower.setText("Tower Attack power: "+((Tower) block.getStructure()).getAttackPower());
                if (isDarkMode){
                    towerAttackPower.setForeground(Color.white);
                }
                add(towerAttackPower);
            }



        }
        else {
            resourceLabel.setText("Resources: " + block.getResourceYield("GOLD") + " Gold" + ","+block.getResourceYield("FOOD")+" Food");
            if (structureLevelLabel!=null) {
                remove(structureLevelLabel);
                remove(durabilityLabel);
                remove(maintenanceCostLabel);
            }
            structureLabel.setText("Structure: None");

        }

        if (block.hasUnit()) {

            if (unitHPLabel == null) {
                unitHPLabel = new JLabel();
                unitMovementRangeLabel = new JLabel();
                unitAttackPower=new JLabel();
                unitePaymentCostLabel = new JLabel();
                unitRotationLabel = new JLabel();
                unitSpaceLabel = new JLabel();
                unitAttackRangeLabel=new JLabel();
            }

            unitLabel.setText("Unit: " + block.getUnit().getClass().getSimpleName());
            unitHPLabel.setText("Unit HP: "+block.getUnit().getHitPoints());
            unitMovementRangeLabel.setText("Unit Movement Range: "+block.getUnit().getMovementRange());
            unitAttackPower.setText("Unit Attack Power: "+block.getUnit().getAttackPower());
            unitAttackRangeLabel.setText("Unit Attack Range: "+block.getUnit().getAttackRange());
            unitePaymentCostLabel.setText("Unite Payment Cost: "+block.getUnit().getPaymentCost());
            unitRotationLabel.setText("Unit Rotation Cost: "+block.getUnit().getRationCost());
            unitSpaceLabel.setText("Unit Space: "+block.getUnit().getUnitSpace());

            if (isDarkMode){
                unitHPLabel.setForeground(Color.white);
                unitMovementRangeLabel.setForeground(Color.white);
                unitAttackPower.setForeground(Color.white);
                unitePaymentCostLabel.setForeground(Color.white);
                unitRotationLabel.setForeground(Color.white);
                unitSpaceLabel.setForeground(Color.white);
                unitAttackRangeLabel.setForeground(Color.white);
            }

            add(unitHPLabel);
            add(unitMovementRangeLabel);
            add(unitAttackPower);
            add(unitAttackRangeLabel);
            add(unitePaymentCostLabel);
            add(unitRotationLabel);
            add(unitSpaceLabel);

        } else {

            if (unitHPLabel!=null) {
                remove(unitHPLabel);
                remove(unitMovementRangeLabel);
                remove(unitAttackPower);
                remove(unitAttackRangeLabel);
                remove(unitePaymentCostLabel);
                remove(unitRotationLabel);
                remove(unitSpaceLabel);
            }
            unitLabel.setText("Unit: None");
        }
    }

    public JLabel getResourceLabel() {
        return resourceLabel;
    }

    public JLabel getBlockTypeLabel() {
        return blockTypeLabel;
    }

    public JLabel getStructureLabel() {
        return structureLabel;
    }

    public JLabel getUnitLabel() {
        return unitLabel;
    }

    public JLabel getDurabilityLabel() {
        return durabilityLabel;
    }

    public JLabel getMaintenanceCostLabel() {
        return maintenanceCostLabel;
    }

    public JLabel getStructureLevelLabel() {
        return structureLevelLabel;
    }

    public JLabel getTowerAttackPower() {
        return towerAttackPower;
    }

    public JLabel getUnitHPLabel() {
        return unitHPLabel;
    }

    public JLabel getUnitMovementRangeLabel() {
        return unitMovementRangeLabel;
    }

    public JLabel getUnitAttackPower() {
        return unitAttackPower;
    }

    public JLabel getUnitePaymentCostLabel() {
        return unitePaymentCostLabel;
    }

    public JLabel getUnitAttackRangeLabel() {
        return unitAttackRangeLabel;
    }

    public JLabel getUnitRotationLabel() {
        return unitRotationLabel;
    }

    public JLabel getUnitSpaceLabel() {
        return unitSpaceLabel;
    }
}
