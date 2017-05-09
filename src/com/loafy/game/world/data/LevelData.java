package com.loafy.game.world.data;

import com.loafy.game.world.WorldBase;

public class LevelData {

    public int[][] blocks;
    public int[][] walls;

    public LevelData() {

    }

    public LevelData(WorldBase world) {
        this.blocks = world.getBlocks();
        this.walls = world.getWalls();
    }

}
