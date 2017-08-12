package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

import java.awt.*;

public class MaterialAir extends Material {

    public MaterialAir() {
        super("air",0, 0, 0.06f, false, true, MaterialType.BLOCK, "Air", new Color(124, 200, 255));

        this.transparent = true;
    }

    public String getName() {
        return "Air";
    }

    public void render(float x, float y) {

    }

}
