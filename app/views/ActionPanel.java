package views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {
    private JButton endTurnButton;
    private JButton buildButton;
    private JButton recruitButton;
    private JButton moveButton;
    private JButton attackButton;
    private JButton updateStructure;
    private JButton mergeUnit;

    public ActionPanel() {
        setPreferredSize(new Dimension(800, 120));
        setLayout(null);
        setBackground(new Color(0xEDEAE6));

        endTurnButton = new JButton("End Turn");
        endTurnButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        endTurnButton.setFocusable(false);
        endTurnButton.setBackground(new Color(0xCA8D37));
        endTurnButton.setForeground(Color.WHITE);
        endTurnButton.setPreferredSize(new Dimension(100, 40));
        endTurnButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        endTurnButton.setIcon(new ImageIcon(getClass().getResource("../resources/endTurnIcon.png")));
        endTurnButton.setBounds(535,10,100,40);
        /// ////////////////////
        buildButton = new JButton("Build");
        buildButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buildButton.setFocusable(false);
        buildButton.setBackground(new Color(0xCA8D37));
        buildButton.setForeground(Color.WHITE);
        buildButton.setPreferredSize(new Dimension(100, 40));
        buildButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        buildButton.setIcon(new ImageIcon(getClass().getResource("../resources/buildIcon.png")));
        buildButton.setBounds(135,10,100,40);
        /// //////////////////////
        recruitButton = new JButton("Recruit");
        recruitButton.setFocusable(false);
        recruitButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        recruitButton.setBackground(new Color(0xCA8D37));
        recruitButton.setForeground(Color.WHITE);
        //recruitButton.setOpaque(true);
        recruitButton.setPreferredSize(new Dimension(120, 40));
        recruitButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        recruitButton.setIcon(new ImageIcon(getClass().getResource("../resources/recruitIcon.png")));
        recruitButton.setBounds(235,10,100,40);
        /// //////////
        moveButton = new JButton("Move");
        moveButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        moveButton.setFocusable(false);
        moveButton.setBackground(new Color(0xCA8D37));
        moveButton.setForeground(Color.WHITE);
        //moveButton.setOpaque(true);
        moveButton.setPreferredSize(new Dimension(100, 40));
        moveButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        moveButton.setIcon(new ImageIcon(getClass().getResource("../resources/moveIcon.png")));
        moveButton.setBounds(335,10,100,40);
        /// /////////////////////
        attackButton = new JButton("Attack");
        attackButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        attackButton.setFocusable(false);
        attackButton.setBackground(new Color(0xCA8D37));
        attackButton.setForeground(Color.WHITE);
        //attackButton.setOpaque(true);
        attackButton.setPreferredSize(new Dimension(100, 40));
        attackButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        attackButton.setIcon(new ImageIcon(getClass().getResource("../resources/attackIcon.png")));
        attackButton.setBounds(435,10,100,40);

        /// //////////////
        updateStructure = new JButton("Update Structure");
        updateStructure.setFocusable(false);
        updateStructure.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        updateStructure.setBackground(new Color(0xCA8D37));
        updateStructure.setForeground(Color.WHITE);
        updateStructure.setPreferredSize(new Dimension(120, 40));
        updateStructure.setFont(new Font("Tahoma", Font.PLAIN, 15));
        updateStructure.setIcon(new ImageIcon(getClass().getResource("../resources/upgrade.png")));
        updateStructure.setBounds(257,54,145,40);
        /// /////
        mergeUnit= new JButton("Merge Unit");
        mergeUnit.setFocusable(false);
        mergeUnit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        mergeUnit.setBackground(new Color(0xCA8D37));
        mergeUnit.setForeground(Color.WHITE);
        mergeUnit.setPreferredSize(new Dimension(120, 40));
        mergeUnit.setFont(new Font("Tahoma", Font.PLAIN, 15));
        mergeUnit.setIcon(new ImageIcon(getClass().getResource("../resources/Merge.png")));
        mergeUnit.setBounds(402,54,110,40);




        /// /////
        add(buildButton);
        add(recruitButton);
        add(moveButton);
        add(attackButton);
        add(endTurnButton);
        add(updateStructure);
        add(mergeUnit);
    }
    public void addBuildButtonAL(ActionListener al){
        buildButton.addActionListener(al);
    }
    public void addRecruitButtonAL(ActionListener al){
        recruitButton.addActionListener(al);
    }
    public void addMoveButtonAL(ActionListener al){
        moveButton.addActionListener(al);
    }
    public void addEndTurnButtonAL(ActionListener al){
        endTurnButton.addActionListener(al);
    }
    public void addAttackButtonAL(ActionListener al){
        attackButton.addActionListener(al);
    }
    public void addUpdateStructureAL(ActionListener al){
        updateStructure.addActionListener(al);
    }
    public void addMergeUnitAL(ActionListener al){
        mergeUnit.addActionListener(al);
    }

}