package com.loafy.game.world;

import com.google.gson.Gson;
import com.loafy.game.Main;
import com.loafy.game.entity.Entity;
import com.loafy.game.entity.EntityGoat;
import com.loafy.game.entity.EntityLiving;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.item.ItemStack;
import com.loafy.game.pathfinding.Node;
import com.loafy.game.pathfinding.Path;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.Materials;
import com.loafy.game.world.block.blocks.BlockChest;
import com.loafy.game.world.data.LevelData;
import com.loafy.game.world.data.WorldData;
import com.loafy.game.world.lighting.Light;
import com.loafy.game.world.lighting.LightMap;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class World extends WorldBase {

    private ArrayList<EntityLiving> entitySpawns;
    private ArrayList<Chunk> activeChunks;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToAdd;
    private ArrayList<Entity> entitiesToRemove;

    private LightMap lightMap;

    private EntityPlayer player;

    public float bxOffset, byOffset;
    public float xOffset, yOffset;
    public Texture background;

    public String name;
    public String fileName;

    private float time = 19250;
    private float timeTimer;
    private float low = 0.125f;
    private float high = 1.0f;
    private Color lowColor = new Color(0, 0, 15);
    private Color highColor = new Color(0, 0, 0);
    private float lowDecay = 2f;
    private float highDecay = 1f;
    private Color timeColor = lowColor;
    private float timeLight = low;
    private float timeDecay = lowDecay;
    private int sunriseStart = 5500; //5:30
    private int sunriseEnd = 6500; //6:30
    private int sunsetStart = 19500; //7:30
    private int sunsetEnd = 20500; //8:30

    private float spawnTimer = 0f;


    private int width, height;

    private Light playerlight;

    private Random random = new Random();

    private Path path;

    // add more data in constructor to find player spawn location and shit lol
    public World(String fileName, WorldData worldData, LevelData levelData) {
        this.activeChunks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.entitiesToAdd = new ArrayList<>();
        this.entitiesToRemove = new ArrayList<>();
        this.entitySpawns = new ArrayList<>();

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
        this.xOffset = player.getX() - (Display.getWidth() / 2) + (player.getWidth() / 2);
        this.yOffset = player.getY() - (Display.getHeight() / 2) + (player.getHeight() / 2);
        this.loadChunk((int) player.getX() / Material.SIZE, (int) player.getY() / Material.SIZE);
        this.playerlight = new Light(lightMap, 1f, (int) player.getX() / Material.SIZE, (int) player.getY() / Material.SIZE, new Color(0, 0, 0));
        this.lightMap.addLight(playerlight);
        this.background = Resources.backgroundTexture;
        this.bxOffset = -(Display.getWidth() - background.getWidth()) / 2;

        try {
            Main.getDrawable().makeCurrent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        entitySpawns.add(new EntityGoat());

        updateLighting();

        path = new Path(this, 0, 0, 5000, 3000);
    }

    public void render() {
        background.render(0 - bxOffset, 0 - byOffset, 1.0f, false, timeLight + timeColor.getRed() / 255f, timeLight + timeColor.getGreen() / 255f, timeLight + timeColor.getBlue() / 255f, 255f);
        for (Chunk chunk : activeChunks) {
            for (int x = 0; x < chunk.getBlocks().length; x++) {
                for (int y = 0; y < chunk.getBlocks()[x].length; y++) {
                    Block block = chunk.getBlocks()[x][y];
                    Block wall = chunk.getWalls()[x][y];

                    int blockX = (int) block.getX() / Material.SIZE;
                    int blockY = (int) block.getY() / Material.SIZE;


                    if (onScreen(block.getX() - xOffset, block.getY() - yOffset, Material.SIZE, Material.SIZE)) {


                        if (block.getMaterial().isTransparent() && wall != null)
                            wall.render(xOffset, yOffset, lightMap.getLevel(blockX, blockY), lightMap.getRed(blockX, blockY), lightMap.getGreen(blockX, blockY), lightMap.getBlue(blockX, blockY));

                        block.render(xOffset, yOffset, lightMap.getLevel(blockX, blockY), lightMap.getRed(blockX, blockY), lightMap.getGreen(blockX, blockY), lightMap.getBlue(blockX, blockY));


                    }
                }
            }
        }

      /*  for (int x = 0; x < path.getNodes().length; x++) {
            for (int y = 0; y < path.getNodes()[0].length; y++) {
                Node node = path.getNodes()[x][y];
                if (node != null) {
                    int blockX = path.getTopX() + node.getX();
                    int blockY = path.getTopY() + node.getY();

                    Resources.breakingSprite.getTexture(1).render(blockX * Material.SIZE - xOffset, blockY * Material.SIZE - yOffset);
                }
            }
        }*/


        for (Entity entity : entities) {
            float light = lightMap.getLevel((int) entity.getX() / Material.SIZE, (int) entity.getY() / Material.SIZE);
            entity.render(xOffset, yOffset, light);
        }

        player.renderContainer();

        float yStart = Display.getHeight() - 120;
        float fontHeight = 18f;
        Font.renderString("FPS: " + Main.CURRENT_FPS, 6, yStart + (0 * fontHeight), 2, Color.WHITE);
        Font.renderString("Active Chunks:  " + (activeChunks.size() + 1), 6, yStart + (1 * fontHeight), 2, Color.WHITE);
        Font.renderString("Entities:  " + (entities.size() + 1), 6, yStart + (2 * fontHeight), 2, Color.WHITE);
        Font.renderString("Coordinates: " + (int) player.getX() / Material.SIZE + ", " + (int) player.getY() / Material.SIZE, 6, yStart + (3 * fontHeight), 2, Color.WHITE);
        Font.renderString("Chunk Coordinates: " + (int) player.getX() / Material.SIZE / Chunk.SIZE + ", " + (int) player.getY() / Material.SIZE / Chunk.SIZE, 6, yStart + (4 * fontHeight), 2, Color.WHITE);
        Font.renderString("Time: " + (int) time, 6, yStart + (5 * fontHeight), 2, Color.WHITE);
    }

    int lastX = 0;
    int lastY = 0;

    public void update(float delta) {
        updateChunks();
        updateTime(delta);

        int mx = (int) (Mouse.getX() + xOffset);
        int my = (int) (Display.getHeight() - Mouse.getY() + yOffset);

        int blockX = getBlockX(mx);
        int blockY = getBlockY(my);

        for (Entity entity : entities) {
            entity.update(delta);
        }

        ItemStack itemStack = player.getInventory().getSlots().get(player.getInventory().getHotbarSlot()).getItemStack();
        if (itemStack.getItem().getLight() != -1) {
            playerlight.setEnabled(this, true);
            playerlight.setLightLevel(itemStack.getItem().getLight());
            playerlight.setX((int) player.getX() / Material.SIZE + 1);
            playerlight.setY((int) player.getY() / Material.SIZE);

            if (getBlockX(player.getX()) != lastX || getBlockY(player.getY()) != lastY) {
                updateLighting();
                lastX = getBlockX(player.getX());
                lastY = getBlockY(player.getY());
            }
        } else {
            playerlight.setEnabled(this, false);
        }

        handleEntitySpawns(delta);


        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
        entitiesToRemove.clear();

    }

    public void handleEntitySpawns(float delta) {
        spawnTimer += delta;

        int min = 30;
        int max = 70;

        if (spawnTimer >= 30 * 15) {
            spawnTimer = 0f;

            for (int x = -max; x < max; x++) {
                for (int y = -max; y < max; y++) {
                    if (Math.abs(x) > min || Math.abs(y) > min) {

                        int xx = x + ((int) player.getX() / Material.SIZE);
                        int yy = y + ((int) player.getY() / Material.SIZE);
                        for (EntityLiving entity : entitySpawns) {
                            if (entity.spawnConditions(this, xx, yy)) {
                                if (random.nextInt(entity.getSpawnRate()) == 0) {
                                    System.out.println(xx + ", " + yy);
                                    entity.newInstance(this, xx * Material.SIZE, yy * Material.SIZE);
                                    //addEntityLoop(entity.newInstance(this, xx * Material.SIZE, yy * Material.SIZE));
                                }
                            }
                        }
                    }
                }
            }

            for (Entity entity : entities) {
                if (entity instanceof EntityLiving) {
                    if (((EntityLiving) entity).despawnConditions(this, (int) entity.getX() / Material.SIZE, (int) entity.getY() / Material.SIZE)) {
                        removeEntityLoop(entity);
                        System.out.println("despawning" + entity.toString());
                    }
                }
            }
        }
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

        lightMap.update(this, lowestX - Chunk.SIZE * 3, lowestY - Chunk.SIZE * 3, highestX + Chunk.SIZE * 3, highestY + Chunk.SIZE * 3);
    }

    public void updateTime(float delta) {
        time += delta * (1 / 1.8) * 12;
        if (time >= 24000) time = 0;

        float sunriseIncrement = (high - low) / (sunriseEnd - sunriseStart);
        float sunsetIncrement = (low - high) / (sunsetEnd - sunsetStart);

        float sunriseRedIncrement = (float) (highColor.getRed() - lowColor.getRed()) / (float) (sunriseEnd - sunriseStart);
        float sunriseGreenIncrement = (float) (highColor.getGreen() - lowColor.getGreen()) / (float) (sunriseEnd - sunriseStart);
        float sunriseBlueIncrement = (float) (highColor.getBlue() - lowColor.getBlue()) / (float) (sunriseEnd - sunriseStart);

        float sunsetRedIncrement = (float) (lowColor.getRed() - highColor.getRed()) / (float) (sunsetEnd - sunsetStart);
        float sunsetGreenIncrement = (float) (lowColor.getGreen() - highColor.getGreen()) / (float) (sunsetEnd - sunsetStart);
        float sunsetBlueIncrement = (float) (lowColor.getBlue() - highColor.getBlue()) / (float) (sunsetEnd - sunsetStart);

        float sunriseDecayIncrement = (highDecay - lowDecay) / (sunriseEnd - sunriseStart);
        float sunsetDecayIncrement = (lowDecay - highDecay) / (sunsetEnd - sunsetStart);

        if (time >= sunriseStart && time <= sunriseEnd) {
            timeLight = low + (time - sunriseStart) * sunriseIncrement;
            timeColor = new Color((int) (lowColor.getRed() + (time - sunriseStart) * sunriseRedIncrement), (int) (lowColor.getGreen() + (time - sunriseStart) * sunriseGreenIncrement), (int) (lowColor.getBlue() + (time - sunriseStart) * sunriseBlueIncrement));
            timeDecay = lowDecay + (time - sunriseStart) * sunriseDecayIncrement;
        } else if (time >= sunsetStart && time <= sunsetEnd) {
            timeLight = high + (time - sunsetStart) * sunsetIncrement;
            timeColor = new Color((int) (highColor.getRed() + (time - sunsetStart) * sunsetRedIncrement), (int) (highColor.getGreen() + (time - sunsetStart) * sunsetGreenIncrement), (int) (highColor.getBlue() + (time - sunsetStart) * sunsetBlueIncrement));
            timeDecay = highDecay + (time - sunsetStart) * sunsetDecayIncrement;
        } else if (time >= sunsetEnd || time <= sunriseStart) {
            timeLight = low;
            timeColor = lowColor;
            timeDecay = lowDecay;
        } else if (time >= sunriseEnd && time <= sunsetStart) {
            timeLight = high;
            timeColor = highColor;
            timeDecay = highDecay;
        }

        //System.out.println(timeDecay);

        timeTimer += delta;
        if (timeTimer >= 30f) {
            timeTimer = 0;
            updateLighting();
        }
    }

    public void blockChange(int x, int y) { // update lightmap in chunk area lm o
        blocks[x / Material.SIZE][y / Material.SIZE] = getBlockFromChunks(x, y).getMaterial().getID();
        int block = getBlock(x / Material.SIZE, y / Material.SIZE);
        lightMap.setDecrement(x / Material.SIZE, y / Material.SIZE, Materials.getID(block).getDecrement());

        updateLighting();
    }

    public void updateChunks() {
        int chunkX = ((int) (player.getX() / Material.SIZE)) / Chunk.SIZE;
        int chunkY = ((int) (player.getY() / Material.SIZE)) / Chunk.SIZE;


        boolean loaded = false;
        for (int i = -3; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                if (loadChunk(chunkX + i, chunkY + j))
                    loaded = true;
            }
        }

        unloadChunks(chunkX, chunkY);

        if (loaded) {
            updateLighting();
        }
    }

    public boolean onScreen(float x, float y, float width, float height) {
        return (!(x + width < 0 || x > Display.getWidth() || y + height < 0 || y > Display.getHeight()));
    }

    public boolean loadChunk(int chunkX, int chunkY) {
        if (getChunk(chunkX, chunkY) != null) return false;

        if (chunkX >= 0 && chunkY >= 0 && chunkX < width / Chunk.SIZE && chunkY < height / Chunk.SIZE) {
            Chunk chunk = fetchChunk(chunkX, chunkY);

            for (int i = 0; i < chunk.getBlocks().length; i++) {
                for (int j = 0; j < chunk.getBlocks()[0].length; j++) {
                    float lightBlock = chunk.getBlock(i, j).getMaterial().getLight();
                    if (lightBlock != -1) {
                        lightMap.addLight(new Light(lightMap, lightBlock, (int) chunk.getBlock(i, j).getX() / Material.SIZE, (int) chunk.getBlock(i, j).getY() / Material.SIZE, null)); //todo add color
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
            if ((Math.abs(chunkX - chunk.getChunkX()) > 3) || Math.abs(chunkY - chunk.getChunkY()) > 3) {

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

    public float getTimeLight() {
        return timeLight;
    }

    public LightMap getLightMap() {
        return lightMap;
    }

    public Color getTimeColor() {
        return timeColor;
    }

    public float getTimeDecay() {
        return timeDecay;
    }

    //todo make this take a block x/y instead of normal x / y casted to a float   lmfao retard
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

    public int getBlockX(float x) {
        return (int) (x / Material.SIZE);
    }

    public int getBlockY(float y) {
        return (int) (y / Material.SIZE);
    }

    public Block createBlock(int id, float x, float y) {
        if (id == Material.CHEST.getID())
            return new BlockChest(x, y); //todo make every block into a class instaed of containing it in the thing XD

        return new Block(Materials.getID(id), x, y);
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
