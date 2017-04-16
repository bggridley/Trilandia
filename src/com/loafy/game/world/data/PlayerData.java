package com.loafy.game.world.data;

import com.loafy.game.entity.player.EntityPlayer;

public class PlayerData {

    public int spawnX;
    public int spawnY;

    public PlayerData () {

    }

    public PlayerData(EntityPlayer player) {
        this.spawnX = player.spawnX;
        this.spawnY = player.spawnY;
    }
}
