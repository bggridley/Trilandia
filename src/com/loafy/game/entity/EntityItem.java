package com.loafy.game.entity;

import com.loafy.game.Main;
import com.loafy.game.gfx.Texture;
import com.loafy.game.item.Item;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;

public class EntityItem extends Entity {

    private Item item;
    private final int pickupTime = 120; //* (Main.FPS / Main.UPS);


    public EntityItem(World world, float x, float y, Item item) {
        super(world, x, y);
        this.item = item;
        this.time = pickupTime;
        this.height = item.getTexture().getHeight() / 2;
        this.width = item.getTexture().getHeight() / 2;
        this.MAX_FALLING_SPEED = 3.5f;
        this.airFriction = 0.1f;
    }

    public EntityItem(World world, float x, float y, int id) {
        this(world, x, y, Item.fromID(id));
    }

    public void render(float xOffset, float yOffset) {
        item.render(x - xOffset, y - yOffset , 0.5f, false);
    }

    public Item getItem() {
        return item;
    }

    public int getPickupTime() {
        return pickupTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
