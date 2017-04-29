package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class WoodenAxe extends Tool {

    public WoodenAxe() {
        super();
        this.id = Item.WOODEN_AXE;
    }

    public void initMaterials() {
        addMaterial(Material.LOG.getID(), 20f);
        addMaterial(Material.WOOD.getID(), 20f);
        addMaterial(Material.LEAF.getID(), 20f);
        addMaterial(Material.CHEST.getID(), 20f);
    }

    public String getName () {
        return "Wooden Axe";
    }

}
