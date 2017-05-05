package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialWood extends Material {

    public MaterialWood() {
        super(5, 70, 0.1f, true, false, BLOCK, "Wood");
    }
}
