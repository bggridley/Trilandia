package com.loafy.game.item;

import com.loafy.game.entity.player.CraftingList;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.entity.player.PlayerInventory;
import com.loafy.game.item.container.ContainerSlot;

import java.util.ArrayList;

public abstract class Recipe {

    private ArrayList<ItemStack> ingredients;
    private PlayerInventory inventory;

    public Recipe(PlayerInventory inventory, CraftingList craftingList) {
        ingredients = new ArrayList<>();
        this.inventory = inventory;

        craftingList.addRecipe(this);
    }

    public abstract ItemStack result();

    public boolean canCraft(EntityPlayer player) {
        int reqIngredients = ingredients.size();
        int curIngredients = 0;


        for (ItemStack ingredient : ingredients) {
            Item ingredientItem = ingredient.getItem();
            int ingredientAmount = ingredient.getAmount();
            int amountIngredients = 0;

            for (ContainerSlot slot : inventory.getSlots()) {
                ItemStack inventoryItemstack = slot.getItemStack();
                if (inventoryItemstack.getItem().getID() == ingredientItem.getID()) {
                    amountIngredients += inventoryItemstack.getAmount();
                }
            }

            if (amountIngredients >= ingredientAmount) {
                curIngredients++;
            }
        }


        return (curIngredients >= reqIngredients);
    }

    public void craft(EntityPlayer player) {
        if (!canCraft(player)) return;

        ItemStack selectedItem = player.getSelectedItem();
        ItemStack result = result();



        if(result.getItem().getID() != selectedItem.getItem().getID()) {
            // not same ids

            player.dropPicked();
            player.setSelectedItem(result);
        } else {
            if(selectedItem.getAmount() + result.getAmount() <= result.getMaxStackSize()) {
                result.setAmount(selectedItem.getAmount() + result.getAmount());
                player.setSelectedItem(result);
            } else {
                return;
            }
        }


        for (ItemStack ingredient : ingredients) {
            inventory.subtractItem(ingredient.getItem(), -1, ingredient.getAmount());
        }

    }

    public void addIngredient(ItemStack stack) {
        ingredients.add(stack);
    }

}
