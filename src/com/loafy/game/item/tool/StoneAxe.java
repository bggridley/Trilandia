package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class StoneAxe extends Tool {

    public StoneAxe() {
        super();
        this.id = Item.STONE_AXE;
    }

    public void initMaterials() {
        addMaterial(Material.LOG.getID(), 8f);
        addMaterial(Material.LEAF.getID(), 8f);
        addMaterial(Material.CHEST.getID(), 8f);
    }

    public String getName () {
        return "Stone Axe";
    }

}
