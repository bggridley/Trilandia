package com.loafy.game.world.block.materials;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;

import java.awt.*;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialChest extends Material {

    public MaterialChest() {
        super("chest",8, 750, 0.1f, true, true, BLOCK, "Chest", new Color(121, 88, 70));

        this.transparent = true;
    }

    public boolean getPlaceConditions(World world, int blockX, int blockY) {
        Block down = world.getBlockFromChunks(blockX, blockY + SIZE);
        if (down.getMaterial().isPassable() || down.getMaterial() == Material.CHEST) return false;

        return true;
    }

    public boolean getBreakConditions(World world, int blockX, int blockY) {
        if (super.getBreakConditions(world, blockX, blockY)) return false;

        // todo otherwise skr skr, this is an example

        return true;
    }
}
