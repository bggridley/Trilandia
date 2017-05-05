package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import static com.loafy.game.world.block.MaterialType.WALL;

public class MaterialWoodWall extends Material {

    public MaterialWoodWall() {
        super(21, 70, 0.1f, true, true, WALL, "Wood Wall");
    }
}
