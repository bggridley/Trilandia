package com.loafy.game.item.tool;

import com.loafy.game.item.Item;
import com.loafy.game.item.Tool;
import com.loafy.game.world.block.Material;

public class WoodenPickaxe extends Tool {

    public WoodenPickaxe() {
        super();
        this.id = Item.WOODEN_PICKAXE;
    }

    public void initMaterials() {
        addMaterial(Material.STONE.getID(), 1000f); //8f
        addMaterial(Material.CERISE_STONE.getID(), 1000f); //8f
        addMaterial(Material.COPPER_ORE.getID(), 1000f);
        addMaterial(Material.SILVER_ORE.getID(), 1000f);
    }

    public String getName() {
        return "Wooden Pickaxe";
    }

}
