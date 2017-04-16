package com.loafy.game.state.gui;

import java.util.LinkedList;

public class GuiScrollableList extends GuiObject {

    private GuiScrollBar scrollBar;
    private float offset;

    public GuiScrollableList(float x, float y, float width, float height) {
        super(x, y);

       // scrollBar = new GuiScrollBar(x + width, y, y + height);
    }

    public void update() {
        scrollBar.update();
    }

    public void render() {
        scrollBar.render();
    }

    public float getOffset() {
        return offset;
    }

}
