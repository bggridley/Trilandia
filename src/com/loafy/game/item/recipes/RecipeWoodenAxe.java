package com.loafy.game.item.recipes;

import com.loafy.game.item.ItemStack;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.item.Recipe;
import com.loafy.game.item.tool.WoodenAxe;

public class RecipeWoodenAxe extends Recipe {

    public RecipeWoodenAxe(PlayerInventory inventory) {
        super(inventory);

        addIngredient(new ItemStack(new ItemWood(), 15));
        addIngredient(new ItemStack(new ItemPlantFiber(),  6));
    }

    public ItemStack result() {
        return new ItemStack(new WoodenAxe(), 1);
    }
}
