package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialDirt extends Material {

    public MaterialDirt() {
        super(2, 85, 0.08f, true, false, BLOCK, "Dirt");
    }
}
