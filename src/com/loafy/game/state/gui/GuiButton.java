package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.resources.Resources;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class GuiButton extends GuiObject {

    protected Texture buttonTexture;
    protected Texture buttonSelectedTexture;

    public String text;

    public boolean selected;

    protected float width, height;

    public GuiButton(String text, float x, float y) {
        super(x, y);
        this.text = text;
        this.buttonTexture = Resources.buttonTexture;
        this.buttonSelectedTexture = Resources.buttonSelectedTexture;
        init();
    }

    public GuiButton(String text, float y) {
        this(text, 0, y);

        init();
        x = (Display.getWidth() - width) / 2;
    }

    public void init() {
        this.width = buttonTexture.getWidth();
        this.height = buttonTexture.getHeight();
    }

    public void action() {

    }

    public void render() {
        if (!selected) {
            buttonTexture.render(x, y);
        } else {
            buttonSelectedTexture.render(x, y);
        }

        int size = 4;
        float width = Font.getWidth(text, size);

        Font.renderString(text, (x + (this.width - width) / 2) + 2, y + 12, size, Color.black);
    }

    public void update() {
        selected = false;

        float mx = InputManager.mouseX;
        float my = InputManager.mouseY;

        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                selected = true;
            }
        }

        if (selected) {
            if (InputManager.mouse1p)
                action();
        }
    }

    public boolean isSelected() {
        return selected;
    }
}
