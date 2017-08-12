package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;
import com.loafy.game.world.block.Materials;

import java.awt.*;

public class MaterialCeriseStone extends Material {

    public MaterialCeriseStone() {
        super("cerise_stone", Materials.CERISE_STONE, 500f, Material.SOLID_LIGHTDECREMENT, true, false, MaterialType.BLOCK, "Cerise Stone", new Color(146, 97, 107));
    }
}
