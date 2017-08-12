package com.loafy.game.world.block.materials;

import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialGrass extends Material {

    public MaterialGrass() {
        super("grass",3, 115, 0.08f, true, false, MaterialType.BLOCK, "Grass", new Color(83, 164, 76));
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(new ItemBlock(Material.DIRT), 1));
        return list;
    }
}
