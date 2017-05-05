package com.loafy.game.state.gui.objects;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.resources.Resources;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class GuiButton extends GuiObject {

    protected Texture buttonTexture;
    public String text;
    public boolean selected;
    protected float width, height;

    private Color color = new Color(235, 235, 255);

    public GuiButton(Texture buttonTexture, float x, float y) {
        super(x, y);
        this.buttonTexture = buttonTexture;
        init();
    }

    public GuiButton(Texture buttonTexture, float y) {
        this(buttonTexture, 0, y);
        this.buttonTexture = buttonTexture;
        init();
        x = (Display.getWidth() - width) / 2;
    }

    public GuiButton(String text, float x, float y) {
        super(x, y);
        this.text = text;
        this.buttonTexture = Resources.buttonTexture;
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
        selected = false;
    }

    public void render() {
        if (!selected) {
            buttonTexture.render(x, y);
        } else {
            buttonTexture.render(x, y, 1f, false, color);
        }

        if (text != null) {
            float size = 2f;

            Font.renderCenteredString(text, y + 14f, size, Color.black);
        }
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

    public void setColor(Color color) {
        this.color = color;
    }


    public boolean isSelected() {
        return selected;
    }
}
