package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class WoodenShovel extends Tool {

    public WoodenShovel() {
        super();
        this.id = Item.WOODEN_SHOVEL;
    }

    public void initMaterials() {
        addMaterial(Material.GRASS.getID(), 4f);
        addMaterial(Material.DIRT.getID(), 4f);
    }

    public String getName() {
        return "Wooden Shovel";
    }
}
