package views;

import models.Position;
import models.blocks.Block;
import models.structures.Structure;
import models.units.Unit;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockButton extends JButton {
    private Block block;
    private ImageIcon icon;
    private Position position;
    private Border kingdomBorder;
    private BlockPanel blockPanel;
    private Border kingdomCompoundBorder;

    public BlockButton(ImageIcon icon, Block block) {
        this.block = block;
        this.icon = icon;
        this.position = block.getPosition();
        blockPanel = new BlockPanel();
        blockPanel.setBounds(0,190,200,410);
        /// /////////
        setPreferredSize(new Dimension(50, 50));

        setContentAreaFilled(false);

        setBorder();
    }

    public void setBorder(){

        Border defaultBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);

        if(block.isAbsorbed()){
            if (block.getKingdomId()==1){
                kingdomBorder=BorderFactory.createLineBorder(Color.RED,3);
                kingdomCompoundBorder=BorderFactory.createCompoundBorder(kingdomBorder, defaultBorder);
                setBorder(kingdomCompoundBorder);
            }
            else {
                kingdomBorder = BorderFactory.createLineBorder(Color.BLUE, 3);
                kingdomCompoundBorder=BorderFactory.createCompoundBorder(kingdomBorder, defaultBorder);
                setBorder(kingdomCompoundBorder);
            }
        }
        else {
            setBorder(defaultBorder);
        }
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

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    public Border getKingdomCompoundBorder() {
        return kingdomCompoundBorder;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public BlockPanel getBlockPanel() {
        return blockPanel;
    }
}
