package com.loafy.game.world;

import com.loafy.game.Main;
import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.objects.GuiLoadingBar;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.lighting.LightMap;
import util.LineSmoother;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class WorldGenerator extends WorldBase {

    private int[] topBlocks;

    public BufferedImage image;

    public LightMap lightMap;

    private final float cs = 0.76f;
    private final float ce = 0.505f;
    private final int progressionBlocks = 28;

    private final int dirtAmount = 12;
    private final int stoneAmount = 16;

    private final float iterations = 7;

    private MenuState menuState = Main.menuState;
    private GuiLoadingBar loadingBar;

    private Random random = new Random();

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new int[width][height];
        this.walls = new int[width][height];
        topBlocks = new int[width];

        this.lightMap = new LightMap(width, height);

        loadingBar = menuState.guiGeneratingWorld.getLoadingBar();

        for (int x = 0; x < walls.length; x++) {
            for (int y = 0; y < walls[0].length; y++) {
                this.walls[x][y] = Material.AIR.getID();
            }
        }

        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                this.blocks[x][y] = Material.AIR.getID();
            }
        }

        generateLines();
        generateCaves();
        generateTerrain();
        generateOre();
    }


    public void generateLines() {
        loadingBar.setStatus("Generating terrain structure.");

        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            int iterations = 8;
            float threshold = 5F;
            float decay = 0.7F;
            int mh = 100;
            int sh = mh + random.nextInt(50);
            List<javafx.scene.shape.Line> lines = new ArrayList<>();
            lines.add(new javafx.scene.shape.Line(0.0F, sh, width, sh));
            for (int i = 0; i < iterations; i++) {
                ArrayList<javafx.scene.shape.Line> add = new ArrayList<>();
                Iterator<javafx.scene.shape.Line> it = lines.iterator();
                while (it.hasNext()) {
                    javafx.scene.shape.Line line = it.next();
                    it.remove();
                    float sX = (float) (int) line.getStartX();
                    float sY = (float) (int) line.getStartY();
                    float eX = (float) (int) line.getEndX();
                    float eY = (float) (int) line.getEndY();
                    float mX = (sX + eX) / 2.0F;
                    float rY = 29.0F * threshold * (random.nextFloat() - 0.5F);

                    add.add(new javafx.scene.shape.Line(sX, sY, mX, sY + rY));
                    add.add(new javafx.scene.shape.Line(mX, sY + rY, eX, eY));

                    lines = add;
                }
                threshold *= decay;
            }

            lines = LineSmoother.smoothLine(lines);


            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE);

            for (javafx.scene.shape.Line line : lines) {
                g.drawLine((int) line.getStartX(), (int) line.getStartY(), (int) line.getEndX(), (int) line.getEndY());
            }

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if ((((image.getRGB(x, y) >> 24) & 0xff) > 0)) {
                        topBlocks[x] = y;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateTerrain() {
        try {
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadingBar.setStatus("Generating terrain.");


        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                int block = blocks[x][y];
                if (y == topBlocks[x]) {

                    for (int i = 0; i < y; i++) {
                        blocks[x][i] = Material.AIR.getID();
                    }

                    blocks[x][y] = Material.GRASS.getID();
                    break;
                }
            }

            //maybe make a method that takes x, y to clean this up more ....

        }

        int lastTree = 0;
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                if (y == topBlocks[x]) {
                    lastTree = createTree(lastTree, x, y);
                    break;
                }
            }
        }
    }

    public int createTree(int lastTree, int x, int y) {
        int treeHeight = 6 + random.nextInt(16 - 6);
        if (x - 4 >= lastTree && random.nextInt(5) == 3) {
            lastTree = x;
            for (int i = 0; i < treeHeight; i++) {
                int yy = y - i - 1;
                setBlock(blocks, Material.LOG, x, yy);
            }

            int startY = y - treeHeight;

            for (int yy = -1; yy < 2; yy++) {
                for (int xx = -1; xx < 2; xx++) {

                    if (!(x + xx < 3 && x + xx > blocks.length - 3 && startY + yy < 3 && startY + yy > blocks[0].length - 3)) {
                        setBlock(blocks, Material.LEAF, x + xx, startY + yy);

                        //setBlock(Material.LEAF, x + xx, startY + yy); //this.blocks[x + xx][startY + yy] = new Block(Material.LEAF, (x + xx) * Material.SIZE, (startY + yy) * Material.SIZE);
                    }
                }
            }
        }

        return lastTree;
    }

    public void generateCaves() {
        loadingBar.setStatus("Generating caves.");

        float dif = (ce - cs) / progressionBlocks; //increment from each block

        for (int x = 0; x < blocks.length; x++) {
            float csa = cs;
            for (int y = 0; y < blocks[x].length; y++) {
                blocks[x][y] = Material.DIRT.getID();
                if (y > topBlocks[x] && y < topBlocks[x] + progressionBlocks) {
                    csa += dif;
                }

                if (random.nextFloat() <= csa)
                    blocks[x][y] = Material.STONE.getID();
                else
                    blocks[x][y] = Material.AIR.getID();
            }
        }

        for (int i = 0; i < iterations; i++) {
            refine(blocks, Material.STONE, Material.AIR);
            loadingBar.setStatus("Generating caves.");
        }


        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                if (y == topBlocks[x]) {

                    if (x == blocks.length / 2) {
                        spawnX = x;
                        spawnY = y - 3;
                    }

                    for (int i = 2; i < height - y; i++) {
                        walls[x][y + i] = Material.STONE_WALL.getID();
                    }

                    for (int i = 0; i < stoneAmount; i++) {
                        blocks[x][y + i] = Material.STONE.getID();
                    }

                    for (int i = 0; i < dirtAmount; i++) {
                        blocks[x][y + i] = Material.DIRT.getID();
                        walls[x][y + i + 2] = Material.DIRT_WALL.getID();
                    }

                    break;
                }
            }
        }


    }

    private void generateOre() {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                int block = blocks[x][y];
                if (block == Material.STONE.getID()) {
                    if (random.nextInt(17 ^ 2) == 0) { // 1 in every 20 square blocks
                        generateOre(x, y, 20, Material.COPPER_ORE);
                    }

                    if(random.nextInt(32 ^ 2) == 0) {
                        generateOre(x, y, 26, Material.SILVER_ORE);
                    }
                }
            }
        }
    }

    private void generateOre(int x, int y, int size, Material material) {
        float csa = 0.675f;
       // float dec = (float) size - s
        int[][] plane = new int[size][size];
        for (int xx = 0; xx < plane.length; xx++) {
            for (int yy = 0; yy < plane[0].length; yy++) {
                plane[xx][yy] = material.getID();
                if(random.nextFloat() <= csa)
                    plane[xx][yy] = Material.AIR.getID();
                else
                    plane[xx][yy] = material.getID();
            }
        }


        for (int i = 0; i < 7; i++) {
            refine(plane, Material.AIR, material);
        }


        for (int xx = 0; xx < plane.length; xx++) {
            for (int yy = 0; yy < plane[0].length; yy++) {
                int block = plane[xx][yy];

                if(block == material.getID()) {
                    int setX = x - (size / 2) + xx;
                    int setY = y - (size / 2) + yy;

                    if(getBlock(blocks, setX, setY) == Material.STONE.getID()) {
                        setBlock(blocks, material, setX, setY);
                    }
                }
            }
        }
        // alive = AIR, dead = COPPER/material
    }

    public void refine(int[][] array, Material alive, Material dead) {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                int nbs = getNeighbors(array, x, y, alive);

                if (nbs > 4) {
                    setBlock(array, alive, x, y);
                } else if (nbs < 4) {
                    setBlock(array, dead, x, y);
                }
            }
        }
    }

    public int getNeighbors(int[][] array, int x, int y, Material alive) {
        int wallCount = 0;
        for (int nx = x - 1; nx <= x + 1; nx++) {
            for (int ny = y - 1; ny <= y + 1; ny++) {
                if (nx >= 0 && nx < array.length && ny >= 0 && ny < array[0].length) {
                    if (nx != x || ny != y) {
                        if (getBlock(array, nx, ny) == alive.getID()) wallCount++;
                    }
                } else {
                    wallCount++;
                }
            }
        }

        return wallCount;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    public int getSpawnY() {
        return this.spawnY;
    }
}
