package com.loafy.game.entity;

import com.loafy.game.Main;
import com.loafy.game.item.Item;
import com.loafy.game.world.World;

import java.awt.*;

public class EntityItem extends Entity {

    private Item item;
    private final float pickupTime = 120 / (Main.FPS / Main.rFPS); //* (Main.FPS / Main.UPS);

    public EntityItem(World world, float x, float y, Item item) {
        super(world, x, y);
        this.item = item;
        this.time = pickupTime;
        this.height = item.getTexture().getHeight() / 2;
        this.width = item.getTexture().getHeight() / 2;
        this.MAX_FALLING_SPEED = 6f;
        this.airFriction = 0.1f;
    }

    public EntityItem(World world, float x, float y, int id) {
        this(world, x, y, Item.fromID(id));
    }

    public void render(float xOffset, float yOffset, float lightLevel) {
        item.render(x - xOffset, y - yOffset, 0.5f, false, new Color(lightLevel, lightLevel, lightLevel));
    }

    public Item getItem() {
        return item;
    }

    public float getPickupTime() {
        return pickupTime;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
