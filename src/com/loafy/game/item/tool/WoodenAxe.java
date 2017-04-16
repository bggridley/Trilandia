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
        addMaterial(Material.WOOD.getID(), 4f);
        addMaterial(Material.LEAF.getID(), 4f);
        addMaterial(Material.CHEST.getID(), 4f);
    }

    public String getName () {
        return "Wooden Axe";
    }

}
