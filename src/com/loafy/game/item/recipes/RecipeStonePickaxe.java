package com.loafy.game.item.recipes;

import com.loafy.game.entity.player.CraftingList;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Recipe;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.item.tool.StonePickaxe;

public class RecipeStonePickaxe extends Recipe {

    public RecipeStonePickaxe(PlayerInventory inventory, CraftingList list) {
        super(inventory, list);

        addIngredient(new ItemStack(new ItemSmallStone(), 25));
        addIngredient(new ItemStack(new ItemPlantFiber(), 8));
    }

    public ItemStack result() {
        return new ItemStack(new StonePickaxe(), 1);
    }
}
