package views;

import models.Position;
import models.blocks.Block;
import models.structures.Structure;
import models.units.Unit;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockButton extends JButton {
    private Block block;
    private Structure structure;
    private Unit unit;
    private ImageIcon icon;
    private Position position;
    private Border originalBorder;

    public BlockButton(ImageIcon icon, Block block) {
        this.block = block;
        this.structure = block.getStructure();
        this.icon = icon;
        this.position = block.getPosition();

        if(block.isAbsorbed()){
            if (block.getKingdomId()==1){
                setBorder(BorderFactory.createLineBorder(Color.RED,3));
            }
            else
                setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        }
        /// /////////
        setPreferredSize(new Dimension(50, 50));

        setContentAreaFilled(false);

        originalBorder = getBorder();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(originalBorder);
            }
        });

    }
    public BlockButton(ImageIcon icon, Block block, Border border) {
        this.block = block;
        this.structure = block.getStructure();
        this.icon = icon;
        this.position = block.getPosition();


        setBorder(border);
        setPreferredSize(new Dimension(50, 50));

        if(block.isAbsorbed()){
            if (block.getKingdomId()==1){
                setBorder(BorderFactory.createLineBorder(Color.RED,3));
            }
            else
                setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        }
        setContentAreaFilled(false);

        originalBorder = getBorder();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(originalBorder);
            }
        });
    }
    public void setBorder(){
        if (block.getKingdomId()==1){
            setBorder(BorderFactory.createLineBorder(Color.RED,3));
        }
        else
            setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
