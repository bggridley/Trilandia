package com.loafy.game.world.lighting;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.data.LightMapData;

import java.util.ArrayList;

public class LightMap {

    public static float DEFAULT = 0.05f;

    public ArrayList<Light> lights;

    private float[][] lightLevels;
    private float[][] decrementValues;

    //private LightBlock[][] lightMap;

    public int width, height;

    public DirectionalLight sunlight;

    public LightMap(int width, int height) {
        // this.lightMap = new LightBlock[width][height];
        this.lightLevels = new float[width][height];
        this.decrementValues = new float[width][height];
        this.width = width;
        this.height = height;
        this.sunlight = new DirectionalLight(this, 1f, 0, 0, true, true, true, true);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                lightLevels[x][y] = 0f;
                decrementValues[x][y] = DEFAULT;
                //new LightBlock(0f, DEFAULT);
            }
        }


        this.lights = new ArrayList<>();
    }

    public void update(World world, int startX, int startY, int endX, int endY) {

        for (int i = 0; i < endX - startX; i++) {
            for (int j = 0; j < endY - startY; j++) {
                int x = startX + i;
                int y = startY + j;

                if (x < 0 || x > lightLevels.length || y < 0 || y > lightLevels.length) continue;

                lightLevels[x][y] = 0f;
            }
        }

        for (int i = 0; i < endX - startX; i++) {
            for (int j = 0; j < endY - startY; j++) {
                int x = startX + i;
                int y = startY + j;

                if (x < 1 || x > lightLevels.length - 1 || y < 1 || y > lightLevels.length - 1) continue;

                Block block = world.getBlock(x * Material.SIZE, y * Material.SIZE);

                Block left = world.getBlock((x - 1) * Material.SIZE, y * Material.SIZE);
                Block right = world.getBlock((x + 1) * Material.SIZE, y * Material.SIZE);
                Block top = world.getBlock(x * Material.SIZE, (y - 1) * Material.SIZE);
                Block bottom = world.getBlock(x * Material.SIZE, (y + 1) * Material.SIZE);

                Block wall = world.getWall(x * Material.SIZE, y * Material.SIZE);

                if (block == null) continue;

                if (left != null && right != null && top != null && bottom != null) {
                    if (left.getMaterial() == Material.AIR && right.getMaterial() == Material.AIR && top.getMaterial() == Material.AIR && bottom.getMaterial() == Material.AIR)
                        continue; // checks if all surroundings are air, so its pointless xd

                }
//                if(block.getMaterial() == Material.AIR && wall.getMaterial() == Material.AIR || block.getMaterial().isPassable()) {
                if ((block.getMaterial() == Material.AIR || block.getMaterial().isPassable()) && wall.getMaterial() == Material.AIR) {
                    sunlight.setX(x);
                    sunlight.setY(y);

                    sunlight.update(x, y, sunlight.getLightLevel());
                }
            }
        }


        for (Light light : lights) {
            light.update(light.getX(), light.getY(), light.getLightLevel());
        }
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public LightMapData getData() {
        return new LightMapData(this);
    }


    protected void setLevel(int x, int y, float level) {
        lightLevels[x][y] = level;
    }

    public float getLevel(int x, int y) {
        return lightLevels[x][y];
    }

    public void setDecrement(int x, int y, float decrement) {
        decrementValues[x][y] = decrement;
    }

    public float getDecrement(int x, int y) {
        return decrementValues[x][y];
    }

    public float[][] getDecrementValues() {
        return decrementValues;
    }

    public void setDecrementValues(float[][] decrementValues) {
        this.decrementValues = decrementValues;
    }

    public boolean containsLight(int x, int y) {
        for (Light light : lights) {
            if (light.getX() == x && light.getY() == y) return true;
        }

        return false;
    }

    public void removeLightAt(int x, int y) {
        //Iterator<Light> it = lights.iterator();
        lights.removeIf(light -> light.getX() == x && light.getY() == y);
      /*  while(it.hasNext()) {
            Light light = it.next();
            if(light.getX() == x && light.getY() == y) {
                it.remove();
            }
        }*/
    }
}
