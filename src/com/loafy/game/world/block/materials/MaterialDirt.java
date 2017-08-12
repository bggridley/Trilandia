package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import java.awt.*;

import static com.loafy.game.world.block.MaterialType.BLOCK;

public class MaterialDirt extends Material {

    public MaterialDirt() {
        super("dirt",2, 85, 0.1f, true, false, BLOCK, "Dirt", new Color(161, 120, 87));
    }
}
