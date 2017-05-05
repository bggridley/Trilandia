package com.loafy.game.entity.player;

import com.loafy.game.input.InputManager;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.container.Inventory;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.item.recipes.*;
import com.loafy.game.item.tool.WoodenAxe;
import com.loafy.game.item.tool.WoodenPickaxe;
import com.loafy.game.item.tool.WoodenShovel;
import com.loafy.game.world.block.Material;

public class PlayerInventory extends Inventory {

    private boolean open;
    private int hotbarSlot;
    private CraftingList craftingList;

    public PlayerInventory(EntityPlayer player, int slots) {
        super(slots);

        this.craftingList = new CraftingList(player);


        new RecipeStone(this, craftingList);
        new RecipeStoneAxe(this, craftingList);
        new RecipeStonePickaxe(this, craftingList);
        new RecipeStoneShovel(this, craftingList);
        new RecipeWoodenAxe(this, craftingList);
        new RecipeWoodenPickaxe(this, craftingList);
        new RecipeWoodenShovel(this, craftingList);
        new RecipeWood(this, craftingList);
        new RecipeWoodWall(this, craftingList);

        //this.slots[15].setItemStack(new ItemStack(new ItemPlantFiber(), 21));

        this.slots.get(0).setItemStack(new ItemStack(new WoodenAxe(), 1));
        this.slots.get(1).setItemStack(new ItemStack(new WoodenPickaxe(), 1));
        this.slots.get(2).setItemStack(new ItemStack(new WoodenShovel(), 1));
        this.slots.get(3).setItemStack(new ItemStack(new ItemBlock(Material.CHEST), 15));
        this.slots.get(4).setItemStack(new ItemStack(new ItemWood(), 99));
        this.slots.get(5).setItemStack(new ItemStack(new ItemPlantFiber(), 15));
        this.slots.get(6).setItemStack(new ItemStack(new ItemBlock(Material.TORCH), 100));

        inventoryChange();
    }

    public void render(EntityPlayer player) {
        super.render(player);

        craftingList.render(player);
    }

    public void update(EntityPlayer player) {
        super.update(player);
        craftingList.update(player);

        int key = InputManager.keyPressed();

        if (!open) {
            for (int i = 2; i < 12; i++) {
                if (key == i) {
                    hotbarSlot = i - 2;
                }
            }

            int dwheel = InputManager.mouseWd / 120;

            hotbarSlot -= dwheel;

            if (hotbarSlot > GRID_WIDTH - 1)
                hotbarSlot = 0;

            if (hotbarSlot < 0)
                hotbarSlot = GRID_WIDTH - 1;
        }

        slots.get(hotbarSlot).setSelected(true);


    }

    public void openSlots() {
        super.openSlots();
        craftingList.openSlots();
    }

    public void closeSlots() {
        super.closeSlots();
        craftingList.closeSlots();
    }

    public void inventoryChange() {
        craftingList.updateSlots();
    }

    public CraftingList getCraftingList() {
        return craftingList;
    }

    public int getHotbarSlot() {
        return hotbarSlot;
    }

    public void setHotbarSlot(int hotbarSlot) {
        this.hotbarSlot = hotbarSlot;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
