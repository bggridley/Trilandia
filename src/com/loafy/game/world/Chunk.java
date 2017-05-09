package com.loafy.game.world;

import com.loafy.game.world.block.Block;


public class Chunk {

    public static final int SIZE = 16;
    private Block[][] blocks;
    private Block[][] walls;
    private int chunkX;
    private int chunkY;

    public Chunk(Block[][] blocks, Block[][] walls, int chunkX, int chunkY) {
        this.blocks = blocks.clone();
        this.walls = walls.clone();
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public Block[][] getWalls() {
        return walls;
    }

    public Block getWall(int x, int y) {
        return walls[x][y];
    }

    public Block getBlock(int x, int y) {
        return blocks[x][y];
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

}
