package com.loafy.game.item;

import com.loafy.game.entity.EntityItem;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.item.tool.*;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Material;
import org.newdawn.slick.Color;

public class Item {

    public static int SIZE = 32;
    public static SpriteSheet items;
    public int id = -1;
    public int maxStackSize;
    public ItemTier tier = ItemTier.DEFAULT;

    public static final int PLANT_FIBER = 48;
    public static final int WOOD = 49;
    public static final int SMALL_STONE = 50;
    public static final int STONE_AXE = 17;
    public static final int STONE_PICKAXE = 1;
    public static final int STONE_SHOVEL = 33;
    public static final int WOODEN_AXE = 16;
    public static final int WOODEN_PICKAXE = 0;
    public static final int WOODEN_SHOVEL = 32;

    public Item() {
        items = Resources.itemsSprite;
        this.maxStackSize = 100;
    }

    public Texture getTexture() {
        if (id != -1)
            return items.getTexture(id);

        return new Texture(0, 0, 0);
    }

    public void render(float x, float y) {
        getTexture().render(x, y);
    }

    public void render(float x, float y, float scale, boolean flip) {
        getTexture().render(x, y, scale, flip);
    }

    public void render(float x, float y, float scale, boolean flip, Color color) {
        getTexture().render(x, y, scale, flip, color);
    }

    public void useLeft(EntityPlayer player, float delta) { // todo
        int mx = (int) (InputManager.mouseX + player.getWorld().xOffset);
        int my = (int) (InputManager.mouseY + player.getWorld().yOffset);

        player.getWorld().getBlockFromChunks(mx, my).clickLeft(player);
    }

    public void useRight(EntityPlayer player, float delta) {
        int mx = (int) (InputManager.mouseX + player.getWorld().xOffset);
        int my = (int) (InputManager.mouseY + player.getWorld().yOffset);

        player.getWorld().getBlockFromChunks(mx, my).clickRight(player);
    }

    public String getName() {
        return "Item name";
    }

    public String getDescription() {
        return "Item description";
    }

    public ItemTier getTier() {
        return tier;
    }

    public void drop(World world, float x, float y, float dx, float dy, boolean instantPickup) {
        EntityItem ei = new EntityItem(world, x, y, this);
        ei.setVelocity(dx, dy);
        world.addEntityLoop(ei);

        if (!instantPickup)
            ei.setTime(0);
    }

    public float getLight() {
        return -1f;
    }

    public int getID() {
        return id;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public static Item fromID(int id) {
        if (id < 128) {
            switch (id) {
                case PLANT_FIBER:
                    return new ItemPlantFiber();
                case WOOD:
                    return new ItemWood();
                case STONE_AXE:
                    return new StoneAxe();
                case STONE_PICKAXE:
                    return new StonePickaxe();
                case STONE_SHOVEL:
                    return new StoneShovel();
                case WOODEN_AXE:
                    return new WoodenAxe();
                case WOODEN_PICKAXE:
                    return new WoodenPickaxe();
                case WOODEN_SHOVEL:
                    return new WoodenShovel();
            }
        } else {
            return new ItemBlock(Material.fromID(id - 128));
        }

        return null;
    }
}
