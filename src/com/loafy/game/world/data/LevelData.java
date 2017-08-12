package com.loafy.game.world.data;

import com.loafy.game.world.WorldBase;

import java.io.Serializable;

public class LevelData implements Serializable {

    public int[][] blocks;
    public int[][] walls;

    public LevelData() {

    }

    public LevelData(WorldBase world) {
        this.blocks = world.getBlocks();
        this.walls = world.getWalls();
    }

}
