package com.loafy.game.entity.player;

import com.loafy.game.item.Recipe;
import com.loafy.game.item.container.Container;
import com.loafy.game.item.container.ContainerSlot;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

public class CraftingList extends Container {

    private ArrayList<Recipe> recipes;
    private EntityPlayer player;


    public CraftingList(EntityPlayer player) {
        super(200, Display.getHeight() - 200);
        this.player = player;
        this.recipes = new ArrayList<>();
    }

    public void updateSlots() {
        slots.clear();

        int s = 0;
        for (Recipe recipe : recipes) {

            if (recipe.canCraft(player)) {

                ContainerSlot slot = new ContainerSlot(200 + (s * (ContainerSlot.SIZE + Container.GRID_SPACING)), Display.getHeight() - 200) {

                    public boolean action() {

                        recipe.craft(player);
                        return true;
                    }

                };

                slot.setItemStack(recipe.result());
                slot.setGrid(false);
                if (player.getInventory() != null && player.getInventory().isOpen())
                    slot.setActive(true);
                slots.add(slot);

                s++;
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
