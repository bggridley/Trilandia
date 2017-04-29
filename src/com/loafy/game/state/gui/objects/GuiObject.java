package com.loafy.game.state.gui.objects;

public class GuiObject {

    public float x;
    public float y;

    public GuiObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render() {

    }

    public void update(float delta) {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
