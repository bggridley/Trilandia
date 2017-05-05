package com.loafy.game.world.data;

import com.loafy.game.world.Chunk;
import com.loafy.game.world.block.Block;

public class ChunkData {

    public int[][] blocks;
    public int[][] walls;

    public ChunkData() {

    }

    public ChunkData(Chunk chunk) {
        Block[][] chunkBlocks = chunk.getBlocks();
        Block[][] chunkWalls = chunk.getWalls();
        blocks = new int[chunkBlocks.length][chunkBlocks[0].length];
        walls = new int[chunkWalls.length][chunkWalls[0].length];

        for (int x = 0; x < chunkBlocks.length; x++) {
            for (int y = 0; y < chunkBlocks[0].length; y++) {
                blocks[x][y] = chunkBlocks[x][y].getMaterial().getID();
            }
        }

        for (int x = 0; x < chunkWalls.length; x++) {
            for (int y = 0; y < chunkWalls[0].length; y++) {
                walls[x][y] = chunkWalls[x][y].getMaterial().getID();
            }
        }
    }

}
