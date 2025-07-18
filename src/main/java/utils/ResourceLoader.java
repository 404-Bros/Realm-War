package utils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
    private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();private Map<String, ImageIcon> structureIcons = new HashMap<String, ImageIcon>();
    public ResourceLoader() {
        icons.put("knight-p1F", new ImageIcon(getClass().getResource("/Knight-p1F.png")));
        icons.put("knight-p1E", new ImageIcon(getClass().getResource("/Knight-p1E.png")));
        icons.put("knight-p2F", new ImageIcon(getClass().getResource("/Knight-p2F.png")));
        icons.put("knight-p2E", new ImageIcon(getClass().getResource("/Knight-p2E.png")));
        icons.put("swordman-p1F", new ImageIcon(getClass().getResource("/Swordman-p1F.png")));
        icons.put("swordman-p1E", new ImageIcon(getClass().getResource("/Swordman-p1E.png")));
        icons.put("swordman-p2F", new ImageIcon(getClass().getResource("/Swordman-p2F.png")));
        icons.put("swordman-p2E", new ImageIcon(getClass().getResource("/Swordman-p2E.png")));
        icons.put("spearman-p1F", new ImageIcon(getClass().getResource("/Spearman-p1F.png")));
        icons.put("spearman-p1E", new ImageIcon(getClass().getResource("/Spearman-p1E.png")));
        icons.put("spearman-p2F", new ImageIcon(getClass().getResource("/Spearman-p2F.png")));
        icons.put("spearman-p2E", new ImageIcon(getClass().getResource("/Spearman-p2E.png")));
        icons.put("peasant-p1F", new ImageIcon(getClass().getResource("/Peasant-p1F.png")));
        icons.put("peasant-p1E", new ImageIcon(getClass().getResource("/Peasant-p1E.png")));
        icons.put("peasant-p2F", new ImageIcon(getClass().getResource("/Peasant-p2F.png")));
        icons.put("peasant-p2E", new ImageIcon(getClass().getResource("/Peasant-p2E.png")));
        /// ///////////////////////////////////////////
        icons.put("farm", new ImageIcon(getClass().getResource("/farm.png")));
        icons.put("market", new ImageIcon(getClass().getResource("/market.png")));
        icons.put("tower", new ImageIcon(getClass().getResource("/tower.png")));
        icons.put("barrack", new ImageIcon(getClass().getResource("/barrack.png")));
        icons.put("townHall-p1", new ImageIcon(getClass().getResource("/townhall-p1.png")));
        icons.put("townHall-p2", new ImageIcon(getClass().getResource("/townhall-p2.png")));
        /// //////////////////////////////////
        icons.put("emptyBlock", new ImageIcon(getClass().getResource("/EmptyBlock.png")));
        icons.put("forestBlock", new ImageIcon(getClass().getResource("/ForestBlock.png")));
        icons.put("voidBlock", new ImageIcon(getClass().getResource("/voidBlock.png")));


    }
    public Map<String,ImageIcon> geticons() {
        return icons;
    }

}
