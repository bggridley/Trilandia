package com.loafy.game.item.recipes;

import com.loafy.game.entity.player.CraftingList;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Recipe;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.item.tool.WoodenPickaxe;

public class RecipeWoodenPickaxe extends Recipe {

    public RecipeWoodenPickaxe(PlayerInventory inventory, CraftingList list) {
        super(inventory, list);

        addIngredient(new ItemStack(new ItemWood(), 20));
        addIngredient(new ItemStack(new ItemPlantFiber(), 8));
    }

    public ItemStack result() {
        return new ItemStack(new WoodenPickaxe(), 1);
    }
}
