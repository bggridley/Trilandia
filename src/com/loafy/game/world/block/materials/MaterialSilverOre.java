package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

public class MaterialSilverOre extends Material {

    public MaterialSilverOre() {
        super(15, 550, 0.13f, true, false, MaterialType.BLOCK, "Silver Ore");
    }

 /*   public List<ItemStack> getDrops() {
        List<ItemStack> list = new ArrayList<>();

        Random random = new Random();
        int rand = random.nextInt(3);

        list.add(new ItemStack(new ItemSmallStone(), rand));

        return list;
    }*/
}
