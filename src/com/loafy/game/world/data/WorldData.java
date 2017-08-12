package com.loafy.game.world.data;

import com.loafy.game.world.World;
import com.loafy.game.world.WorldBase;

import java.io.Serializable;
import java.util.Date;

public class WorldData implements Serializable {

    public Date date;
    public String name;
    public int width;
    public int height;
    public int spawnX;
    public int spawnY;

    public WorldData() {

    }

    public WorldData(WorldBase world) {
        this.name = world.getName();
        this.date = new Date();
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.spawnX = world.getSpawnX();
        this.spawnY = world.getSpawnY();
    }
}
