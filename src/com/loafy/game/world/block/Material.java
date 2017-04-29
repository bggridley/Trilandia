package com.loafy.game.world.block;

import com.loafy.game.gfx.Animation;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;
import com.loafy.game.world.block.materials.*;
import com.loafy.game.world.lighting.Light;
import com.loafy.game.world.lighting.LightMap;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

import static com.loafy.game.world.block.MaterialType.*;

public class Material {

    protected boolean transparent = false;
    protected boolean passable;
    protected boolean solid;
    protected int id;
    protected float hardness;
    protected float friction = 1f;
    private float lightDecrement;
    protected String name;
    protected MaterialType type;


    private SpriteSheet sprites;
    private Animation breakAnimation;

    private static final int size = 16;
    public static final int scale = 2;
    public static final int SIZE = size * scale;

    public static Material DIRT_WALL = new Material(2 + 16, 85, 0f, true, true, WALL, "Dirt Wall");
    public static Material STONE_WALL = new Material(1 + 16, 750, 0f,true, true, WALL, "Stone Wall");
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

    public Material(int id, float hardness, float lightDecrement, boolean solid, boolean passable, MaterialType type, String name) {
        this.name = name;
        this.id = id;
        this.solid = solid;
        this.hardness = hardness;
        this.type = type;
        this.passable = passable;
        this.lightDecrement = lightDecrement;

        sprites = Resources.blocksSprite;
        this.breakAnimation = new Animation("blocks.png", 2, 16, 16);
        this.breakAnimation.setType(Animation.CUSTOM);
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
        if (id != -1)
            return getSpriteSheet().getTexture(id);

        else return null;
    }

    public void render(float x, float y, float light) {
        sprites.getTexture(id).render(x, y, 1f, false, new Color(light, light, light));
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

    public SpriteSheet getSpriteSheet() {
        return sprites;
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

    public Animation getBreakAnimation() {
        return breakAnimation;
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
        Block above = world.getBlock(blockX, blockY - SIZE);

        if(above.getMaterial() == Material.LOG && this != Material.LOG) return false;

        return true;
    }
}
