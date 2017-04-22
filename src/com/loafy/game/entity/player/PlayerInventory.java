package com.loafy.game.entity.player;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.Item;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.container.Inventory;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.tool.WoodenAxe;
import com.loafy.game.item.tool.WoodenPickaxe;
import com.loafy.game.item.tool.WoodenShovel;
import com.loafy.game.world.block.Material;

public class PlayerInventory extends Inventory {

    private boolean open;
    private int hotbarSlot;

    public PlayerInventory(int slots) {
        super(slots);

        //this.slots[15].setItemStack(new ItemStack(new ItemPlantFiber(), 21));

        this.slots[0].setItemStack(new ItemStack(new WoodenAxe(), 1));
        this.slots[1].setItemStack(new ItemStack(new WoodenPickaxe(), 1));
        this.slots[2].setItemStack(new ItemStack(new WoodenShovel(), 1));
        this.slots[3].setItemStack(new ItemStack(new ItemBlock(Material.CHEST), 15));
    }

    public void update(EntityPlayer player) {
        super.update(player);
        int key = InputManager.keyPressed();

        if(!open) {
            for (int i = 2; i < 12; i++) {
                if (key == i) {
                    hotbarSlot = i - 2;
                }
            }

            int dwheel =InputManager.mouseWd / 120;

            hotbarSlot -= dwheel;

            if (hotbarSlot > GRID_WIDTH - 1)
                hotbarSlot = 0;

            if (hotbarSlot < 0)
                hotbarSlot = GRID_WIDTH - 1;
        }

        slots[hotbarSlot].setSelected(true);
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
