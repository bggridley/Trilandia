package com.loafy.game.world.block.materials;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialChest extends Material {

    public MaterialChest() {
        super(7, 750, true, true,  BLOCK,"Chest");
    }

    public boolean getPlaceConditions(World world, int blockX, int blockY) {
        Block down = world.getBlock(blockX, blockY + SIZE);
        if(!down.getMaterial().isSolid()) return false;

        return true;
    }
}
