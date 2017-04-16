package com.loafy.game.item;

import com.loafy.game.gfx.Texture;
import com.loafy.game.item.container.ContainerSlot;
import com.loafy.game.world.World;

public class ItemStack {

    private Item item;
    private int maxStackSize;
    private int amount;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.maxStackSize = item.getMaxStackSize();
        this.amount = amount;
    }

    public void renderInContainer(float x, float y) {
        Texture texture = item.getTexture();
        texture.render(x + ((ContainerSlot.SIZE - texture.getWidth()) / 2), y + ((ContainerSlot.SIZE - texture.getWidth()) / 2));
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public boolean subtractAmount(int amount) {
        this.amount -= amount;
        return true;
    }

    public void drop(World world, float x, float y, float dx, float dy, boolean time) {
        for(int i = 0; i < amount; i++)
            item.drop(world, x, y, dx, dy, time);
    }

    public boolean useLeft() {
        return false;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxStackSize () {
        return maxStackSize;
    }
}
