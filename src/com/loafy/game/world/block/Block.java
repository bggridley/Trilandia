package com.loafy.game.world.block;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.item.ItemStack;
import com.loafy.game.world.World;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;
import java.util.Random;

public class Block {

    private Material material;
    private float x, y;

    private float maxHardness;
    private float hardness;

    private float friction;

    public org.newdawn.slick.geom.Rectangle box;

    public Block(Material material, float x, float y) {
        this.material = material;
        this.x = x;
        this.y = y;

        this.maxHardness = material.getHardness();
        this.hardness = maxHardness;
        this.friction = material.getFriction();
        this.box = new Rectangle(x, y, Material.SIZE, Material.SIZE);
    }

    public void render(float xOffset, float yOffset, float renderLight) {
       /* float renderLight = light;
        if(renderLight <= 0.03f) {
            renderLight = 0.03f;
        }*/

        if (!(x - xOffset + Material.SIZE < 0 || x - xOffset > Display.getWidth() || y - yOffset + Material.SIZE < 0 || y - yOffset > Display.getHeight())) {
            material.render(x - xOffset, y - yOffset, renderLight);
            if (hardness != maxHardness) {

                float space = maxHardness / 7;
                float curHardness = maxHardness - hardness;
                int state = -1;
                for (int i = 0; i < 7; i++) {
                    if (curHardness >= i * space && curHardness < i * space + space)
                        state = i;
                }

                material.getSpriteSheet().getTexture(32 + state).render(x - xOffset, y - yOffset);
            }
        }
    }

    public void clickLeft(EntityPlayer player) {

    }

    public void clickRight(EntityPlayer player) {

    }

    public List<ItemStack> dropItem() {
        return material.getDrops();
    }

    public void drop(World world, float x, float y) {
        Random random = new Random();

        List<ItemStack> dropItem = dropItem();


        for (ItemStack drop : dropItem) {
            if (drop != null)
                drop.drop(world, x, y, random.nextInt(5) - 2.5f, random.nextInt(5) - 2.5f, true);
        }
    }

    public void destroy(World world) {
        material.destroy(world, this.getX(), this.getY());

        int x = (int) this.getX() / Material.SIZE;
        int y = (int) this.getY() / Material.SIZE;

        float light = material.getLight();

        if (light != -1) {
            world.getLightMap().removeLightAt(x, y);
        }

        destroyClear(world, this);


        world.blockChange((int) this.getX(), (int) this.getY());
    }

    public void destroyClear(World world, Block block) {
        if (block.getMaterial() != Material.AIR)
            block.drop(world, block.x + 8, block.y + 8);

        block.setMaterial(Material.AIR);
        block.setHardness(block.getMaxHardness());
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getHardness() {
        return hardness;
    }

    public float getMaxHardness() {
        return maxHardness;
    }

    public void setMaterial(Material material) {
        this.material = material;
        this.maxHardness = material.getHardness();
        this.hardness = maxHardness;
    }

    public Material getMaterial() {
        return material;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getFriction() {
        return friction;
    }
}
