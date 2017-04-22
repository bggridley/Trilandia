package com.loafy.game.state.gui.objects;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Graphics;
import com.loafy.game.input.InputManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import util.KeyConversions;

public class GuiTextbox extends GuiObject {

    public String text;
    public int maxLength;
    public int size;

    private int time = 1;

    public GuiTextbox(float x, float y, int size, int maxLength) {
        super(x, y);
        this.text = "";
        this.maxLength = maxLength;
        this.size = size;
    }

    public void update() {
        time++;
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
                if(text.length() != 0)
                text = text.substring(0, text.length() - 1);
            }
        }
    }

    public void render() {
        float textboxWidth = 300;
        float textboxHeight = 30;
        Graphics.setColor(Color.black);
        Graphics.drawRect((Display.getWidth() - textboxWidth) / 2, y, textboxWidth, textboxHeight, 2);
        renderUnderscore(Font.renderString(text, x - 40, y + 6, size, Color.black));
        //System.out.println(text);
    }

    public void renderUnderscore(float width) {
        if((time / 15 % 2 == 0)) {
            String s = text.length() == maxLength ? "|" : "_";
            Font.renderString(s, x - 40 + width, y + 6, size, Color.black);
        }
    }
}
