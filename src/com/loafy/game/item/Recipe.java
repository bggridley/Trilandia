package com.loafy.game.item;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.entity.player.PlayerInventory;

import java.util.ArrayList;

public abstract class Recipe {

    private ArrayList<ItemStack> ingredients;
    private PlayerInventory inventory;

    public Recipe(PlayerInventory inventory) {
        this.inventory = inventory;
        ingredients = new ArrayList<>();

        //inventory.addRecipe(this);
    }

    public abstract ItemStack result();

    public boolean canCraft() {
        int reqIngredients = ingredients.size();
        int curIngredients = 0;


        for (ItemStack ingredient : ingredients) {
            Item ingredientItem = ingredient.getItem();
            int ingredientAmount = ingredient.getAmount();
            int amountIngredients = 0;

          /*  for (ItemStack inventoryItemstack : inventory.getItems()) {
                if (inventoryItemstack.getItem().getID() == ingredientItem.getID()) {
                        amountIngredients+=inventoryItemstack.getAmount();
                }
            }*/

            if(amountIngredients >= ingredientAmount) {
                curIngredients++;
            }
        }

        return (curIngredients >= reqIngredients);
    }

    public void craft(EntityPlayer player) {
      /*  if(!canCraft()) return;

        if(inventory.getPicked().getItem().getID() != -1)
            player.dropPicked();

            ItemStack result = result();
            inventory.setPicked(result);

        for (ItemStack ingredient : ingredients) {
            inventory.subtractItem(ingredient.getItem(), -1, ingredient.getAmount());
        }*/
    }

    public void addIngredient(ItemStack stack) {
        ingredients.add(stack);
    }

}
