package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GuiLoadingBar extends GuiObject {

    private Texture loadingBar; //TODO
    private int maxLoadingSteps;
    private float increment;

    private float maxLoadingLength;

    private int step = 0;
    public String status = "";

    public GuiLoadingBar (float y, String init, int steps) { // x is 0 because centering
        super(0, y);

        this.status = init;
        this.loadingBar = Resources.loading;
        this.maxLoadingSteps = steps;
        this.maxLoadingLength = (int)loadingBar.getWidth();
        this.increment = maxLoadingLength / maxLoadingSteps;
    }

    public void render() {
        float drawX = (Display.getWidth() - loadingBar.getWidth()) / 2;

        GL11.glColor3f(0.0f, 0.8f, 0.0f);
        GL11.glRectf(drawX, y, drawX + (step * increment), y + loadingBar.getHeight());
        loadingBar.render(drawX, y);

        Font.renderCenteredString(status, y + 40, 4f, Color.white);
    }

    public void setStatus(String status) {
        this.status = status;
        step++;
    }

    public void update() {

    }
}
