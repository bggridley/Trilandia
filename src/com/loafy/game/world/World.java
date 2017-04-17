package com.loafy.game.world;

import com.google.gson.Gson;
import com.loafy.game.entity.Entity;
import com.loafy.game.entity.EntityGoat;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.Materials;
import com.loafy.game.world.block.blocks.BlockChest;
import com.loafy.game.world.data.PlayerData;
import com.loafy.game.world.data.WorldData;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.Iterator;

public class World {

    private ArrayList<Chunk> activeChunks;

    private ArrayList<Entity> entities;

    private ArrayList<Entity> entitiesToAdd;
    private ArrayList<Entity> entitiesToRemove;

    private EntityPlayer player;
    private EntityGoat goat;

    public float bxOffset, byOffset;
    public float xOffset, yOffset;
    public Texture background;

    public String name;

    private int width, height;

    public void initLists() {
        this.activeChunks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.entitiesToAdd = new ArrayList<>();
        this.entitiesToRemove = new ArrayList<>();
    }

    /**
     * Apply all properties from worldgenerator to current world
     */

    public World(String name, WorldGenerator generator) {
        initLists();
        this.name = name;

        this.width = generator.width;
        this.height = generator.height;

        this.player = new EntityPlayer(this, generator.getSpawnX() * Material.SIZE, generator.getSpawnY() * Material.SIZE);
        this.goat = new EntityGoat(this, generator.getSpawnX() * Material.SIZE, generator.getSpawnY() * Material.SIZE);

        this.xOffset = player.getX() - (Display.getWidth() / 2) + (player.width / 2);
        this.yOffset = player.getY() - (Display.getHeight() / 2) + (player.height / 2);
        this.loadChunk((int)player.getX() / Material.SIZE, (int)player.getY() / Material.SIZE);
        initBackground();
    }

    // add more data in constructor to find player spawn location and shit lol
    public World(WorldData worldData, PlayerData playerData) {
        initLists();
        this.name = worldData.name;

        this.width = worldData.width;
        this.height = worldData.height;

        this.player = new EntityPlayer(this, playerData.spawnX * Material.SIZE, playerData.spawnY * Material.SIZE); // maybe make spawnx/y an int to save a TEENIE TINIIE bit of space :) ) ))
        this.goat = new EntityGoat(this, playerData.spawnX * Material.SIZE, playerData.spawnY * Material.SIZE);

        this.xOffset = player.getX() - (Display.getWidth() / 2) + (player.width / 2);
        this.yOffset = player.getY() - (Display.getHeight() / 2) + (player.height / 2);
        this.loadChunk((int)player.getX() / Material.SIZE, (int)player.getY() / Material.SIZE);
        initBackground();
    }


    // load world idc rn so ya
 /*   public World(String name, ChunkLoader loader) {
        this.name = name;
        initLists();


    }*/


  /*  public void loadEntities(String worldName) {
        String path = Resources.gameLocation + "/worlds/" + worldName;
        entities = WorldDataLoader.loadEntities(this, path, "entities.dat");

        for (Entity entity : entities) {
            System.out.println(entity);
            if (entity instanceof EntityPlayer) {
                this.player = (EntityPlayer) entity;
            } else if (entity instanceof EntityGoat) {
                this.goat = (EntityGoat) entity;
            }
        }

        this.xOffset = player.getX() - (Display.getWidth() / 2) + (player.width / 2);
        this.yOffset = player.getY() - (Display.getHeight() / 2) + (player.height / 2);
        initBackground();
    }*/


 /*   public void saveToFile() {
        String path = Resources.gameLocation + "/worlds/" + name;

        //WorldData.saveBlocks(path + "/level", "blocks.dat", blocks);
        //WorldData.saveBlocks(path + "/level", "walls.dat", walls);
        WorldDataLoader.saveAllChunks(path + "/level", "chunks.dat", chunks);
        WorldDataLoader.saveEntities(path, "entities.dat", entities);
        WorldDataLoader.saveWorld(path, "world.dat", name, new Date(), chunks.length, chunks[0].length);
        //WorldData.loadBlocks(path, "blocks.wld");
    }*/


    public void initBackground() {
        this.background = Resources.backgroundTexture;

        this.bxOffset = -(Display.getWidth() - background.getWidth()) / 2;
        //this.byOffset = (Display.getHeight() - background.getHeight()) / 2;
    }

    boolean rendering = false;

