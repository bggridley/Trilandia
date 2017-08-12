package com.loafy.game.world.block.materials;

import com.loafy.game.item.ItemStack;
import com.loafy.game.item.material.ItemSmallStone;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialCopperOre extends Material {

    public MaterialCopperOre() {
        super("copper_ore",14, 500, 0.13f, true, false, MaterialType.BLOCK, "Copper Ore", new Color(183, 107, 66));
    }

 /*   public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();

        Random random = new Random();
        int rand = random.nextInt(3);

        list.add(new ItemStack(new ItemSmallStone(), rand));

        return list;
    }*/
}
