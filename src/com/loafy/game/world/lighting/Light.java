package com.loafy.game.world.lighting;

import com.loafy.game.world.World;

import java.awt.*;

public class Light {

    private Color color;
    private LightMap map;
    private int x, y;
    private float lightLevel;
    private boolean enabled = true;

    public Light(LightMap map, float lightLevel, int x, int y, Color color) {
        this.map = map;

        this.x = x;
        this.y = y;
        this.lightLevel = lightLevel;
        this.color = color;

        if (this.color == null) this.color = new Color(0, 0, 0);
    }

    public void update(int currentx, int currenty, float lightDegrade, float lastLight) {
        update(currentx, currenty, lightDegrade, lastLight, true, true, true, true);
    }

    protected void update(int currentx, int currenty, float originalLight, float lastLight, boolean up, boolean down, boolean left, boolean right) {
        if (!enabled) return;
        if (currentx < 0 || currentx > map.width - 1 || currenty < 0 || currenty > map.height) return;
        float newLight = lastLight - (map.getDecrement(currentx, currenty) * originalLight);
        if (newLight <= map.getLevel(currentx, currenty)) return;

        map.setLevel(currentx, currenty, newLight);
        /*int[] averageColor = averageColor(color.getRed(), color.getGreen(), color.getBlue(), map.getRed(currentx, currenty), map.getGreen(currentx, currenty), map.getBlue(currentx, currenty));
        map.setColor(currentx, currenty, averageColor[0], averageColor[1], averageColor[2]);*/

        map.setColor(currentx, currenty,0, 0, 0);

       //

        if (right) update(currentx + 1, currenty, originalLight, newLight, up, down, false, right);

        if (down) update(currentx, currenty + 1, originalLight, newLight, false, down, left, right);

        if (left) update(currentx - 1, currenty, originalLight, newLight, up, down, left, false);

        if (up) update(currentx, currenty - 1, originalLight, newLight, up, false, left, right);
    }

    public int[] averageColor(int r1, int g1, int b1, int r2, int g2, int b2) {
        if(r1 == r2 && g1 == g2 && b1 == b2) return new int[]{r1, g1, b1};
     /*   if(c1 == null) c1 = new Color(0, 0, 0, 255);
        if(c2 == null) c2 = new Color(0, 0, 0, 255);*/

        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;

        //if(b !=0)System.out.println(b);


        return new int[]{r, g, b};
    }

    public void setEnabled(World world, boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            world.updateLighting();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLightLevel(float lightLevel) {
        this.lightLevel = lightLevel;
    }

    public float getLightLevel() {
        return lightLevel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
