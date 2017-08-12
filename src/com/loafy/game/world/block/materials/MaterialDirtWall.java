package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import java.awt.*;

import static com.loafy.game.world.block.MaterialType.WALL;

public class MaterialDirtWall extends Material {

    public MaterialDirtWall() {
        super("dirt_wall", 18, 85, 0f, true, true, WALL, "Dirt Wall", new Color(79, 61, 46));
    }
}