    public void render() {
        rendering = true;
        background.render(0 - bxOffset, 0 - byOffset);
        for (Chunk chunk : activeChunks) {
            for (int x = 0; x < chunk.getBlocks().length; x++) {
                for (int y = 0; y < chunk.getBlocks()[x].length; y++) {
                    Block block = chunk.getBlocks()[x][y];
                    Block wall = chunk.getWalls()[x][y];


                    if(block.getMaterial() == Material.AIR && wall != null)
                    wall.render(xOffset, yOffset);

                    block.render(xOffset, yOffset);
                }
            }
        }

        for (Entity entity : entities) {
            entity.render(xOffset, yOffset);
        }

        rendering = false;
    }

    public void update(float delta) {
        updateChunks();

        for (Entity entity : entities) {
            entity.update(delta);
        }

        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);

        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }


    public void updateChunks() {
        int chunkX = ((int) (player.x / Material.SIZE)) / Chunk.SIZE;
        int chunkY = ((int) (player.y / Material.SIZE)) / Chunk.SIZE;

        for (int i = -2; i < 3; i++) {
            for (int j = -1; j < 2; j++) {
                loadChunk(chunkX + i, chunkY + j);
            }
        }
        unloadChunks(chunkX, chunkY);
    }

    public void loadChunk(int chunkX, int chunkY) {
        if (getChunk(chunkX, chunkY) != null) return;

        if (chunkX >= 0 && chunkY >= 0 && chunkX < width / Chunk.SIZE && chunkY < height / Chunk.SIZE) {
            Chunk chunk = WorldLoader.fetchChunk(name, chunkX, chunkY);
            activeChunks.add(chunk);
        }
    }


    // player chunkx/y
    public void unloadChunks(int chunkX, int chunkY) {
        Iterator<Chunk> it = activeChunks.iterator();

        Gson gson = new Gson();
        while (it.hasNext()) {
            Chunk chunk = it.next();
            if ((Math.abs(chunkX - chunk.getChunkX()) >= 4) || Math.abs(chunkY - chunk.getChunkY()) >= 3) {
                WorldLoader.saveChunk(name, chunk, gson);
                it.remove();
                System.out.println("unloading/saving chunk " + chunk.getChunkX() + ", " + chunk.getChunkY() + "... active chunks: " + activeChunks.size());
            }
        }
    }

    public void unload() {

        Gson gson = new Gson();

        for(Chunk chunk : activeChunks) {
            WorldLoader.saveChunk(name, chunk, gson);
        }

        this.activeChunks.clear();
        this.entities.clear();
        this.entitiesToAdd.clear();
        this.entitiesToRemove.clear();
    }

    public Chunk getChunk(int chunkX, int chunkY) {
        for (Chunk chunk : activeChunks) {
            if (chunk.getChunkX() == chunkX && chunk.getChunkY() == chunkY)
                return chunk;
        }

        return null;
    }

    // make this take a block x instead of normal x / y casted to an int lmfao retard
    public Block getBlock(int x, int y) {
        int blockX = x / Material.SIZE;
        int blockY = y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return null;

        if(!inBounds(blockX, blockY))
            return null;

        return chunk.getBlock(blockX % Chunk.SIZE, blockY % Chunk.SIZE);
    }

    public Block getWall(int x, int y) {
        int blockX = x / Material.SIZE;
        int blockY = y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return null;

        if(!inBounds(blockX, blockY))
            return null;

        return chunk.getWall(blockX % Chunk.SIZE, blockY % Chunk.SIZE);
    }

    public void setBlock(Block block, int x, int y) {
        int blockX = x / Material.SIZE;
        int blockY = y / Material.SIZE;
        getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE).getBlocks()[blockX % Chunk.SIZE][blockY % Chunk.SIZE] = block;
    }


    // make a set blcok function you reatard
    public void setWall(Block block, int x, int y) {
        int blockX = x / Material.SIZE;
        int blockY = y / Material.SIZE;
        getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE).getWalls()[blockX % Chunk.SIZE][blockY % Chunk.SIZE] = block;
    }


    public Block createBlock(Material material, float x, float y) {
        return createBlock(material.getID(), x, y);
    }

    public Block createBlock(int id, float x, float y) {
        if (id == Material.CHEST.getID())
            return new BlockChest(x, y);

        return new Block(Materials.getID(id), x, y);
    }

    public int getBlockX(int x) {
        return x / Material.SIZE;
    }

    public int getBlockY(int y) {
        return y / Material.SIZE;
    }

    public boolean inBounds(int x, int y) {
        return !(x < 0 || x > width || y < 0 || y > height);
    }

    public void addEntityLoop(Entity entity) {
        entitiesToAdd.add(entity);
    }

    public void removeEntityLoop(Entity entity) {
        entitiesToRemove.add(entity);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ArrayList<Chunk> getActiveChunks() {
        return activeChunks;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public WorldData getData() {
        return new WorldData(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
