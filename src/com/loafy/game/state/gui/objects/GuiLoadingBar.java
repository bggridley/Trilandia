package com.loafy.game.state.gui.objects;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Graphics;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GuiLoadingBar extends GuiObject {

    private int maxLoadingSteps;
    private float increment;
    private float maxLoadingLength;

    private float width;
    private float height;

    private int step = 0;
    public String status = "";

    public GuiLoadingBar(float y, String init, int steps) { // x is 0 because centering
        super(0, y);

        this.width = 512;
        this.height = 30;

        this.status = init;
        this.maxLoadingSteps = steps;
        this.maxLoadingLength = (int) width; // TODO
        this.increment = maxLoadingLength / maxLoadingSteps;
    }

    public void render() {
        float drawX = (Display.getWidth() - width) / 2;

        GL11.glColor3f(0.0f, 0.8f, 0.0f);
        GL11.glRectf(drawX, y, drawX + (step * increment), y + height);

        Graphics.setColor(Color.black);
        Graphics.drawRect(drawX, y, width, height, 2);

        Font.renderCenteredString(status, y + 40, 4f, Color.white);
    }

    public void setStatus(String status) {
        this.status = status;
        step++;
    }

    public void update() {

    }
}
