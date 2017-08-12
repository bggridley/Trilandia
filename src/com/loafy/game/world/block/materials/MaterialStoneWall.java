package com.loafy.game.world.block.materials;

import com.loafy.game.world.block.Material;

import java.awt.*;

import static com.loafy.game.world.block.MaterialType.WALL;

public class MaterialStoneWall extends Material {

    public MaterialStoneWall() {
       super("stone_wall",17, 750, 0f, true, true, WALL, "Stone Wall", new Color(51, 51, 51));
    }
}
