package com.loafy.game.world.lighting;

import com.loafy.game.world.World;

public class Light {

    private LightMap map;
    private int x, y;
    private float lightLevel;
    private boolean enabled = true;

    public Light(LightMap map, float lightLevel, int x, int y) {
        this.map = map;

        this.x = x;
        this.y = y;
        this.lightLevel = lightLevel;
    }

    public void update(int currentx, int currenty, float lastLight) {
        update(currentx, currenty, lastLight, true, true, true, true);
    }

    protected void update(int currentx, int currenty, float lastLight, boolean up, boolean down, boolean left, boolean right) {
        if (!enabled) return;
        if (currentx < 0 || currentx > map.width - 1 || currenty < 0 || currenty > map.height) return;
        float newLight = lastLight - (map.getDecrement(currentx, currenty) * lightLevel);
        if (newLight <= map.getLevel(currentx, currenty)) return;

        map.setLevel(currentx, currenty, newLight);

        if (right) update(currentx + 1, currenty, newLight, up, down, left, right);

        if (down) update(currentx, currenty + 1, newLight, up, down, left, right);

        if (left) update(currentx - 1, currenty, newLight, up, down, left, right);

        if (up) update(currentx, currenty - 1, newLight, up, down, left, right);
    }

    public void setEnabled(World world, boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            world.updateLighting();
        }
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
