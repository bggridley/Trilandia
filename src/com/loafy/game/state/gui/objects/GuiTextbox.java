package com.loafy.game.state.gui.objects;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Graphics;
import com.loafy.game.input.InputManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import util.KeyConversions;

import java.awt.*;

public class GuiTextbox extends GuiObject {

    public String text;
    public int maxLength;
    public int size;

    private float time = 1;

    private float textboxWidth = 300;
    private float textboxHeight = 30;

    public GuiTextbox(float x, float y, int size, int maxLength, float textboxWidth, float textboxHeight) {
        super(x, y);
        this.text = "";
        this.maxLength = maxLength;
        this.size = size;
        this.textboxWidth = textboxWidth;
        this.textboxHeight = textboxHeight;
    }

    public GuiTextbox(float y, int size, int maxLength, float textboxWidth, float textboxHeight) {
        this((Display.getWidth() - textboxWidth) / 2, y, size, maxLength, textboxWidth, textboxHeight);
    }

    public void update(float delta) {
        time += delta;
        int k = InputManager.keyPressed();
        String key = KeyConversions.getKeyName(k);
        char keyChar = InputManager.keyPressedChar();
        if (key != null && !key.equals("null")) {
            int ascii = (int) keyChar;
            if ((ascii >= 32 && ascii <= 126) || (ascii >= 128 && ascii <= 255)) {
                if (text.length() != maxLength) {
                    text = text + keyChar;
                    time = 30;

                }
            } else if (k == Keyboard.KEY_BACK) {
                if (text.length() != 0)
                    text = text.substring(0, text.length() - 1);
            }
        }
    }

    public void render() {
        Graphics.setColor(Color.BLACK);
        Graphics.drawRect(x, y, textboxWidth, textboxHeight, 2);
        renderUnderscore(Font.renderString(text, x, y + 6, size, Color.BLACK));
        //System.out.println(text);
    }

    public void renderUnderscore(float width) {
        if (((int) time / 15 % 2 == 0)) {
            String s = text.length() == maxLength ? "|" : "_";
            Font.renderString(s, x - 40 + width, y + 6, size, Color.BLACK);
        }
    }
}
