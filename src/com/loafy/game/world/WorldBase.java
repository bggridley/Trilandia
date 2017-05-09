package com.loafy.game.world;

import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.data.LevelData;
import com.loafy.game.world.data.WorldData;

public class WorldBase {

    protected String name;
    protected int width;
    protected int height;
    protected int[][] blocks;
    protected int[][] walls;
    protected int spawnX;
    protected int spawnY;

    public WorldData getWorldData() {
        return new WorldData(this);
    }

    public LevelData getLevelData() {
        return new LevelData(this);
    }

    public Chunk fetchChunk(int chunkX, int chunkY) {
        int xx = chunkX * Chunk.SIZE;
        int yy = chunkY * Chunk.SIZE;
        Block[][] chunkBlocks = new Block[Chunk.SIZE][Chunk.SIZE];
        Block[][] chunkWalls = new Block[Chunk.SIZE][Chunk.SIZE];

        for (int i = 0; i < Chunk.SIZE; i++) {
            for (int j = 0; j < Chunk.SIZE; j++) {
                chunkBlocks[i][j] = new Block(Material.fromID(blocks[xx + i][yy + j]), (xx + i) * Material.SIZE, (yy + j) * Material.SIZE);
                chunkWalls[i][j] = new Block(Material.fromID(walls[xx + i][yy + j]), (xx + i) * Material.SIZE, (yy + j) * Material.SIZE);
            }
        }

        return new Chunk(chunkBlocks, chunkWalls, chunkX, chunkY);
    }

    public void saveChunk(Chunk chunk) {
        for(int x = 0; x < chunk.getBlocks().length; x++) {
            for (int y = 0; y < chunk.getBlocks()[0].length; y++) {
                blocks[chunk.getChunkX() * Chunk.SIZE + x][chunk.getChunkY() * Chunk.SIZE + y] = chunk.getBlock(x, y).getMaterial().getID();
                walls[chunk.getChunkX() * Chunk.SIZE + x][chunk.getChunkY() * Chunk.SIZE + y] = chunk.getWall(x, y).getMaterial().getID();
            }
        }
    }

    public int[][] getBlocks() {
        return this.blocks;
    }

    public int[][] getWalls() {
        return this.walls;
    }

    public int getWall(int x, int y) {
        return getBlock(walls, x, y);
    }

    public void setWall(Material material, int x, int y) {
        setBlock(walls, material, x, y);
    }

    public int getBlock(int x, int y) {
        return getBlock(blocks, x, y);
    }

    public void setBlock(Material material, int x, int y) {
        setBlock(blocks, material, x, y);
    }

    public void setBlock(int[][] array, Material material, int x, int y) {
        if (x < 0 || x > array.length - 1 || y < 0 || y > array[0].length - 1)
            return;
        array[x][y] = material.getID();
    }

    public int getBlock(int[][] array, int x, int y) {
        if (x < 0 || x > array.length - 1 || y < 0 || y > array[0].length - 1)
            return -1;

        return array[x][y];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }
}
