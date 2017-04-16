package com.loafy.game.item.recipes;

import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Recipe;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.item.tool.StoneAxe;

public class RecipeStoneAxe extends Recipe {

    public RecipeStoneAxe(PlayerInventory inventory) {
        super(inventory);

        addIngredient(new ItemStack(new ItemSmallStone(), 25));
        addIngredient(new ItemStack(new ItemPlantFiber(),  6));
    }

    public ItemStack result() {
        return new ItemStack(new StoneAxe(), 1);
    }
}
