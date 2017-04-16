package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Graphics;
import org.newdawn.slick.Color;

public class GuiWorldButton extends GuiButton {

    public GuiWorldButton(String text, float x, float y) {
        super(text, x, y);
    }

    public GuiWorldButton(String text, float y) {
        super(text, y);
    }

    public void init() {
        this.width = 600;
        this.height = 72;
    }

    public void render() {
        if (!selected) {
            Graphics.drawBorderedRect(x, y, width, height, 2, Color.black, Color.blue);
        } else {
            Graphics.drawBorderedRect(x, y, width, height, 2, Color.black, Color.cyan);
        }

        int size = 4;
        float width = Font.getWidth(text, size);

        Font.renderString(text, (x + (this.width - width) / 2) + 2, y + 12, size, Color.black);
    }
}
