package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class StonePickaxe extends Tool {

    public StonePickaxe() {
        super();
        this.id = Item.STONE_PICKAXE;
    }

    public void initMaterials() {
        addMaterial(Material.STONE.getID(), 13f);
    }

    public String getName() {
        return "Stone Pickaxe";
    }

}
