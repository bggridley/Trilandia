package com.loafy.game.world.block.materials;

import com.loafy.game.item.ItemStack;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialStone extends Material {

    public MaterialStone() {
        super("stone",1, 400, 0.1f, true, false, MaterialType.BLOCK, "Stone", new Color(145, 145, 145));
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();

        Random random = new Random();
        int rand = random.nextInt(3);

        list.add(new ItemStack(new ItemSmallStone(), rand));

        return list;
    }
}
