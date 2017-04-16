package com.loafy.game.world.data;

import com.loafy.game.world.World;

import java.util.Date;

public class WorldData {

    public Date date;
    public String name;
    public int width;
    public int height;

    public WorldData() {

    }

    public WorldData(World world) {
        this.name = world.getName();
        this.date = new Date();
        this.width = world.getWidth();
        this.height = world.getHeight();
    }
}
