package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.resources.Resources;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import util.KeyConversions;
import util.StringUtils;

public class GuiTextbox extends GuiObject {

    public Texture textbox;
    public String text;
    public int maxLength;
    public int size;

    public GuiTextbox(float x, float y, int size, int maxLength) {
        super(x, y);
        this.text = "";
        this.maxLength = maxLength;
        this.size = size;
        this.textbox = Resources.textBox;
    }

    public void update() {
        int k = InputManager.keyPressed();
        String key = KeyConversions.getKeyName(k);
        char keyChar = InputManager.keyPressedChar();
        if (key != null && !key.equals("null")) {
            int ascii = (int) keyChar;
            if ((ascii >= 32 && ascii <= 126) || (ascii >= 128 && ascii <= 255)) {
                if (text.length() != maxLength) {
                    text = text + keyChar;
                }
            } else if (k == Keyboard.KEY_BACK) {
                if(text.length() != 0)
                text = text.substring(0, text.length() - 1);
            }
        }
    }

    public void render() {
        textbox.render((Display.getWidth() - textbox.getWidth()) / 2, y - 4);
        Font.renderString(text, x, y, size, Color.black);
        //System.out.println(text);
    }

    public void renderUnderscore(float width) {

    }
}
