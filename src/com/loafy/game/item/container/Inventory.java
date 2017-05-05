package com.loafy.game.item.container;

import com.loafy.game.item.Item;
import com.loafy.game.item.ItemStack;

public class Inventory extends Container {

    public Inventory(int slots) {
        super(slots);
    }

    public boolean subtractItem(Item item, int slot, int subtractAmount) {

        boolean removeSlot = slot != -1;
        int remaining = subtractAmount;
        if (removeSlot) {
            remaining = removeItem(item, slot, subtractAmount);
            // just remove from that slot
        }


        for (int i = slots.size() - 1; i >= 0; i--) {
            remaining = removeItem(item, i, remaining);

            if (remaining <= 0) {
                inventoryChange();
                return true;
            }
        }

        return false;
    }

    private int removeItem(Item item, int slot, int subtractAmount) {
        ItemStack itemstack = slots.get(slot).getItemStack();
        if (itemstack.getItem().getID() == item.getID()) {
            int amount = itemstack.getAmount();

            if (amount - subtractAmount <= 0) {
                itemstack.setAmount(0);
                itemstack.setItem(new Item());

                subtractAmount = Math.abs(amount - subtractAmount);

                slots.get(slot).setItemStack(itemstack);
            } else {
                itemstack.setAmount(amount - subtractAmount);
                subtractAmount = -1;
            }
        }

        return subtractAmount;
    }

    public boolean addItem(Item item, int amount) {
        for (ContainerSlot slot : getSlots()) {
            ItemStack itemstack = slot.getItemStack();

            if (itemstack == null)
                continue;

            if (itemstack.getAmount() + amount > itemstack.getMaxStackSize() && itemstack.getItem().getID() == item.getID())
                continue;

            if (itemstack.getItem().getID() == -1) {
                itemstack.setAmount(amount);
                itemstack.setItem(item);
                inventoryChange();
                return true;
            } else if (itemstack.getItem().getID() == item.getID()) {
                itemstack.addAmount(amount);
                inventoryChange();
                return true;
            }
        }

        return false;
    }

}
