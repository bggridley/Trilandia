package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import java.awt.*;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialWood extends Material {

    public MaterialWood() {
        super("wood",5, 70, 0.1f, true, false, BLOCK, "Wood", new Color(177, 138, 104));
    }
}
