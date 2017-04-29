package com.loafy.game.world.lighting;

public class DirectionalLight extends Light {

    private boolean up, down, left, right;

    public DirectionalLight(LightMap map, float lightLevel, int x, int y, boolean up, boolean down, boolean left, boolean right) {
        super(map, lightLevel, x, y);


        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;

    }

    public void update(int currentx, int currenty, float lastLight) {
        update(currentx, currenty, lastLight, up, down, left, right);
    }
}
