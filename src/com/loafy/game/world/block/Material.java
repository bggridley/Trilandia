package com.loafy.game.world.block;

import com.loafy.game.gfx.*;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;
import com.loafy.game.world.block.materials.*;
import com.loafy.game.gfx.Graphics;
import org.lwjgl.Sys;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.loafy.game.world.block.MaterialType.WALL;

public class Material {

    public static final float SOLID_LIGHTDECREMENT = 0.1f;

    protected boolean transparent = false;
    protected boolean passable;
    protected boolean solid;
    protected int id;
    protected float hardness;
    protected float friction = 1f;
    private float lightDecrement;
    protected String name;
    protected MaterialType type;
    protected Color color;

    private Texture texture;

    private static final int size = 12;
    public static final int scale = 2;
    public static final int SIZE = size * scale;

    public static Material COPPER_ORE = new MaterialCopperOre();
    public static Material SILVER_ORE = new MaterialSilverOre();
    public static Material DIRT_WALL = new MaterialDirtWall();
    public static Material STONE_WALL = new MaterialStoneWall();
    public static Material WOOD_WALL = new MaterialWoodWall();
    public static Material LOG = new MaterialLog();
    public static Material LEAF = new MaterialLeaf();
    public static Material AIR = new MaterialAir();
    public static Material STONE = new MaterialStone();
    public static Material DIRT = new MaterialDirt();
    public static Material GRASS = new MaterialGrass();
    public static Material WOOD = new MaterialWood();
    public static Material CHEST = new MaterialChest();
    public static Material TORCH = new MaterialTorch();

    public static Material CERISE_STONE = new MaterialCeriseStone();

    public Material(String file, int id, float hardness, float lightDecrement, boolean solid, boolean passable, MaterialType type, String name, Color color) {
        this.name = name;
        this.id = id;
        this.solid = solid;
        this.hardness = hardness;
        this.type = type;
        this.passable = passable;
        this.lightDecrement = lightDecrement;
        this.color = color;
        this.texture = Texture.loadTexture(Texture.loadBi("materials/"+ file + ".png", 2));
    }

    public static Material fromID(int id) {
        return Materials.getID(id);
    }

    public String getName() {
        return name;
    }

    public void destroy(World world, float x, float y) {

    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(new ItemBlock(this), 1));
        return list;
    }

    public Texture getTexture() {
        return texture;
    }

    public void render(float x, float y, float light, int r, int g, int b) {
        if(this == Material.AIR) return;
        texture.render(x, y, 1f, false, light + r, light + g, light + b, 255f);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getLight() {
        return -1;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public float getDecrement() {
        return lightDecrement;
    }

    public float getHardness() {
        return hardness;
    }

    public MaterialType getType() {
        return type;
    }

    public final int getID() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public float getFriction() {
        return friction;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean getPlaceConditions(World world, int blockX, int blockY) {
        return true;
    }

    public boolean getBreakConditions(World world, int blockX, int blockY) {
        int above = world.getBlock(blockX, blockY - 1);

       // System.out.println(above);

        if (above == Material.LOG.getID() && this != Material.LOG) return false;

        return true;
    }
}
