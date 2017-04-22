package com.loafy.game.world.block.materials;

import com.loafy.game.item.ItemStack;
import com.loafy.game.item.material.ItemWood;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.util.ArrayList;
import java.util.List;

public class MaterialWood extends Material {

    public MaterialWood() {
        super(4, 2000, true, true, MaterialType.BLOCK, "Log");
    }

    public void destroy(World world, float x, float y) {
        int blockX = (int)x;
        int blockY = (int)y;

        outer: for(int i = 1; i < 50; i++) {
            Block block = world.getBlock(blockX, blockY - (i * Material.SIZE));

            if(block.getMaterial().getID() == Material.WOOD.getID()) {
                block.destroyClear(world, block);
            } else {

                blockX = (int)block.getX() - Material.SIZE;
                blockY = (int)block.getY() - Material.SIZE * 2;

                for(int j = 0; j < 3; j++) {
                    for(int k = 0; k < 3; k++) {
                        Block leafblock = world.getBlock(blockX + (j * Material.SIZE), blockY + (k * Material.SIZE));
                        if(leafblock.getMaterial() == Material.LEAF) {
                            block.destroyClear(world, leafblock);
                        } else {
                            break outer;
                        }
                    }
                }
            }
        }
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(new ItemWood(), 1));
        return list;
    }
}
