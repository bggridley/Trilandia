package com.loafy.game.item.container;

import com.loafy.game.item.Item;
import com.loafy.game.item.ItemStack;

public class ContainerSlot {

    private ItemStack itemStack;
    public static final float SIZE = 44f;

    private float x;
    private float y;
    private boolean active;
    private boolean selected;

    public ContainerSlot (float x, float y) {
        this.x = x;
        this.y = y;

        itemStack = new ItemStack(new Item(), 0);
    }

    public void setItemStack (ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack () {
        return itemStack;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
