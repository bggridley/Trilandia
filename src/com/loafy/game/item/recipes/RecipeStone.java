package com.loafy.game.item.recipes;

import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Recipe;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.world.block.Material;

public class RecipeStone extends Recipe {

    public RecipeStone(PlayerInventory inventory) {
        super(inventory);

        addIngredient(new ItemStack(new ItemSmallStone(), 2));
    }

    public ItemStack result() {
        return new ItemStack(new ItemBlock(Material.STONE), 1);
    }
}
