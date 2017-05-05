package com.loafy.game.world.data;

import com.loafy.game.world.lighting.LightMap;

public class LightMapData {

    public float[][] decrementValues;

    public LightMapData() {

    }

    public LightMapData(LightMap map) {
        this.decrementValues = map.getDecrementValues();
    }


}
