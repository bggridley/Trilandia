package com.loafy.game.item.recipes;

import com.loafy.game.entity.player.CraftingList;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Recipe;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.world.block.Material;

public class RecipeWoodWall extends Recipe {

    public RecipeWoodWall(PlayerInventory inventory, CraftingList list) {
        super(inventory, list);

        addIngredient(new ItemStack(new ItemWood(), 1));
    }

    public ItemStack result() {
        return new ItemStack(new ItemBlock(Material.WOOD_WALL), 6);
    }
}
