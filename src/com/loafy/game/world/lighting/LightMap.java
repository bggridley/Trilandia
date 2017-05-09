package com.loafy.game.world.lighting;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Material;

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

                int block = world.getBlock(x, y);

                int left = world.getBlock((x - 1), y );
                int right = world.getBlock((x + 1) , y );
                int top = world.getBlock(x , (y - 1) );
                int bottom = world.getBlock(x, (y + 1) );

                int wall = world.getWall(x, y);

                if (block == -1) continue;

                if (left != -1 && right != -1 && top != -1 && bottom != -1) {
                    if (left == Material.AIR.getID() && right == Material.AIR.getID() && top == Material.AIR.getID() && bottom == Material.AIR.getID())
                        continue;

                }

                if(block == Material.AIR.getID() && wall == Material.AIR.getID() || Material.fromID(block).isPassable()) {
                    if ((block == Material.AIR.getID() || Material.fromID(block).isPassable()) && wall == Material.AIR.getID()) {
                        sunlight.setX(x);
                        sunlight.setY(y);

                        sunlight.update(x, y, sunlight.getLightLevel());
                    }
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
