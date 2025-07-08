package utils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
    private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();private Map<String, ImageIcon> structureIcons = new HashMap<String, ImageIcon>();
    public ResourceLoader() {
        icons.put("knight-p1", new ImageIcon(getClass().getResource("../resources/Knight-p1.png")));
        icons.put("knight-p2", new ImageIcon(getClass().getResource("../resources/Knight-p2.png")));
        icons.put("swordman-p1", new ImageIcon(getClass().getResource("../resources/Swordman-p1.png")));
        icons.put("swordman-p2", new ImageIcon(getClass().getResource("../resources/Swordman-p2.png")));
        icons.put("spearman-p1", new ImageIcon(getClass().getResource("../resources/Spearman-p1.png")));
        icons.put("spearman-p2", new ImageIcon(getClass().getResource("../resources/Spearman-p2.png")));
        icons.put("peasant-p1", new ImageIcon(getClass().getResource("../resources/Peasant-p1.png")));
        icons.put("peasant-p2", new ImageIcon(getClass().getResource("../resources/Peasant-p2.png")));
        /// ///////////////////////////////////////////
        icons.put("farm", new ImageIcon(getClass().getResource("../resources/farm.png")));
        icons.put("market", new ImageIcon(getClass().getResource("../resources/market.png")));
        icons.put("tower", new ImageIcon(getClass().getResource("../resources/tower.png")));
        icons.put("barrack", new ImageIcon(getClass().getResource("../resources/barrack.png")));
        icons.put("townHall-p1", new ImageIcon(getClass().getResource("../resources/townhall-p1.png")));
        icons.put("townHall-p2", new ImageIcon(getClass().getResource("../resources/townhall-p2.png")));
        /// //////////////////////////////////
        icons.put("emptyBlock", new ImageIcon(getClass().getResource("../resources/EmptyBlock.png")));
        icons.put("forestBlock", new ImageIcon(getClass().getResource("../resources/ForestBlock.png")));
        icons.put("voidBlock", new ImageIcon(getClass().getResource("../resources/voidBlock.png")));


    }
    public Map<String,ImageIcon> geticons() {
        return icons;
    }

}
