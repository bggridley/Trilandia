package com.loafy.game.item.container;

import com.loafy.game.item.Item;
import com.loafy.game.item.ItemStack;

public class Inventory extends Container {

    public Inventory(int slots) {
        super(slots, slots);
    }

    public boolean subtractItem(Item item, int slot, int subtractAmount) {
        for (int i = slots.length - 1; i >= 0; i--) {
            ItemStack itemstack = slots[i].getItemStack();

            if (itemstack.getItem().getID() == item.getID()) {
                int amount = itemstack.getAmount();

                if (amount - subtractAmount <= 0) {
                    itemstack.setAmount(0);
                    itemstack.setItem(new Item());

                    subtractAmount = Math.abs(amount - subtractAmount);

                    if (slot == -1)
                        slot = i;

                    slots[slot].setItemStack(itemstack);

                    continue;
                } else {
                    itemstack.setAmount(amount - subtractAmount);
                }

                return true;
            }
        }

        return false;
    }

    public boolean addItem(Item item, int amount) {
        for (ContainerSlot slot : getSlots()) {
            ItemStack itemstack = slot.getItemStack();

            if(itemstack == null)
                continue;

            if (itemstack.getAmount() + amount > itemstack.getMaxStackSize() && itemstack.getItem().getID() == item.getID())
                continue;

            if (itemstack.getItem().getID() == -1) {
                itemstack.setAmount(amount);
                itemstack.setItem(item);
                return true;
            } else if (itemstack.getItem().getID() == item.getID()) {
                itemstack.addAmount(amount);
                return true;
            }
        }

        return false;
    }


}
