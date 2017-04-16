package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class StoneShovel extends Tool {

    public StoneShovel () {
        super();
        this.id = Item.STONE_SHOVEL;
    }

    public void initMaterials() {
        addMaterial(Material.GRASS.getID(), 8f);
        addMaterial(Material.DIRT.getID(), 8f);
    }

    public String getName() {
        return "Stone Shovel";
    }
}
