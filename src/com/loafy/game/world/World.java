package com.loafy.game.world;

import com.google.gson.Gson;
import com.loafy.game.Main;
import com.loafy.game.entity.Entity;
import com.loafy.game.entity.EntityGoat;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Graphics;
import com.loafy.game.gfx.Texture;
import com.loafy.game.item.ItemStack;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.Materials;
import com.loafy.game.world.block.blocks.BlockChest;
import com.loafy.game.world.data.LevelData;
import com.loafy.game.world.data.WorldData;
import com.loafy.game.world.lighting.Light;
import com.loafy.game.world.lighting.LightMap;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Iterator;

public class World extends WorldBase {

    private ArrayList<Chunk> activeChunks;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToAdd;
    private ArrayList<Entity> entitiesToRemove;

    private LightMap lightMap;

    private EntityPlayer player;
    private EntityGoat goat;

    public float bxOffset, byOffset;
    public float xOffset, yOffset;
    public Texture background;

    public String name;
    public String fileName;

    private int width, height;

    Light playerlight;

    private Color ambient = new Color(255, 226, 153, 12);

    public void initLists() {
        this.activeChunks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.entitiesToAdd = new ArrayList<>();
        this.entitiesToRemove = new ArrayList<>();
    }

    // add more data in constructor to find player spawn location and shit lol
    public World(String fileName, WorldData worldData, LevelData levelData) {
        initLists();
        this.name = worldData.name;
        this.fileName = fileName;

        this.width = worldData.width;
        this.height = worldData.height;
        this.blocks = levelData.blocks;
        this.walls = levelData.walls;
        this.spawnX = worldData.spawnX;
        this.spawnY = worldData.spawnY;

        this.lightMap = new LightMap(width, height);

        float[][] decrementValues = new float[width][height];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Material bm = Material.fromID(blocks[i][j]);
                decrementValues[i][j] = bm.getDecrement();
            }
        }

        this.lightMap.setDecrementValues(decrementValues);

        this.player = new EntityPlayer(this, worldData.spawnX * Material.SIZE, worldData.spawnY * Material.SIZE); // maybe make spawnx/y an int to save a TEENIE TINIIE bit of space :) ) ))
        //this.goat = new EntityGoat(this, playerData.spawnX * Material.SIZE, playerData.spawnY * Material.SIZE);

        this.xOffset = player.getX() - (Display.getWidth() / 2) + (player.getWidth() / 2);
        this.yOffset = player.getY() - (Display.getHeight() / 2) + (player.getHeight() / 2);
        this.loadChunk((int) player.getX() / Material.SIZE, (int) player.getY() / Material.SIZE);

        playerlight = new Light(lightMap, 1f, (int) player.getX() / Material.SIZE, (int) player.getY() / Material.SIZE);
        lightMap.addLight(playerlight);

        initBackground();

        try {
            Main.getDrawable().makeCurrent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initBackground() {
        this.background = Resources.backgroundTexture;

        this.bxOffset = -(Display.getWidth() - background.getWidth()) / 2;
        //this.byOffset = (Display.getHeight() - background.getHeight()) / 2;
    }

    public void render() {
        background.render(0 - bxOffset, 0 - byOffset);
        for (Chunk chunk : activeChunks) {
            for (int x = 0; x < chunk.getBlocks().length; x++) {
                for (int y = 0; y < chunk.getBlocks()[x].length; y++) {
                    Block block = chunk.getBlocks()[x][y];
                    Block wall = chunk.getWalls()[x][y];

                    int blockX = (int) block.getX() / Material.SIZE;
                    int blockY = (int) block.getY() / Material.SIZE;


                    if (block.getMaterial().isTransparent() && wall != null)
                        wall.render(xOffset, yOffset, lightMap.getLevel(blockX, blockY));

                    block.render(xOffset, yOffset, lightMap.getLevel(blockX, blockY));

                }
            }
        }


        for (Entity entity : entities) {
            float light = lightMap.getLevel((int) entity.getX() / Material.SIZE, (int) entity.getY() / Material.SIZE);
            entity.render(xOffset, yOffset, light);
        }

        player.renderContainer();


        Graphics.setColor(ambient);
        Graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());

        float yStart = Display.getHeight() - 120;
        float fontHeight = 18f;
        Font.renderString("FPS: " + Main.CURRENT_FPS, 6, yStart + (0 * fontHeight), 2, Color.white);
        Font.renderString("Active Chunks:  " + (activeChunks.size() + 1), 6, yStart + (1 * fontHeight), 2, Color.white);
        Font.renderString("Entities:  " + (entities.size() + 1), 6, yStart + (2 * fontHeight), 2, Color.white);
        Font.renderString("Coordinates: " + (int) player.getX() / Material.SIZE + ", " + (int) player.getY() / Material.SIZE, 6, yStart + (3 * fontHeight), 2, Color.white);
        Font.renderString("Chunk Coordinates: " + (int) player.getX() / Material.SIZE / Chunk.SIZE + ", " + (int) player.getY() / Material.SIZE / Chunk.SIZE, 6, yStart + (4 * fontHeight), 2, Color.white);
    }

    public void update(float delta) {
        updateChunks();

        for (Entity entity : entities) {
            entity.update(delta);
        }

        ItemStack itemStack = player.getInventory().getSlots().get(player.getInventory().getHotbarSlot()).getItemStack();
        if (itemStack.getItem().getLight() != -1) {
            playerlight.setEnabled(this, true);
            playerlight.setLightLevel(itemStack.getItem().getLight());
            playerlight.setX((int) player.getX() / Material.SIZE + 1);
            playerlight.setY((int) player.getY() / Material.SIZE);
            updateLighting();
        } else {
            playerlight.setEnabled(this, false);
        }

        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);

        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }


    public void blockChange(int x, int y) { // update lightmap in chunk area lm o
        blocks[x / Material.SIZE][y / Material.SIZE] = getBlockFromChunks(x, y).getMaterial().getID();
        int block = getBlock(x / Material.SIZE, y / Material.SIZE);
        lightMap.setDecrement(x / Material.SIZE, y / Material.SIZE, Materials.getID(block).getDecrement());

        updateLighting();
    }

    public void updateLighting() {
        int lowestX = Integer.MAX_VALUE;
        int lowestY = Integer.MAX_VALUE;
        int highestX = 0;
        int highestY = 0;

        for (int i = 0; i < activeChunks.size(); i++) {
            Chunk chunk = activeChunks.get(i);

            int curX = chunk.getChunkX() * Chunk.SIZE;
            int curY = chunk.getChunkY() * Chunk.SIZE;

            if (curX < lowestX) lowestX = curX;
            if (curY < lowestY) lowestY = curY;
            if (curX + Chunk.SIZE > highestX) highestX = curX;
            if (curY + Chunk.SIZE > highestY) highestY = curY;
        }

        lightMap.update(this, lowestX - Chunk.SIZE, lowestY - Chunk.SIZE, highestX + Chunk.SIZE, highestY + Chunk.SIZE);
    }


    public void updateChunks() {
        int chunkX = ((int) (player.getX() / Material.SIZE)) / Chunk.SIZE;
        int chunkY = ((int) (player.getY() / Material.SIZE)) / Chunk.SIZE;


        boolean loaded = false;
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 2; j++) {
                if (loadChunk(chunkX + i, chunkY + j))
                    loaded = true;
            }
        }

        unloadChunks(chunkX, chunkY);

        if (loaded) {
            updateLighting();
        }
    }


    public boolean loadChunk(int chunkX, int chunkY) {
        if (getChunk(chunkX, chunkY) != null) return false;

        if (chunkX >= 0 && chunkY >= 0 && chunkX < width / Chunk.SIZE && chunkY < height / Chunk.SIZE) {
            Chunk chunk = fetchChunk(chunkX, chunkY);

            for (int i = 0; i < chunk.getBlocks().length; i++) {
                for (int j = 0; j < chunk.getBlocks()[0].length; j++) {
                    float lightBlock = chunk.getBlock(i, j).getMaterial().getLight();
                    if (lightBlock != -1) {
                        lightMap.addLight(new Light(lightMap, lightBlock, (int) chunk.getBlock(i, j).getX() / Material.SIZE, (int) chunk.getBlock(i, j).getY() / Material.SIZE));
                    }
                }
            }

            activeChunks.add(chunk);
        }

        return true;
    }


    // player chunkx/y
    public void unloadChunks(int chunkX, int chunkY) {
        Iterator<Chunk> it = activeChunks.iterator();

        Gson gson = new Gson();
        while (it.hasNext()) {
            Chunk chunk = it.next();
            if ((Math.abs(chunkX - chunk.getChunkX()) >= 4) || Math.abs(chunkY - chunk.getChunkY()) >= 3) {

                for (int i = 0; i < chunk.getBlocks().length; i++) {
                    for (int j = 0; j < chunk.getBlocks()[0].length; j++) {
                        float lightBlock = chunk.getBlock(i, j).getMaterial().getLight();
                        if (lightBlock != -1) {
                            lightMap.removeLightAt((int) chunk.getBlock(i, j).getX() / Material.SIZE, (int) chunk.getBlock(i, j).getY() / Material.SIZE);
                        }
                    }
                }

                saveChunk(chunk);
                it.remove();

                System.out.println("unloading/saving chunk " + chunk.getChunkX() + ", " + chunk.getChunkY() + "... active chunks: " + activeChunks.size());
            }
        }
    }

    public void unload() {
        for (Chunk chunk : activeChunks) {
            saveChunk(chunk);
        }

        WorldLoader.save(getWorldData(), fileName, "world.dat");
        WorldLoader.save(getLevelData(), fileName, "level.dat");
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

    //todo make this take a block x instead of normal x / y casted to an int lmfao retard
    public Block getBlockFromChunks(float x, float y) {
        int blockX = (int) x / Material.SIZE;
        int blockY = (int) y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return null;

        if (!inBounds(blockX, blockY))
            return null;

        return chunk.getBlock(blockX % Chunk.SIZE, blockY % Chunk.SIZE);
    }

    public Block getWallFromChunks(int x, int y) {
        int blockX = x / Material.SIZE;
        int blockY = y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return null;

        if (!inBounds(blockX, blockY))
            return null;

        return chunk.getWall(blockX % Chunk.SIZE, blockY % Chunk.SIZE);
    }

    public void setBlockFromChunks(Block block, float x, float y) {
        int blockX = (int) x / Material.SIZE;
        int blockY = (int) y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return;

        if (!inBounds(blockX, blockY))
            return;

        chunk.getBlocks()[blockX % Chunk.SIZE][blockY % Chunk.SIZE] = block;

        blockChange((int) x, (int) y);
    }


    // make a set blcok function you reatard
    public void setWallFromChunks(Block block, float x, float y) {
        int blockX = (int) x / Material.SIZE;
        int blockY = (int) y / Material.SIZE;

        Chunk chunk = getChunk(blockX / Chunk.SIZE, blockY / Chunk.SIZE);

        if (chunk == null)
            return;

        if (!inBounds(blockX, blockY))
            return;

        chunk.getWalls()[blockX % Chunk.SIZE][blockY % Chunk.SIZE] = block;

        blockChange((int) x, (int) y);
    }
/*
    public Block createBlock(Material material, float x, float y) {
        return createBlock(material.getID(), x, y);
    }*/

    public Block createBlock(int id, float x, float y) {
        if (id == Material.CHEST.getID())
            return new BlockChest(x, y); //todo make every block into a class instaed of containing it in the thing XD

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

    public LightMap getLightMap() {
        return lightMap;
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
