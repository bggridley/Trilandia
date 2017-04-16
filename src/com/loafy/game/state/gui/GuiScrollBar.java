package com.loafy.game.state.gui;

import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.resources.Resources;

public class GuiScrollBar extends GuiObject {

    private float scrollY;
    private float startY;
    private float endY;

    private float width;
    private float height;

    private Texture scrollbar;

    public boolean bounds;

    private float offset;
    private float min;
    private float max;

    private float ipp;

    public GuiScrollBar(float x, float startY, float endY, int scrollLength) {
        super(x, startY);

        this.startY = startY;
        this.endY = endY;
        this.scrollbar = Resources.scrollbar;
        this.width = scrollbar.getWidth();
        this.height = scrollbar.getHeight();

        this.ipp = scrollLength / ((endY - startY) - height);
        System.out.println(ipp);

        min = 0;
        max = scrollLength;
    }

    public void update() {
        if (InputManager.mouse1p) {
            bounds = false;
        }

        if (InputManager.mouse1) {
            float mouseY = InputManager.mouseY;
            float mouseX = InputManager.mouseX;

            if (mouseY < y + height && mouseY > y && mouseX < x + width && mouseX > x && !bounds) {
                bounds = true;
                scrollY = mouseY;
            }

            System.out.println(offset);

            if (bounds) {
                float dif = mouseY - scrollY;
                float newY = y + dif;

                if (mouseY > y && mouseY < y + height) {
                    if (newY > endY - height) {
                        y = endY - height;
                        offset = -max;
                    } else if (newY < startY) {
                        y = startY;

                        offset = min;
                    } else {
                        y = newY;
                        offset -= (dif * ipp);
                    }

                    scrollY = mouseY;
                }
            }
        }
    }

    public float getWidth() {
        return width;
    }

    public void render() {
        scrollbar.render(x, y);
    }

    public float getOffset() {
        return offset;
    }
}
