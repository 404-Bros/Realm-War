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

    public ActionPanel() {
        setPreferredSize(new Dimension(800, 100));
        setLayout(null);
        setBackground(new Color(225, 212, 193));

        //GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(5, 5, 5, 5);

        endTurnButton = new JButton("End Turn");
        endTurnButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        endTurnButton.setFocusable(false);
        endTurnButton.setBackground(new Color(0xCA8D37));
        endTurnButton.setForeground(Color.WHITE);
        //endTurnButton.setOpaque(true);
        endTurnButton.setPreferredSize(new Dimension(100, 40));
        endTurnButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        endTurnButton.setIcon(new ImageIcon(getClass().getResource("../resources/endTurnIcon.png")));
        endTurnButton.setBounds(555,10,100,40);
        /// ////////////////////
        buildButton = new JButton("Build");
        buildButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buildButton.setFocusable(false);
        buildButton.setBackground(new Color(0xCA8D37));
        buildButton.setForeground(Color.WHITE);
        //buildButton.setOpaque(true);
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
        recruitButton.setBounds(235,10,120,40);
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
        moveButton.setBounds(355,10,100,40);
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
        attackButton.setBounds(455,10,100,40);


        /// /////
        //gbc.gridx = 0;
        add(buildButton);
        //gbc.gridx = 1;
        add(recruitButton);
        //gbc.gridx = 2;
        add(moveButton);
        //gbc.gridx = 3;
        add(attackButton);
        //gbc.gridx = 4;
        add(endTurnButton);


        endTurnButton.addActionListener(e -> {

        });

        buildButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Build menu opened.");
        });

        recruitButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Recruit menu opened.");
        });

        moveButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Move mode enabled.");
        });

        attackButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Attack mode enabled.");
        });
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

}