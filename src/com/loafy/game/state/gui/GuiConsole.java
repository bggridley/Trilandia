package com.loafy.game.state.gui;

import com.loafy.game.gfx.Graphics;
import com.loafy.game.state.GameState;
import com.loafy.game.state.gui.objects.GuiTextbox;

import java.awt.*;

public class GuiConsole extends Gui {

    private int width, height;
    private int x;
    private int y;

    private GuiTextbox textbox;

    public GuiConsole(GameState state) {
        super(state, "Console");

        this.width = 600;
        this.height = 500;

        textbox = new GuiTextbox(x + (24 / 2), y + height - 12 - 30, 4, 1000, width - 24, 30);
    }

    public void render() {
        Graphics.drawBorderedRect(x, y, width, height, 3, Color.lightGray, Color.gray);
        Graphics.drawBorderedRect(x + (24 / 2), y + (24 / 2), width - 24, height - 70, 3, Color.lightGray, Color.darkGray);
        Graphics.drawBorderedRect(x + (24 / 2), y + height - 12 - 30, width - 24, 30, 3, Color.lightGray, Color.darkGray);

        textbox.render();
    }

    public void update(float delta) {
        textbox.update(delta);
    }

}
