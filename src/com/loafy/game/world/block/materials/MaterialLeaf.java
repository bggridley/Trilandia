package com.loafy.game.world.block.materials;

import com.loafy.game.item.ItemStack;
import com.loafy.game.item.material.ItemPlantFiber;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;
import com.loafy.game.world.lighting.LightMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialLeaf extends Material {

    public MaterialLeaf() {
        super("leaf",6, 45, LightMap.DEFAULT, true, true, MaterialType.WALL, "Leaf", new Color(104, 198, 93));
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();


        Random random = new Random();
        int rand = random.nextInt(3);

        if (rand == 1 || rand == 2)
            list.add(new ItemStack(new ItemPlantFiber(), 1));

        return list;
    }
}
