package com.loafy.game.world.lighting;

public class LightBlock {

    private float lightLevel;
    private float decrement;
    // private ArrayList<Light> effectedLights;

    public LightBlock(float lightLevel, float decrement) {
        this.lightLevel = lightLevel;
        this.decrement = decrement;
        //  this.effectedLights = new ArrayList<>();
    }


    public float getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(float lightLevel) {
        this.lightLevel = lightLevel;
    }

    public float getDecrement() {
        return decrement;
    }

    public void setDecrement(float decrement) {
        this.decrement = decrement;
    }
}
