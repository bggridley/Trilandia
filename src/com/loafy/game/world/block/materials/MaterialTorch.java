package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;
import com.loafy.game.world.lighting.Light;
import com.loafy.game.world.lighting.LightMap;

public class MaterialTorch extends Material {

    public MaterialTorch() {
        super(9, 0f, 0f, true, true, MaterialType.BLOCK, "Torch");

        this.transparent = true;
    }

    public float getLight() {
        return 0.8f;
    }
}
