package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;

public class MaterialAir extends Material {

    public MaterialAir() {
        super(0, 0, 0.05f, false, true, MaterialType.BLOCK, "Air");

        this.transparent = true;
    }

    public String getName() {
        return "Air";
    }

    public void render(float x, float y) {

    }

}
