package com.loafy.game.world.block.materials;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.awt.*;

public class MaterialTorch extends Material {

    public MaterialTorch() {
        super("torch",9, 0f, 0f, true, true, MaterialType.BLOCK, "Torch", new Color(255, 209, 0));

        this.transparent = true;
    }

    public float getLight() {
        return 0.92f;
    }

    public boolean getPlaceConditions(World world, int blockX, int blockY) {
        Block wall = world.getWallFromChunks(blockX, blockY);

        if(wall.getMaterial() == Material.AIR) return false;

        return true;
    }
}
